import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		br.readLine();		
		HashMap<Integer, Integer> hm = new HashMap<>();

		String[] input = br.readLine().split(" ");
		for (int i = 0; i < input.length; i++) {
			int temp = Integer.parseInt(input[i]);
			
			if (hm.containsKey(temp)) {
				hm.put(temp, hm.get(temp) + 1);
			}
			else
				
				hm.put(temp, 1);
		}
		br.readLine();
		input = br.readLine().split(" ");
		for (int i = 0; i < input.length; i++) {
			int temp = Integer.parseInt(input[i]);
			
			if (hm.containsKey(temp)) 
				sb.append(hm.get(temp) + " ");
			else
				sb.append(0 + " ");
		}
		
		System.out.println(sb);
	}
}
