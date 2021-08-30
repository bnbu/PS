import java.util.*;
public class Main {	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int[] arr;
		int[][] status = new int[n][n];
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				status[i][j] = scan.nextInt();
		
		for (int j = 2; j <= n / 2; j++) {
			arr = new int[j];
			for (int i = 0; i < arr.length; i++)
				arr[i] = n - j + i;
			
			while (true) {
				int[] start = new int[n - arr.length];
				int idx = 0;
				for (int i = 0; i < n; i++) {
					boolean overlapped = false;
					for (int k = 0; k < arr.length; k++) {
						if (i == arr[k]) {
							overlapped = true;
							break;
						}
					}
					if (overlapped)
						continue;
					start[idx++] = i;
				}

				int linkSum = 0, startSum = 0;
				int[] duo = new int[2];
				for (int i = 0; i < 2; i++)
					duo[i] = arr.length - 2 + i;
				while (true) {
					for (int i = 0; i < duo.length; i++)
						linkSum += status[arr[duo[0]]][arr[duo[1]]] + status[arr[duo[1]]][arr[duo[0]]];

					if ((duo[0] + 1 == duo[1]) && duo[0] == 0) {
						break;
					} else if ((duo[0] + 1 == duo[1]) && duo[0] != 0) {
						duo[0] -= 1;
						duo[1] = arr.length - 1;
					} else {
						duo[1] -= 1;
					}

				}

				for (int i = 0; i < 2; i++)
					duo[i] = start.length - 2 + i;
				while (true) {
					for (int i = 0; i < duo.length; i++)
						startSum += status[start[duo[0]]][start[duo[1]]] + status[start[duo[1]]][start[duo[0]]];

					if ((duo[0] + 1 == duo[1]) && duo[0] == 0) {
						break;
					} else if ((duo[0] + 1 == duo[1]) && duo[0] != 0) {
						duo[0] -= 1;
						duo[1] = start.length - 1;
					} else {
						duo[1] -= 1;
					}
				}
			min = min <= (Math.abs(linkSum - startSum)) ? min : (Math.abs(linkSum - startSum));
			
			boolean check = true;
			for (int i = 0; i < arr.length - 1; i++) {
				if (arr[i] + 1 != arr[i + 1]) {
					check = false;
					break;
				}
			}
			
			if (check && arr[0] == 0) {
				break;
			}
			else if (check && arr[0] != 0) {
				arr[0] -= 1;
				for (int i = 1; i < arr.length; i++) {
					arr[i] = n - j + i;
				}
			}
			else {
				int index = arr.length - 1;
				for (int i = arr.length - 1; i > 0; i--) {
					if (arr[i] - 1 != arr[i - 1]) {
						index = i;
						break;
					}
				}
				
				arr[index] -= 1;
				for (int i = index + 1; i < arr.length; i++) {
					arr[i] = n - j + i;
				}
			}
			}
		}
		System.out.println(min / 2);
	}
}
//2020-09-15 해결
// 백트레킹은 전부 다 뒤져서 시간초과 뜬듯;
// 이건 딱 필요한 부분만 뒤짐 (그래도 같은곳 2번 뒤지는건 해결해봐야할 숙제)
