import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int num = scan.nextInt();
		int[] arr1 = new int[num];
		int[] arr2 = new int[num];
		//Integer[] arr2 = new Integer[num];
		
		for (int i = 0; i < num; i++)
			arr1[i] = scan.nextInt();
		
		for (int i = 0; i < num; i++)
			arr2[i] = scan.nextInt();
		
		Arrays.sort(arr1);
		Arrays.sort(arr2);
		//Arrays.sort(arr2, Collections.reverseOrder());
		
		int sum = 0;
		for (int i = 0; i < num; i++) {
			sum += arr1[i] * arr2[num - 1 - i];
		}
		
		System.out.println(sum);
	}
}
