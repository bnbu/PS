import java.util.*;
import java.io.*;
public class A {
	static final char[] mobis = {'M', 'O', 'B', 'I', 'S'};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		boolean[] b = new boolean[26];
		String s = br.readLine();
		for (char c : s.toCharArray()) {
			b[c - 'A'] = true;
		}
		
		boolean ans = true;
		for (char c : mobis) ans &= b[c - 'A'];
		System.out.println(ans ? "YES" : "NO");
	}
}
// A번
// 받은 문자열에 M O B I S가 있는지만 체크하면 끝
