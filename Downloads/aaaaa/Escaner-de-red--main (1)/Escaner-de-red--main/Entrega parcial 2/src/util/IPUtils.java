package util;

import java.net.InetAddress; //Proporciona métodos para obtener la dirección IP de cualquier nombre de host.
import java.util.ArrayList; //es una implementación de array redimensionable.
import java.util.stream.Collectors;

import logic.IPRangeOutofBoundsException;

public class IPUtils {
	
    public static boolean isValidIP(String ip) { //Sirve para comprobar si lo que se escribio es una IP
        try {
            InetAddress.getByName(ip);
            return ip.matches("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b"); //Expresión regular para ver que haya un . despues de 3 numeros
        	}
        catch (Exception e) {
            return false;
        }
    }

    public static ArrayList<String> generateIPRange(String startIP, String endIP) throws IPRangeOutofBoundsException {
    	 ArrayList<Integer> ipsStart = new ArrayList<>();
        ArrayList<Integer> ipsEnd = new ArrayList<>();
        ArrayList<String> rango = new ArrayList<>();
      
        String[] start = startIP.split("\\."); //Divide la IP en 4 pedazos separados por el .
        String[] end = endIP.split("\\.");
        
        
        
        for(String numero: start) {
        	ipsStart.add(Integer.parseInt(numero));
        
        }
        
        for(String numero: end) {
        	ipsEnd.add(Integer.parseInt(numero));
        
        }
        
        int cantVuel = 0;
        
        int[]multis = new int[]{16777216,65536,256,1};
        
        for(int i = 3; i >= 0; i--) {
        	int diferencia = ipsEnd.get(i) - ipsStart.get(i);
    	   
    	   
        	if(diferencia > 0) {
        		cantVuel += diferencia*multis[i];
    		   
        	}
    	   
        }
        
        if(cantVuel  > 10000) {
        	throw new IPRangeOutofBoundsException("El rango de IP es muy grande");
        	
        }
        
        for(int i = 0 ; i <= cantVuel ; i++) {
        	rango.add(ipsStart.stream().map(String::valueOf).collect(Collectors.joining(".")));
        	ipsStart.set(3, ipsStart.get(3) + 1);

        	// Si el último número es mayor a 255, se pone en 0, y al siguiente número se le suma 1, y se comprueba lo mismo hasta el número final
        	if (ipsStart.get(3) > 255){
        		
        	    ipsStart.set(3,0);
        	    ipsStart.set(2, ipsStart.get(2) + 1);;
        	    
        	    if (ipsStart.get(2) > 255){
        	    	
        	    	ipsStart.set(2,0);
        	        ipsStart.set(1, ipsStart.get(1) + 1);
        	        
        	        if (ipsStart.get(1) > 255){
        	        	
        	        	ipsStart.set(1,0);
        	            ipsStart.set(0, ipsStart.get(0) + 1); }
        	    }
        	}
        }
        
      
        return rango;
    }
}



