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
// 3?????? 2?????? ????????? ????????? ?????????, 
// R -> 1 / B -> -1 ??? ????????? ??????
// ??? ????????? ?????? ????????? 2?????? ????????? ????????? ?????? ????????? ???????????????
// ????????? n1, n2, n3?????? ?????????
// (n1 v n2) ^ (n1 v n3) ^ (n2 v n3) ?????? ????????? ??? ??????
// ?????? n1, n2, n3??? 2?????? ???????????? ?????? true??? ??????

// ?????? ???????????? ????????? ???????????? ?????? ????????? ????????? ???????????? ??? ????????? ??????????????? ??????
// ????????? ?????? ??????????????? ??????????????? SCC??? ??????????????? ?????? ??????????????? ??????
// SCC??? ????????? ?????? ??????????????? ???????????? ???????????????
// 2-SAT?????? ???????????? ?????? ????????? ????????? ?????? ?????? ?????? ?????? not?????? ????????? ??????
// R?????? B??? ?????????


// ????????? ?????? ????????????
// 2-SAT????????? ?????? ????????? ?????? false??? ??????????????? ?????????
// ?????? ?????? ????????? ?????? ????????????, ??? ?????? false
// not????????? ?????? ????????????, not????????? false?????? ????????? ??? ?????? true??? ?????? ??????
// ?????? ???????????? R??? 1, B??? -1??? ???????????????
// ?????? ????????? ?????? B??? ???????????? ?????? ????????? ?????????
// ?????? ???????????? ???????????????.
// ??? ????????? ?????? ?????????????????????.
