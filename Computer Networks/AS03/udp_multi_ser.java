import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;

public class udp_multi_ser
{
	
	public static void main(String args[])throws Exception
	{
		try{
		DatagramSocket s = new DatagramSocket(1025);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter number of clients: ");
		int n = scanner.nextInt();
		scanner.nextLine();
		for(int i = 0; i < n; i++) {
			Thread t  = new Multiple(s);
			t.start();
		}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class Multiple extends Thread {
	
	private DatagramSocket s;
	private BufferedReader br;
	private InetAddress inetAddress;
	private String addr; 
	private int portno;
	public Multiple(DatagramSocket servSock) throws Exception {
		s = servSock;
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter IP Address Of Client: ");
		addr = br.readLine();
		inetAddress = InetAddress.getByName(addr);
		System.out.println("Enter port number of client: ");
		portno = Integer.parseInt(br.readLine());
	}
	
	public void run() {
		
		try{
		String str1=" ";
		while(!str1.equals("Bye"))
		{
			byte[] buf=new byte[20];
			byte[] buf1=new byte[20];
			DatagramPacket p2=new DatagramPacket(buf1,buf1.length);
			s.receive(p2);
			buf1=p2.getData();
			String str2=new String(buf1,StandardCharsets.UTF_8);
			System.out.println("Client says : " +str2);
			str1=br.readLine();
			buf=str1.getBytes();
		
			DatagramPacket p1=new DatagramPacket(buf,buf.length,inetAddress,portno);
			s.send(p1);
		}
		}
		catch(Exception e) {
			e.printStackTrace();	
		}

	}
}
