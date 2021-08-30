import java.util.*;
import java.io.*;
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String origin = br.readLine();
		String pattern = br.readLine();
		
		Stack<Character> st = new Stack<>();
		ArrayList<Integer> idx = new ArrayList<>();
		
		for (int i = 0; i < origin.length(); i++) {
			st.push(origin.charAt(i));
			
			if (st.size() < pattern.length())
				continue;
			else {
				boolean chk = true;
				for (int j = 0; j < pattern.length(); j++) {
					if (st.get(st.size() - pattern.length() + j) != pattern.charAt(j)) {
						chk = false;
						break;
					}
				}
				if (chk) 
					for (int j = 0; j < pattern.length(); j++)
						st.pop();
			}
		}
		
		if (st.isEmpty())
			sb.append("FRULA");
		else {
			Queue<Character> q = new LinkedList<>(st);
			while (!q.isEmpty())
				sb.append(q.poll());
		}
		System.out.println(sb);
	}
}
// 2021-01-04 22:38 해결
// 스택에 한 문자씩 넣고
// 폭발 문자열의 길이보다 클 경우, 폭발 문자열인지 체크
// 폭발 문자열이면 폭발 문자열 길이만큼 스택에서 제거
// 아니면 다시 진행
