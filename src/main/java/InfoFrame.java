import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoFrame {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Umgebungsinformationen");
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null); // Setzen den Layoutmanager auf null damit man manuel alles verwalten kann

        JTextArea area = new JTextArea();
        area.setBounds(0, 0, 600, 300);
        area.setEditable(false);
        area.setForeground(Color.WHITE);
        area.setBackground(Color.DARK_GRAY);

        JButton button = new JButton("Netzwerkinformationen");
        button.setBounds(10, 310, 200, 30);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean verbindung = NetzwerkInfo.isInternetConnected();
                double speed = NetzwerkInfo.getDownloadSpeed();
                int port = NetzwerkInfo.getPort();
                String ip = NetzwerkInfo.getIPAddress();
                String mac = NetzwerkInfo.getMACAddress();

                // Weitere Methoden zum Abrufen von CPU-, Grafikkarten- und Temperaturinformationen hinzufügen
                String ramInfo = SystemInfo.getRAMInfo();
                double diskSpace = SystemInfo.getFreeDiskSpaceInGB();
                double cpuLoad = SystemInfo.getCPULoad();
                // Erstelle einen String, der alle Informationen untereinander enthält
                String text = "Netzwerkinformationen: " + "\n" + "\n";
                text += "Internetverbindung: " + verbindung + "\n";
                text += "Download-speed: " + speed + "\n";
                text += "Port: " + port + "\n";
                text += "IP-Adresse: " + ip + "\n";
                text += "MAC-Adresse: " + mac + "\n" + "\n";
                text += "Systeminformationen: " + "\n" + "\n";
                text += ramInfo + "\n";
                text += "Festplatte: " + diskSpace + "\n";
                if (cpuLoad >= 0) {
                    text += "CPU-Auslastung (%): " + cpuLoad + "\n";
                } else {
                    text += "Fehler beim Abrufen der CPU-Auslastung." + "\n";
                }
                text += SystemInfoExample.printGPUInfo();
                // Setze den erstellten Text in das Textfeld
                area.setText(text);
            }
        });

        frame.add(area);
        frame.add(button);
        frame.setVisible(true);
    }
}
