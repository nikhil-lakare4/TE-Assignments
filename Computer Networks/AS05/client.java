import java.io.*;
import java.net.*;
import java.math.*;
import java.util.*;

public class client {

	public static void main(String[] args) throws Exception {

		int j;

		Socket S = new Socket("localhost", 5001);
		DataOutputStream dout = new DataOutputStream(S.getOutputStream());
		BufferedInputStream din = new BufferedInputStream(S.getInputStream());
		Scanner SC = new Scanner(System.in);

		System.out.println("Enter number of frames to be recieved from Server: ");
		int n = SC.nextInt();
		dout.write(n);
		System.out.println("Enter Communication Mode - 0 for No error and 1 for with error : ");
		int choice = SC.nextInt();
		dout.write(choice);
		if (choice == 0) {
			for (int i = 0; i < n; i++) {

				j = din.read();
				System.out.println(j + "th Frame Recieved");
				dout.write(j);
				System.out.println("Acknoledgement for " + j + "th frame sent");

			}

		} else {

			int check = 0;

			for (int i = 0; i < n; i++) {
				
				j = din.read();
				if (check == j) {
					System.out.println("Acknoledgement for " + j + "th frame sent");
					dout.write(j);
					check++;

				} else {
					System.out.println("Negative Acknoledgement sent");
					i--;
					dout.write(-1);

				}

			}

		}

		S.close();
		SC.close();

	}

}
