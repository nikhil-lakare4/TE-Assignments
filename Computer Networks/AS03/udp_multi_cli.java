import java.io.*;
import java.net.*;
import java.nio.charset.*;
public class udp_multi_cli
{
public static void main(String args[])throws Exception
{

BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
System.out.println("Enter port number of client: ");
int portno = Integer.parseInt(br.readLine());
DatagramSocket s=new DatagramSocket(portno);//for multiuser give same port nos to client machine
InetAddress addr=InetAddress.getByName("localhost");//IP address of server
String str1=" ",str2=" ";
while(!str1.equals("Bye"))
{
byte[] buf=new byte[100];
byte[] buf1=new byte[100];
str1=br.readLine();
buf=str1.getBytes();
DatagramPacket p1=new DatagramPacket(buf,buf.length,addr,1025);//port no of server
s.send(p1);

DatagramPacket p2=new DatagramPacket(buf1,buf1.length);
s.receive(p2);
buf1=p2.getData(); 
str2=new String(buf1,StandardCharsets.UTF_8);
System.out.println("Server says : " +str2);
}
}
}
