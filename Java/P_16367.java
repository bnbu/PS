import java.util.*;
import java.io.*;
public class Main {
	public static int n, m, cnt, idx;
	public static int[] sccNum, discover;
	public static char[] ans;
	public static Stack<Integer> stack;
	public static ArrayList<Integer>[] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		sccNum = new int[2*n + 1];
		ans = new char[n + 1];
		discover = new int[2*n + 1];
		adj = new ArrayList[2*n + 1];
		
		Arrays.fill(sccNum, -1);
		Arrays.fill(discover, -1);
		Arrays.fill(ans, '0');
		
		for (int i = 1; i <= 2*n; i++)
			adj[i] = new ArrayList<>();
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int n1 = Integer.parseInt(st.nextToken()) * (st.nextToken().equals("R") ? 1 : -1),
				n2 = Integer.parseInt(st.nextToken()) * (st.nextToken().equals("R") ? 1 : -1),
				n3 = Integer.parseInt(st.nextToken()) * (st.nextToken().equals("R") ? 1 : -1);
			
			int num1 = n1 < 0 ? Math.abs(n1) + n : n1,
				num2 = n2 < 0 ? Math.abs(n2) + n : n2,
				num3 = n3 < 0 ? Math.abs(n3) + n : n3;
			
			adj[not(num1)].add(num2);
			adj[not(num2)].add(num1);
			adj[not(num1)].add(num3);
			adj[not(num3)].add(num1);
			adj[not(num2)].add(num3);
			adj[not(num3)].add(num2);
		}
		tarjan(n);
		
		boolean chk = true;
		for (int i = 1; i <= n; i++)
			if (sccNum[i] == sccNum[i + n]) {
				chk = false;
				break;
			}
		
		if (chk) {
			for (int i = 1; i <= n; i++)
				sb.append(ans[i]);
		}
		else {
			sb.append("-1");
		}
		System.out.println(sb);
	}
	public static int not(int i) {
		return i <= n ? i + n : i - n;
	}
	public static void tarjan(int n) {
		cnt = 0;
		idx = 0;
		stack = new Stack<>();
	
		for (int i = 1; i <= 2*n; i++)
			if (discover[i] == -1)
				dfs(i);
	}
	public static int dfs(int v) {
		int ret = discover[v] = cnt++;
		stack.push(v);
		for (int next : adj[v]) {
			if (discover[next] == -1)
				ret = Math.min(ret, dfs(next));
			else if (sccNum[next] == -1)
				ret = Math.min(ret, discover[next]);
		}
		
		if (ret == discover[v]) {	
			while (true) {
				int curr = stack.pop();
				sccNum[curr] = idx;
				
				int num = curr <= n ? curr : curr - n;
				if (ans[num] == '0') {
					if (curr <= n) 
						ans[num] = 'R';
					else
						ans[num] = 'B';
				}
				
				if (v == curr)
					break;
			}
			
			idx++;
		}
		
		return ret;
	}
}
// 2021-01-24 21:08
// 3개중 2개를 맞춰야 상품을 얻는다, 
// R -> 1 / B -> -1 로 생각한 다음
// 각 번호에 대해 적어도 2개가 맞아서 상품을 받는 상태를 생각해보면
// 번호를 n1, n2, n3라고 한다면
// (n1 v n2) ^ (n1 v n3) ^ (n2 v n3) 으로 나타낼 수 있고
// 이는 n1, n2, n3중 2개만 만족해도 값이 true가 된다

// 이를 입력받은 전체를 고려하여 모두 그래프 요소로 변환시킨 후 그래프 인접리스트 생성
// 각자의 값을 알아보려면 위상정렬된 SCC가 필요하므로 타잔 알고리즘을 사용
// SCC를 추출한 이후 위상정렬된 순서대로 따라가면서
// 2-SAT값을 지정하듯 먼저 만나는 변수에 대해 일반 변수 또는 not변수 여부에 따라
// R또는 B를 써준다


// 여기서 드는 의문점이
// 2-SAT에서는 먼저 만나는 값을 false로 지정했어야 하므로
// 만약 일반 변수를 먼저 만났다면, 그 값은 false
// not변수를 먼저 만났다면, not변수가 false이기 때문에 그 값은 true가 되야 했다
// 이를 적용해서 R을 1, B를 -1로 두었으므로
// 먼저 만나는 값을 B로 둬봤는데 맞게 나오지 않아서
// 둘을 바꿨더니 정답이었다.
// 이 부분은 다시 생각해봐야겠다.
