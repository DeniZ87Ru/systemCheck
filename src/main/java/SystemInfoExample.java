import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.GraphicsCard;

import java.text.DecimalFormat;

public class SystemInfoExample {

    public static String printGPUInfo() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        GraphicsCard[] graphicsCards = hardware.getGraphicsCards().toArray(new GraphicsCard[0]);

        StringBuilder gpuText = new StringBuilder();

        for (GraphicsCard gpu : graphicsCards) {
            gpuText.append("GPU Name: ").append(gpu.getName()).append("\n");
            gpuText.append("GPU Vendor: ").append(gpu.getVendor()).append("\n");

            long vramBytes = gpu.getVRam();
            double vramMegabytes = (double) vramBytes / (1024 * 1024); // Umrechnung in Megabyte
            DecimalFormat df = new DecimalFormat("#.##"); // Auf zwei Nachkommastellen runden
            String vramFormatted = df.format(vramMegabytes);

            gpuText.append("GPU VRAM: ").append(vramFormatted).append(" MB").append("\n");
        }

        if (gpuText.length() > 0) {
            return gpuText.toString();
        } else {
            return "Das hat leider nicht funktioniert..";
        }
    }
}
