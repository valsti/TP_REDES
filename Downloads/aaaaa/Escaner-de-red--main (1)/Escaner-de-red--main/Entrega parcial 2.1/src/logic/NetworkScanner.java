package logic;
import java.io.BufferedReader; //
import java.io.InputStreamReader; //
import java.net.InetAddress; //
import java.util.ArrayList;
import java.util.List; 

public class NetworkScanner {
	
   public static List<DeviceInfo> scanRange(List<String> ips, int timeout)throws ScanTimeOutException {
       List<DeviceInfo> results = new ArrayList<>();
       
       long start = System.currentTimeMillis();
       
       for (String ip : ips) {
    	   
           try {
        	   
        	  long startPing = System.currentTimeMillis();
        	  
        	   String  reachable = IsReachable(ip);
        	   
        	   long endPing = System.currentTimeMillis();
        	   
               String hostname = getHostName(ip);
                          
               results.add(new DeviceInfo(ip, hostname, reachable, endPing - startPing) );}
           
           catch (Exception e) {
        	 results.add(new DeviceInfo(ip, "Error", "No Conectado", -1));}
           
           
           finally {
        	   long end = System.currentTimeMillis();
        	   if (end - start >= timeout) {
        		   throw new ScanTimeOutException("Se dejará de escanear el rango de IPs. El tiempo de espera esta agotado.");}
           }
       }
       
       return results;
   }
   
   private static String getHostName(String ip) {
       try {
    	   ProcessBuilder pb = new ProcessBuilder("nslookup", ip);
    	   pb.redirectErrorStream(true);
    	   Process process = pb.start();
           
           BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())); //Agarra el resultado de
          
                          
           String resultado = "El nombre no fue encontrado";
           String line;
                        
           while (true)  {
        	   
        	   line = reader.readLine();
  
               if (line.contains("Nombre:")) {
                   return line.split("Nombre:")[1].trim(); //Trim: elimina de izquierda a derecha los espacio
                 
               }
               else if(line.contains("*")) {
            	   break;
               }
                                                 
           }
           return resultado;
           
       } catch (Exception e) {
           return "Error";
       }
      
   }
   private static String IsReachable(String ip) {
       try {
    	   ProcessBuilder pb = new ProcessBuilder("ping -n 1", ip);
    	   pb.redirectErrorStream(true);
    	   Process process = pb.start();
           
           BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())); //Agarra el resultado de
                                    
           String line;
                        
           while (true)  {
        	  
        	   line = reader.readLine();
        	   System.out.println(line);
        	   
               if (line.contains(ip + ": bytes")) {
                   return "Conectado";
                 
               }
               else if(line.contains(ip + ": Host")) {
            	   return "Inaccesible";
               }
               
               else if(line.contains("(100% perdidos)")) {
            	   return "No Conectado";
               }
                                                 
           }
          
       } catch (Exception e) {
           return "Error";
       }
      
   }
}
     