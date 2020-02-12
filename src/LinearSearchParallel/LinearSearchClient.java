package LinearSearchParallel;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
public class LinearSearchClient {

	public static void main(String[] args) throws IOException, UnknownHostException {
		// TODO Auto-generated method stub
		String serverAddress = "127.0.0.1";
        Socket socket = new Socket(serverAddress, 9090);
		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String serverResponse = input.readLine();
		System.out.println("Time taken for server response ns:" + serverResponse );
        socket.close();
		System.exit(0);
    }
}