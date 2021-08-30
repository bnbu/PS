import java.util.*;
import java.io.*;
public class Main {
	public static int[] parent;
	public static boolean[] treeChk;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int cnt = 0;
		while (true) {
			cnt++;
			st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken()),
				m = Integer.parseInt(st.nextToken());
			
			if (n == 0 && m == 0)
				break;
			
			parent = new int[n + 1];
			treeChk = new boolean[n + 1];
			for (int i = 1; i <= n; i++) {
				parent[i] = i;
				treeChk[i] = true;
			}
				
			while (m-- > 0) {
				st = new StringTokenizer(br.readLine());
				int u = Integer.parseInt(st.nextToken()),
					v = Integer.parseInt(st.nextToken());
				
				union(Math.min(u, v), Math.max(u, v));
			}
			
			int treeCnt = 0;
			for (int i = 1; i <= n; i++) {
				if (treeChk[find(i)]) {
					treeChk[find(i)] = false;
					treeCnt++;
				}
			}
			
			sb.append("Case " + cnt + ": ");
			if (treeCnt > 1)
				sb.append("A forest of " + treeCnt + " trees.\n");
			else if (treeCnt == 1)
				sb.append("There is one tree.\n");
			else
				sb.append("No trees.\n");
		}	
		System.out.println(sb);
	}
	public static void union(int a, int b) {
		int x = find(a);
		int y = find(b);
		if (x == y || !treeChk[x] || !treeChk[y]) {
			treeChk[x] = false;
			treeChk[y] = false;
		}
		parent[y] = x;
	}
	public static int find(int a) {
		if (a == parent[a])
			return a;
		else
			return parent[a] = find(parent[a]);
	}
}
// 2021-01-10 03:28
// 흠...
// 일단은 질문에 있는 코드를 분석하여 작성함
// 문제 자체는 유니온-파인드 -> 사이클 갯수 파악 -> 전체 연결개체 개수 - 사이클개수 임.
