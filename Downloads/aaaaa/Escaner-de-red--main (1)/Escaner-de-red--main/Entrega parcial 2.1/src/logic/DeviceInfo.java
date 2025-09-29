package logic;
public class DeviceInfo {
   private String ip;
   private String hostname;
   private String connected;
   private long responseTime;
   
   public DeviceInfo(String ip, String hostname, String connected, long responseTime) {
       this.ip = ip;
       this.hostname = hostname;
       this.connected = connected;
       this.responseTime = responseTime;
   }
   public String getIp() { return ip; }
   public String getHostname() { return hostname; }
   public String getConnected() { return connected; }
   public long getResponseTime() { return responseTime; }
}
