package logic;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
public class NetworkScanner {
   public List<DeviceInfo> scanRange(List<String> ips, int timeout) {
       List<DeviceInfo> results = new ArrayList<>();
       for (String ip : ips) {
           try {
               long start = System.currentTimeMillis();
               boolean reachable = InetAddress.getByName(ip).isReachable(timeout);
               long end = System.currentTimeMillis();
               String hostname = reachable ? getHostName(ip) : "N/A";
               results.add(new DeviceInfo(ip, hostname, reachable, end - start));
           } catch (Exception e) {
               results.add(new DeviceInfo(ip, "Error", false, -1));
           }
       }
       return results;
   }
   private String getHostName(String ip) {
       try {
           Process process = Runtime.getRuntime().exec("nslookup " + ip);
           BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
           String line;
           while ((line = reader.readLine()) != null) {
               if (line.contains("name =")) {
                   return line.split("name =")[1].trim();
               }
           }
       } catch (Exception e) {
           return "N/A";
       }
       return "N/A";
   }
}
