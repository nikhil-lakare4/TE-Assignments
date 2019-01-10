import java.io.*;
import java.net.*;
import java.math.*;
import java.util.*;

public class server {

	public static void main(String[] args) throws Exception {

		int j;
		boolean f[] = new boolean[20];

		ServerSocket SS = new ServerSocket(5001);
		Socket S = SS.accept();
		DataOutputStream dout = new DataOutputStream(S.getOutputStream());
		BufferedInputStream din = new BufferedInputStream(S.getInputStream());
		Scanner SC = new Scanner(System.in);

		int p = din.read();
		int pc = din.read();

		if (pc == 0) {

			for (int i = 0; i < p; i++) {

				dout.write(i);
				System.out.println(i + "th frame sent");
				System.out.println("Waiting for acknoledgement......");
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
					System.out.println("Exception Caught!");
				}

				int a = din.read();
				System.out.println("Acknolegdement Recieved for " + a + "th frame");

			}

		} else {

			for (int i = 0; i < p; i++) {

				if (i == 2) {
					System.out.println("Failed to send " + i + "th frame");
				} else {
					dout.write(i);
					System.out.println(i + "th frame sent");
					
					try {
						Thread.sleep(2000);
					} catch (Exception e) {
						System.out.println("Exception Caught!");
					}

					int a = din.read();
					
					if(a == 255) {
						f[i] = false;
						System.out.println("-ve Ackowledgement Recieved");
					} else {
						f[i] = true;
						System.out.println("Acknolegdement Recieved for " + a + "th frame");
					}

				}


			}
			
			for (int i = 0; i < p; i++) {

				if(f[i] == false) {
					dout.write(i);
					System.out.println(i + "th frame resent");
					
					try {
						Thread.sleep(2000);
					} catch (Exception e) {
						System.out.println("Exception Caught!");
					}

					int a = din.read();
					System.out.println("Acknolegdement Recieved for " + a + "th frame");
				}

			}

		}
		
		SS.close();
		S.close();
		SC.close();

	}

}