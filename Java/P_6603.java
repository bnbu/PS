import java.util.*;

public class Main {
	static public void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		while(true) {
			String[] inputs = scan.nextLine().split(" ");
			
			if (inputs[0].equals("0"))
				break;
			
			int[] arr = new int[Integer.parseInt(inputs[0])];
			
			for (int i = 0; i < arr.length; i++) {
				arr[i] = Integer.parseInt(inputs[i + 1]);
			}
			
			int[] passIndex = new int[arr.length - 6];
			
			for (int i = 0; i < passIndex.length; i++) {
				passIndex[i] = 6 + i;
			}
			
			while(true) {
				for (int i = 0; i < arr.length; i++) {
					boolean overlapped = false;
					for (int j = 0; j < passIndex.length; j++) {
						if (i == passIndex[j]) {
							overlapped = true;
							break;
						}
					}
					if (overlapped) {
						continue;
					}
					System.out.print(arr[i] + " ");
				}
				
				boolean check = true;
				for (int i = 0; i < passIndex.length - 1; i++) {
					if (passIndex[i] + 1 != passIndex[i + 1]) {
						check = false;
						break;
					}
				}
				
				if (check && passIndex[0] == 0) {
					break;
				}
				else if (check && passIndex[0] != 0) {
					passIndex[0] -= 1;
					for (int i = 1; i < passIndex.length; i++) {
						passIndex[i] = 6 + i;
					}
				}
				else {
					int index = passIndex.length - 1;
					for (int i = passIndex.length - 1; i > 0; i--) {
						if (passIndex[i] - 1 != passIndex[i - 1]) {
							index = i;
							break;
						}
					}
					
					passIndex[index] -= 1;
					for (int i = index + 1; i < passIndex.length; i++) {
						passIndex[i] = 6 + i;
					}
				}
				
				
				System.out.println();
			}
			System.out.println("\n");
		}
	}
}
//2020 08 09 04:37 해결

