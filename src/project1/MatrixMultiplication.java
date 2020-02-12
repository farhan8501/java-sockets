package project1;

import java.util.Random;

public class MatrixMultiplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int sizeOfMatrix = Integer.parseInt(args[0]);
		int[][] arr1 = new int[sizeOfMatrix][sizeOfMatrix];
		int[][] arr2 = new int[sizeOfMatrix][sizeOfMatrix];
		int[][] result = new int[sizeOfMatrix][sizeOfMatrix];
		long start, stop, timeDifference;
		Random random=new Random();
		
		//populating matrix with random numbers
		for (int i = 0; i < sizeOfMatrix; i++) {
			for (int j = 0; j < sizeOfMatrix; j++) {
				arr1[i][j] = random.nextInt(10);
				arr2[i][j] = random.nextInt(10);
				result[i][j] = 0;
			}			
		}
						
		start = System.currentTimeMillis();
		//multiplication of matrix
		for (int i = 0; i < sizeOfMatrix; i++) {
			for (int j = 0; j < sizeOfMatrix; j++) {
				for (int k = 0; k < sizeOfMatrix; k++) {
					result[i][j] +=  arr1[i][k]*arr2[k][j];
				}
			}
		}
				
		stop = System.currentTimeMillis();
		timeDifference = stop - start;		
		System.out.println("Time taken for Matrix Multiplication in milliseconds:"+timeDifference);
		
		/*//printing the results
		for (int i = 0; i < sizeOfMatrix; i++) {
			for (int j = 0; j < sizeOfMatrix; j++) {
				System.out.print(result[i][j] + " ");
			}	
			System.out.println();//new line    

		}*/
		

	}

}
