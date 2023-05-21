import java.util.*;
import java.io.*;
public class D {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine());
		
		String[] str = new String[n];
		for (int i = 0; i < n; i++) str[i] = br.readLine();
		
		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				for (int k = 0; k < str[i].length(); k++) {
					String sub1 = str[i].substring(str[i].length() - 1 - k, str[i].length()),
							sub2 = str[i].substring(0, k + 1);
					
					if (str[j].length() - 1 < k) break;
					String sub3 = str[j].substring(0, k + 1),
							sub4 = str[j].substring(str[j].length() - 1 - k, str[j].length());
					
					if (sub1.equals(sub3) || sub2.equals(sub4)) {
						ans++;
						break;
					}
				}
			}
		}
		System.out.println(ans);
	}
}
// N 최대 100, 각 문자열 길이 최대 20
// 완전탐색 했음