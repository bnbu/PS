import java.util.*;
import java.io.*;
public class B {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		ArrayList<Integer> odd = new ArrayList<>();
		
		int n = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		int ans = 0;
		for (int i = 0; i < n; i++) {
			int a = Integer.parseInt(st.nextToken());
			if (a % 2 == 0) ans += a;
			else odd.add(a);
		}
		
		Collections.sort(odd, Collections.reverseOrder());
		for (int i = 0; i < odd.size() - 1; i+=2) {
			ans += odd.get(i) + odd.get(i + 1);
		}
		System.out.println(ans);
	}
}
// 짝수는 관계없이 전부 더해도 되지만
// 홀수는 반드시 2번씩만 더해져야 하므로
// 내림차순 정렬 후 2개씩 더하면 된다.
