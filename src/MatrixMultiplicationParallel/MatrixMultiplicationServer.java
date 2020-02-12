package MatrixMultiplicationParallel;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class MatrixMulThread extends Thread {
	private int cores, splitSize, range;
	private int[][] arr, res;
	
	public MatrixMulThread(int[][] arr, int[][]res, int cores, int splitSize, int range) {
		this.arr = arr;
		this.res = res;
		this.cores = cores;
		this.splitSize = splitSize;
		this.range = range;
	}

	public void run() {		
		for (int i = cores*splitSize; i < cores*splitSize + splitSize; i++) {
			for (int j = 0; j < range; j++) {
				for (int k = 0; k < range; k++) {
					res[i][j] = res[i][j] + arr[i][k]*arr[k][j];
				}
			}
		}
	}
}


public class MatrixMultiplicationServer {
	
	public static void main(String[] args) throws IOException {		
		int numberRange = Integer.parseInt(args[0]);
		int[][] arr = new int[numberRange][numberRange];
		int[][] result = new int[numberRange][numberRange];
		int cores, splitSize, remainder;
		long start, stop, timeDifference;
		Thread threadArray[];
		ServerSocket listener = new ServerSocket(9090);

		// array prep
		for (int i = 0; i < numberRange; i++) {
			for (int j = 0; j < numberRange; j++) {
				arr[i][j] = i*numberRange + j; 
				result[i][j] = 0;
			}			
		}
						
		cores = Integer.parseInt(args[1]);//Runtime.getRuntime().availableProcessors();
		splitSize = (int) numberRange/cores;
		remainder = (int) numberRange%cores;
		threadArray = new Thread[cores];		
		start = System.currentTimeMillis();
		
		for(int i = 0; i < cores; i++) {
			MatrixMulThread t;
			//array,resultant array, thread num, splitSize, matrix size N
			t = new MatrixMulThread(arr, result, i, splitSize, numberRange);
			threadArray[i] = t;
			t.start();			
		}	

		for (int i = 0; i < cores; i++) { 
			try { threadArray[i].join(); } 
			catch (InterruptedException e) { e.printStackTrace(); }	
		}
		
		if (remainder != 0 ) {
			for (int i = cores*splitSize; i < cores*splitSize + remainder; i++) {
				for (int j = 0; j < numberRange; j++) {
					for (int k = 0; k < numberRange; k++) {
						result[i][j] = result[i][j] + arr[i][k]*arr[k][j];
					}
				}
			}
		}
		
		stop = System.currentTimeMillis();
		timeDifference = stop - start;		
		
		/*
		System.out.println("input matrix");
		for (int i = 0; i < numberRange; i++) {
			for (int j = 0; j < numberRange; j++) {
				System.out.print(A[i][j] + "\t");
			}			
			System.out.println();
		}
		
		System.out.println("result");
		for (int i = 0; i < numberRange; i++) {
			for (int j = 0; j < numberRange; j++) {
				System.out.print(result[i][j] + "\t");
			}			
			System.out.println();
		}
		
		*/
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

