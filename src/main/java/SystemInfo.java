import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class SystemInfo {

    public static String getRAMInfo() {
        String result = "";
        try {
            Process process = Runtime.getRuntime().exec("systeminfo");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Gesamter physischer Speicher")) {
                    result = line.trim();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static double getFreeDiskSpaceInGB() {
        String result = "";
        try {
            Process process = Runtime.getRuntime().exec("wmic logicaldisk get size,freespace,caption");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("C:")) {
                    result = line.trim();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Extrahiere die Größe und den freien Speicherplatz aus dem Ergebnis
        String[] parts = result.split("\\s+");
        long size = Long.parseLong(parts[1]);
        String freeSpaceStr = parts[2].replace(",", "."); // Ersetze Komma durch Punkt
        double freeSpace = Double.parseDouble(freeSpaceStr);

        // Konvertiere Bytes in Gigabytes
        double freeSpaceInGB = freeSpace / (1024 * 1024 * 1024);

        // Runde auf zwei Nachkommastellen
        DecimalFormat df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
        return Double.parseDouble(df.format(freeSpaceInGB));
    }

    public static double getCPULoad() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
            Object attribute = mbs.getAttribute(name, "ProcessCpuLoad");

            // ProcessCpuLoad ist ein Wert zwischen 0 und 1, daher wird er mit 100 multipliziert
            double cpuLoad = ((Double) attribute) * 100.0;
            DecimalFormat dfs = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));

            return Double.parseDouble(dfs.format(cpuLoad));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1.0; // Fehlerfall
    }


}
