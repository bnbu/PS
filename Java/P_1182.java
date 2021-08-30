import java.util.*;

public class Main {
	static public void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		String[] inputs = scan.nextLine().split(" ");
		
		int length = Integer.parseInt(inputs[0]);
		int sum = Integer.parseInt(inputs[1]);
		int partLength = 1;
		int cnt = 0;
		
		inputs = scan.nextLine().split(" ");
		int[] arr = new int[length];
		
		for (int i = 0; i < arr.length; i++) {
			arr[i] = Integer.parseInt(inputs[i]);
		}
		
		while (true) {
			int[] partIndex = new int[partLength]; 
			for (int i = 0; i < partLength; i++) {
				partIndex[i] = (arr.length - partLength) + i;
			}
			
			while (true) {
				int subTotal = 0;
				for (int i = 0; i < arr.length; i++) {
					boolean overlapped = false;
					for (int j = 0; j < partIndex.length; j++) {
						if (i == partIndex[j]) {
							overlapped = true;
							break;
						}
					}
					if (overlapped) {
						subTotal += arr[i];
						//System.out.print(arr[i] + " ");
					}
				}
				//System.out.print("=> " + subTotal + "\n");
				
				if (subTotal == sum)
					cnt++;
				
				boolean check = true;
				for (int i = 0; i < partIndex.length - 1; i++) {
					if (partIndex[i] + 1 != partIndex[i + 1]) {
						check = false;
						break;
					}
				}
				
				if (check && partIndex[0] == 0) {
					break;
				}
				else if (check && partIndex[0] != 0) {
					partIndex[0] -= 1;
					for (int i = 1; i < partIndex.length; i++) {
						partIndex[i] = (arr.length - partLength) + i;
					}
				}
				else {
					int index = partIndex.length - 1;
					for (int i = partIndex.length - 1; i > 0; i--) {
						if (partIndex[i] - 1 != partIndex[i - 1]) {
							index = i;
							break;
						}
					}
					
					partIndex[index] -= 1;
					for (int i = index + 1; i < partIndex.length; i++) {
						partIndex[i] = (arr.length - partLength) + i;
					}
				}
 			}
			
			if (partLength == length) {
				break;
			}
			else {
				partLength += 1;
			}
		}
		
		System.out.println(cnt);
	}
}
// 2020 08 09 19:08 해결
