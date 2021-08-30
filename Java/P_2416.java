import java.util.*;
import java.io.*;
public class Main {
	public static int cnt, sccIdx;
	public static int[] sccNum, discover, ans;
	public static Stack<Integer> stack;
	public static ArrayList<Integer>[] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		ans = new int[m + 1];
		discover = new int[2*m + 1];
		sccNum = new int[2*m + 1];
		adj = new ArrayList[2*m + 1];
		for (int i = 1; i <= 2*m; i++) {
			adj[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				switch_a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken()),
				switch_b = Integer.parseInt(st.nextToken());
			
			int idx_a = switch_a == 1 ? a + m : a;
			int idx_b = switch_b == 1 ? b + m : b;
			
			adj[not(idx_a, m)].add(idx_b);
			adj[not(idx_b, m)].add(idx_a);
		}
		tarjan(m);
		
		boolean chk = true;
		for (int i = 1; i <= m; i++)
			if (sccNum[i] == sccNum[i + m]) {
				chk = false;
				break;
			}
		
		if (chk) {
			for (int i = 1; i <= m; i++)
				sb.append(ans[i] + "\n");
		}
		else {
			sb.append("IMPOSSIBLE\n");
		}
		System.out.print(sb);
	}
	public static int not(int i, int n) {
		return i <= n ? i + n : i - n;
	}
	public static void tarjan(int n) {
		cnt = 0;
		sccIdx = 0;
		stack = new Stack<>();
		Arrays.fill(discover, -1);
		Arrays.fill(sccNum, -1);
		Arrays.fill(ans, -1);
		for (int i = 1; i <= n*2; i++)
			if (discover[i] == -1)
				dfs(i, n);
	}
	public static int dfs(int v, int m) {
		int ret = discover[v] = cnt++;
		stack.push(v);
		for (int next : adj[v]) {
			if (discover[next] == -1)
				ret = Math.min(ret, dfs(next, m));
			else if (sccNum[next] == -1)
				ret = Math.min(ret, discover[next]);
		}
		
		if (ret == discover[v]) {
			while (true) {
				int i = stack.pop();
				sccNum[i] = sccIdx;
				int num = i <= m ? i : i - m;
				if (ans[num] == -1) {
					if (i <= m)
						ans[num] = 0;
					else
						ans[num] = 1;
				}
				if (i == v)
					break;
			}
			sccIdx++;
		}
		return ret;
	}
}
// 2021-01-23 01:46
// m이 최대 50만개까지 올 수 있는데 비해 메모리는 128mb (c++ 기준)으로 극한의 문제
// 따라서 코사라주 대신, 타잔 알고리즘을 사용했으며
// 타잔 알고리즘은 scc의 생성 순서의 역순이 곧 위상정렬된 순서이므로
// 이를 이용하여 scc를 따로 담아두지 않고
// scc의 번호만 추출하는 과정중에 동시에 ans배열을 완성시킨다
// 이후 scc번호로 2-SAT충족여부를 검사하고
// 충족이 가능하다면 ans배열을 출력
// 충족불가능이라면 IMPOSSIBLE을 출력시킨다.
// 이러한 방법으로 java도 통과시켰다.

// 여기서 포인트는 scc를 리스트로 따로 추출하지 않고
// 각 정점이 몇번 scc에 포함되어있는지만 따로 알아가는 과정에서 동시에 ans 배열을 만든것
// 이 부분에서 메모리를 아낄 수 있었다고 판단한다

// 그 외에 처음으로 통과한 C++ 코드와
// 기존 메모리 초과났던 코드를 해당 방법으로 수정한 C++코드도 같이 텍스트파일로 저장해뒀다.
