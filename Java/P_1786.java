import java.util.*;
import java.io.*;
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String t = br.readLine();
		String p = br.readLine();
		
		ArrayList<Integer> al = kmp(t, p);
		sb.append(al.size() + "\n");
		for (int i : al)
			sb.append(i + " ");
		System.out.println(sb);
	}
	public static ArrayList<Integer> kmp(String origin, String pattern) {
		ArrayList<Integer> list = new ArrayList<>();
		int[] fail = new int[pattern.length()];
		int j = 0;
		for (int i = 1; i < pattern.length(); i++) {
			while (j > 0 && pattern.charAt(i) != pattern.charAt(j))
				j = fail[j - 1];
			if (pattern.charAt(i) == pattern.charAt(j))
				fail[i] = ++j;
		}
		
		j = 0;
		for (int i = 0; i < origin.length(); i++) {
			while (j > 0 && origin.charAt(i) != pattern.charAt(j))
				j = fail[j - 1];
			if (origin.charAt(i) == pattern.charAt(j)) {
				if (j == pattern.length() - 1) {
					list.add(i - j + 1);
					j = fail[j];
				}
				else
					j++;
			}
		}
		return list;
	}
}
// 2021-01-02 23:19 
// KMP 알고리즘
