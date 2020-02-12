package project1;

import java.util.Random;

public class LinearSearch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//reading search space size for linear search
		int numberRange = Integer.parseInt(args[0]);
		System.out.println("Numbers Range:"+numberRange);
		int[] arr= new int[numberRange];
		int key;
		Random random = new Random();
		long start, stop, timeDifference;
		//inserting elements 1,2,3,..... into search space
		for (int i = 0; i < numberRange; i++) {
			arr[i] = i + 1; 
		}
		
		//randomly selecting a key 
		//select last element of key for O(N)? or random
		key = random.nextInt(numberRange);	
		System.out.println("Randomly generated key is: " + key);
		
		start = System.currentTimeMillis();//nanoTime();
		//lookout for key in search space
		for (int i = 0; i < arr.length; i++) {
			if (key == arr[i]) {
				System.out.println("Found Key at: " + i);
				break;
			}
		}
		
		stop = System.currentTimeMillis();//nanoTime();
		timeDifference = stop - start;		
		System.out.println("Time taken for Linear Search in Nano Seconds:"+timeDifference);

	}

}
