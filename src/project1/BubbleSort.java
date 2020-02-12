package project1;

public class BubbleSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int numbersRange = Integer.parseInt(args[0]);
		int[] arr = new int[numbersRange];
		long start, stop, timeDifference;
		int n, count = numbersRange;
		
		// inserting elements in reverse order 100,99,98...
		for (int i = 0; i < numbersRange; i++) { 
			arr[i] = count; 
			count--; 
		}		
				
		start = System.currentTimeMillis();
		n = arr.length;
		
		//sorting the elements O(n2)
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (arr[j] > arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
			}
		}
	  
		stop = System.currentTimeMillis();
		timeDifference = stop - start;
		
		//display the output of bubble sort
		 /*for (int i = 0; i < numbersRange; i++) {
			 System.out.println(arr[i] + " "); 
		}*/		
		System.out.println("Time taken to sort the elements in Milli seconds:"+timeDifference);

	}

}
