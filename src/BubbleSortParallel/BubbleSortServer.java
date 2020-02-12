package BubbleSortParallel;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

class BubbleSortThread extends Thread {
	private int cores, chunkSize;
	private int targetArray[];
	
	public BubbleSortThread(int[] arr, int cores, int chunkSize) {
		targetArray = arr;
		this.cores = cores;
		this.chunkSize = chunkSize;
	}

	public void run() {		
		int n = targetArray.length;
		
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (targetArray[j] > targetArray[j+1]) {
                    int temp = targetArray[j];
                    targetArray[j] = targetArray[j+1];
                    targetArray[j+1] = temp;
                }
			}
		}
       
        //System.out.println("thread no:"+cores);
        //for(int i=0;i<n;i++)System.out.print(" "+targetArray[i]);
        
	}
}



public class BubbleSortServer {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		
		int numberRange = Integer.parseInt(args[0]);
		int[] arr = new int[numberRange];
		long start, stop, timeDifference;
		int n, count = numberRange;
				int cores, splitSize, remainder;
				ServerSocket listener = new ServerSocket(9090);
				Thread threadArray[];
				
				// inserting elements in reverse order 100,99,98...
				for (int i = 0; i < numberRange; i++) { 
					arr[i] = count; 
					count--; 
				}	
		
				//use cores=1 to verify the output
				cores = Integer.parseInt(args[1]);//Runtime.getRuntime().availableProcessors(); //4
				// 100/4=25   100%4=0
				splitSize = (int)numberRange/(cores); //25 in each thread
				remainder = (int) numberRange%cores; //equally distributed across threads if 0
				System.out.println("cores:"+cores+"\n splitSize:"+splitSize);
				//creating array of threads
				threadArray = new Thread[cores];
				
				start = System.currentTimeMillis();
						
				for(int i = 0; i < cores; i++) {
					BubbleSortThread t;
					int s = i*splitSize;  
					//creating subArray from 0-25 elements, then 25-50....
					int[] subArray = Arrays.copyOfRange(arr, s, s + splitSize);
					
					t = new BubbleSortThread(subArray, i, splitSize);
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
				n = arr.length;
				
				// 2%11=1 some splitSize is remaining to search for key

				if (remainder != 0 ) {
					for (int i = 0; i < n-1; i++) {
				
		            for (int j = 0; j < n-i-1; j++) {
		                if (arr[j] > arr[j+1]) {
		                    int temp = arr[j];
		                    arr[j] = arr[j+1];
		                    arr[j+1] = temp;
		                }
					}
				}
				}
				stop = System.currentTimeMillis();
				timeDifference = stop - start;
				
				
		
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

//five threads test[0], test[1] etc. are not thread objects; they are reference variables. 
//That is, test[] is an array of reference variables of Test class but not array objects Test class. 
//They must be converted into array objects
//new Test() converts each reference variable into array object. When converted into object,
//start() method is called on the thread object so that it calls the callback method run().