import java.util.*;
import java.io.*;
public class E {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String s = br.readLine();
		int possSp = Integer.parseInt(br.readLine());
		int[] possAl = new int[26];
		String[] input = br.readLine().split(" ");
		for (int i = 0; i < 26; i++) 
			possAl[i] = Integer.parseInt(input[i]);
		
		int needSp = 0;
		int[] needAl = new int[26];
		char curr = 0, last = 0;
		for (int i = 0; i < s.length(); i++) {
			curr = s.charAt(0);
			if (i == 0) {
				last = curr;
				needAl[curr <= 'Z' ? curr - 'A' : curr - 'a']++;
			}
			else {
				curr = s.charAt(i);
				if (curr == last)
					continue;
				else if (curr == ' ') {
					needSp++;
					last = curr;
					continue;
				}
				else {
					needAl[curr <= 'Z' ? curr - 'A' : curr - 'a']++;
					last = curr;
				}
			}
		}
		
		String[] title = s.split(" ");
		for (int i = 0; i < title.length; i++)
			title[i] = title[i].toUpperCase();
		
		for (int i = 0; i < title.length; i++) {
			if (title[i].length() == 0)
				continue;
			else {
				curr = title[i].charAt(0);
				if (i == 0) {
					last = curr;
					needAl[curr - 'A']++;
				}
				else {
					if (curr == last)
						continue;
					else {
						last = curr;
						needAl[curr - 'A']++;
					}
				}
			}
		}
		boolean chk = true;
		for (int i = 0; i < 26; i++) {
			if (needAl[i] > 0)
				if (possAl[i] < needAl[i]) {
					chk = false;
					break;
				}
		}
		if (possSp >= needSp && chk) {
			for (int i = 0; i < title.length; i++) {
				if (title[i].length() == 0)
					continue;
				sb.append(title[i].charAt(0));
			}
		}
		else {
			sb.append(-1);
		}
		
		System.out.println(sb);
	}
}