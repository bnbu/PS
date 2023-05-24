import java.util.*;
import java.io.*;
public class H {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Scanner scan = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		
		String[] s = br.readLine().split(" "); 
		int n = Integer.parseInt(s[0]), m = Integer.parseInt(s[1]);
		int[] field = new int[n];
		s = br.readLine().split(" ");
		for (int i = 0; i < n; i++)
			field[i] = Integer.parseInt(s[i]);
		
		while (m-- > 0) {
			s = br.readLine().split(" ");
			int a = Integer.parseInt(s[0]), b = Integer.parseInt(s[1]),
				k = Integer.parseInt(s[2]);
			
			for (int i = a - 1; i < b; i++)
				field[i] += k;
		}
		
		for (int i : field)
			sb.append(i + " ");
		
		System.out.println(sb);
	}
}