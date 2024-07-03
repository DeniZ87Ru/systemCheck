import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.swing.*;
import java.io.InputStream;
import java.net.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class NetzwerkInfo {
    boolean isInternetConnected = isInternetConnected();
    double downloadSpeed = getDownloadSpeed();
    int port = getPort();
    String ipAddress = getIPAddress();
    String localIPAddress = getLocalIPAddress();
    String macAddress = getMACAddress();

    public NetzwerkInfo() throws IOException {
    }


    // Überprüfen, ob eine Internetverbindung besteht
    public static boolean isInternetConnected() {
        try {
            InetAddress.getByName("www.google.com");
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }

    // Geschwindigkeit der Internetverbindung abrufen
    public static double getDownloadSpeed() {
        try {
            String url = "https://www.example.com"; // URL zur Geschwindigkeitsmessung
            Path tempFile = Files.createTempFile("download", ".tmp");

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(url);
            InputStream inputStream = httpClient.execute(request).getEntity().getContent();
            Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);


            long fileSizeBytes = Files.size(tempFile);
            long startTime = System.currentTimeMillis();
            Files.delete(tempFile);
            long endTime = System.currentTimeMillis();

            // Die Download-Geschwindigkeit in Mbps berechnen
            double downloadTimeSeconds = (endTime - startTime) / 1000.0; // in Sekunden
            double downloadSpeedMbps = (fileSizeBytes * 8.0) / (downloadTimeSeconds * 1024 * 1024);

            return downloadSpeedMbps;

        } catch (IOException e) {
            return 0.0;
        }
    }

    // Port abrufen
    public static int getPort() {
        try {
            ServerSocket socket = new ServerSocket(0);
            int port = socket.getLocalPort();
            return port;
        } catch (IOException e) {
            return 0;
        }
    }

    // Öffentliche IP-Adresse abrufen
    public static String getIPAddress() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (IOException e) {
            return "Das hat leider nicht geklappt :(";
        }
    }

    // Lokale IP-Adresse abrufen
    public static String getLocalIPAddress() throws SocketException, UnknownHostException {
        InetAddress localhost = InetAddress.getLocalHost();
        return localhost.getHostAddress();
    }

    // MAC-Adresse abrufen
    public static String getMACAddress() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localhost);
            byte[] mac = networkInterface.getHardwareAddress();

            StringBuilder macAddress = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                macAddress.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            return macAddress.toString();
        } catch (IOException e) {
            return "Das hat leider nicht geklappt :(";
        }
    }
}
