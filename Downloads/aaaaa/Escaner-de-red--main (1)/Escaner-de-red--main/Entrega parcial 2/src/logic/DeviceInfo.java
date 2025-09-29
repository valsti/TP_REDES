package logic;
public class DeviceInfo {
   private String ip;
   private String hostname;
   private boolean connected;
   private long responseTime;
   public DeviceInfo(String ip, String hostname, boolean connected, long responseTime) {
       this.ip = ip;
       this.hostname = hostname;
       this.connected = connected;
       this.responseTime = responseTime;
   }
   public String getIp() { return ip; }
   public String getHostname() { return hostname; }
   public boolean isConnected() { return connected; }
   public long getResponseTime() { return responseTime; }
}
