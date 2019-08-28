package org.ninestar.im.utils;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IPAddress {
	
	static List<IP> ip4s = new ArrayList<IP>();
	static List<IP> ip6s = new ArrayList<IP>();
	static Set<String> ip4Strs = new HashSet<String>();
	
	static Map<String, IP> ip4Map = new HashMap<String, IP>(64);
	static Map<String, IP> ip6Map = new HashMap<String, IP>(64);
	
	static{
		Enumeration<NetworkInterface> allNetInterfaces = null;  
      try {  
          allNetInterfaces = NetworkInterface.getNetworkInterfaces();  
      } catch (java.net.SocketException e) {  
          e.printStackTrace();  
      }  
      while (allNetInterfaces.hasMoreElements())  
      {  
          NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();  
          String interfaceName = netInterface.getName();
          Enumeration<InetAddress> addresses = netInterface.getInetAddresses();  
          while (addresses.hasMoreElements())  
          {  
        	  InetAddress inetAddress = (InetAddress) addresses.nextElement();  
              if(inetAddress == null){
            	  
              }else if (inetAddress instanceof Inet4Address){  
            	  IP ip = new IP(inetAddress.getHostAddress(), interfaceName, inetAddress, 4);
                  ip4s.add(ip);
                  ip4Map.put(interfaceName, ip);
                  ip4Strs.add(ip.getIp());
              }else if(inetAddress instanceof Inet6Address){
            	  IP ip = new IP(inetAddress.getHostAddress(), interfaceName, inetAddress, 6);
            	  ip6s.add(ip);
            	  ip6Map.put(interfaceName, ip);
              }
          }  
      }  
	}
	public static boolean existIp(String ip) {
		return ip4Strs.contains(ip);
	}
	public static List<IP> getIp4s() {
		return new ArrayList<IP>(ip4s);
	}
	public static List<IP> getIp6s() {
		return new ArrayList<IP>(ip6s);
	}
	public static IP getIp4(String interfaceName){
		return ip4Map.get(interfaceName);
	}
	public static IP getIp6(String interfaceName){
		return ip6Map.get(interfaceName);
	}
	public static Set<String> getIp4InterfaceNames(){
		return ip4Map.keySet();
	}
	public static Set<String> getIp6InterfaceNames(){
		return ip6Map.keySet();
	}
	
	public static Map<String, IP> getIp6Map(){
		return ip6Map;
	}
	public static Map<String, IP> getIp4Map(){
		return ip4Map;
	}
	public static Map<String, IP> getIpAll(){
		Map<String, IP> re = new HashMap<>();
		re.putAll(ip4Map);
		re.putAll(ip6Map);
		return re;
	}
	
	/**
	 * 获取192.168 开头的ip
	 * @return
	 */
	public static String getIP192168(){
		String ipr = "";
		for (IP ip : ip6s) {
			if (ip.getIp().startsWith("192.168")) {
				ipr = ip.getIp();
			}
		}
		return ipr;
	}
	
	public static class IP{
		private String ip;
		private String interfaceName;
		private InetAddress inetAddress;
		private int ipType = 4;
		public IP(String ip, String interfaceName, InetAddress inetAddress,
				int ipType) {
			this.ip = ip;
			this.interfaceName = interfaceName;
			this.inetAddress = inetAddress;
			this.ipType = ipType;
		}
		public String getIp() {
			return ip;
		}
		public String getInterfaceName() {
			return interfaceName;
		}
		public InetAddress getInetAddress() {
			return inetAddress;
		}
		public int getIpType() {
			return ipType;
		}
		@Override
		public String toString() {
			return ip;
		}
	}
	public static void main(String[] args) {
		System.out.println(getIp4s());
	}
}


