package LinearSearchParallel;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;


class LinearSearchThread extends Thread {
	private int  key, cores, splitSize;
	private int targetArray[];
	
	public LinearSearchThread(int[] arr, int key, int cores, int splitSize) {
		targetArray = arr;
		this.key = key;
		this.cores = cores;
		this.splitSize = splitSize;
	}

	public void run() {
		for (int i = 0; i < targetArray.length; i++) {
			if (key == targetArray[i]) {
				//if found on core 2 , 2*25+i
				int pos = cores*splitSize + i;
				System.out.println("key is obtained at: " + pos);
				break;
			}
		}
	}
}
public class LinearSearchServer {

	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//reading search space size for linear search
		int numberRange = Integer.parseInt(args[0]);
		System.out.println("Numbers Range:"+numberRange);
		int[] arr= new int[numberRange];
		int key;
		int cores, splitSize, remainder;
		ServerSocket listener = new ServerSocket(9090);
		long start, stop, timeDifference;
		Thread threadArray[];
				
		//inserting elements 1,2,3,..... into search space
		for (int i = 0; i < numberRange; i++) {
				arr[i] = i + 1; 
		}
		//randomly selecting a key 
		//select last element of key for O(N)? or random
		key=numberRange-1;
		//key = random.nextInt(numberRange);	
		System.out.println("Randomly generated key is: " + key);
		
		cores = Integer.parseInt(args[1]);//Runtime.getRuntime().availableProcessors(); //4
		// 100/4=25   100%4=0
		splitSize = (int)numberRange/(cores); //25 in each thread
		remainder = (int) numberRange%cores; //equally distributed across threads if 0
		System.out.println("cores:"+cores+"\n splitSize:"+splitSize);
		//creating array of threads
		threadArray = new Thread[cores];
		
		//start = System.nanoTime();
		start=System.currentTimeMillis();
				
		for(int i = 0; i < cores; i++) {
			LinearSearchThread t;
			int s = i*splitSize;  
			//creating subArray from 0-25 elements, then 25-50....
			int[] subArray = Arrays.copyOfRange(arr, s, s + splitSize);
			
			t = new LinearSearchThread(subArray, key, i, splitSize);
			//executing thread run() on 0'th thread
			threadArray[i] = t;
			t.start();
		}	

		for (int i = 0; i < cores; i++) { 
			try { 
				threadArray[i].join(); //bring back the values from thread[0]
			} 
			catch (InterruptedException e) { e.printStackTrace(); }	
		}
		
		// 2%11=1 some splitSize is remaining to search for key
		if (remainder != 0 ) {
			for (int i = cores*splitSize; i < cores*splitSize + remainder; i++) {
				if (key == arr[i]) {
					int pos = cores*splitSize + i;
					System.out.println("key is obtained at: " + pos);
					break;
				}
			}
		}
		
		//stop = System.nanoTime();
		stop=System.currentTimeMillis();
		timeDifference = stop - start;
		
		//clean up code
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					out.println(timeDifference+" ms");					
                } finally { socket.close(); }
            }
        }		
        finally { listener.close(); }
    }
}


