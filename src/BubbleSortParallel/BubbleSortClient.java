package BubbleSortParallel;
import java.io.IOException;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class BubbleSortClient {

	public static void main(String[] args) throws IOException {
        String serverAddress = "127.0.0.1";
        Socket s = new Socket(serverAddress, 9090);
		BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String serverResponse = input.readLine();
		System.out.println("Time taken for server response ms:" + serverResponse );
        s.close();
		System.exit(0);
    }
}
