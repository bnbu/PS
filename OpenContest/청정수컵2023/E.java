import java.util.*;
import java.io.*;
public class E {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine());
		
		int s = 1, e = n;
		boolean b = true;
		for (int i = 0; i < n; i++) {
			if (b) sb.append(e--).append(" ");
			else sb.append(s++).append(" ");
			b = !b;
		}
		System.out.println(sb);
	}
}

// 길이가 N이라고 할 때
// 인접한 두 값의 차의 절댓값이 N-1 부터 공차가 -1인 등차수열을 이룬다

// 이는 1 부터 N까지 나열시킨 후
// 왼쪽 끝과 오른쪽 끝을 순서대로 하나씩 나열하면 왼성시킬 수 있다