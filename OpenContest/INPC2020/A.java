import java.util.*;
import java.io.*;
public class A {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		String[] input = br.readLine().split(" ");
		int n = Integer.parseInt(input[0]);
		int m = Integer.parseInt(input[1]);
		
		if (m == 1 || m == 2)
			sb.append("NEWBIE!");
		else if (m <= n && m > 2)
			sb.append("OLDBIE!");
		else
			sb.append("TLE!");
		
		System.out.println(sb);
	}
}
// 당시 기준 광탈 방지 문제
