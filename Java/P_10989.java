import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int[] arr = new int[10001];
		
		int size = Integer.parseInt(br.readLine());
		
		for (int i = 0; i < size; i++) {
			int num = Integer.parseInt(br.readLine());
			arr[num]++;
		}
		
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i]; j++) {
				sb.append(i + "\n");
			}
		}
		
		System.out.print(sb);
	}
}
