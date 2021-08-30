import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		int num = scan.nextInt();
		int[] arr1 = new int[num];
		
		for (int i = 0; i < num; i++)
			arr1[i] = scan.nextInt();
		
		Arrays.sort(arr1);
		
		num = scan.nextInt();
		int[] arr2 = new int[num];
		
		for (int i = 0; i < num; i++)
			arr2[i]	= scan.nextInt();
		
		for (int i = 0; i < num; i++) {
			if (Arrays.binarySearch(arr1, Integer.valueOf(arr2[i])) < 0)
				sb.append(0 + " ");
			else
				sb.append(1 + " ");
		}
		System.out.print(sb);
	}
}
