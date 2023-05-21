import java.util.*;
import java.io.*;
public class C {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine());
		int ans = 4;
		
		st = new StringTokenizer(br.readLine());
		int x = Integer.parseInt(st.nextToken()),
			y = Integer.parseInt(st.nextToken());
		
		if (x == 1) ans--;
		if (x == n) ans--;
		if (y == 1) ans--;
		if (y == n) ans--;
		System.out.println(ans);
	}
}
// 어쩄든 최대 4번 복사를 시도하는데
// 어느 한 모서리에 붙어있다면, 붙어있는 모서리의 개수만큼 복사를 덜 시도해도 된다