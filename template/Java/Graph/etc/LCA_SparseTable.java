import java.util.*;
import java.io.*;
public class LCA_SparseTable {
	public static int n, m;
	public static int[] depth;
	public static int[][] parent;
	public static boolean[] visit;
	public static ArrayList<Integer>[] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		parent = new int[n + 1][21];
		adj = new ArrayList[n + 1];
		depth = new int[n + 1];
		visit = new boolean[n + 1];
		for (int i = 1; i <= n; i++)
			adj[i] = new ArrayList<>();
		
		for (int i = 0; i < n - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken());
			
			adj[u].add(v);
			adj[v].add(u);
		}
		dfs(1, 0);
		
		for (int i = 1; i <= n; i++)
			System.out.print(parent[i][0] + " ");
		System.out.println();
		
		calcParent();
		
		m = Integer.parseInt(br.readLine());
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken());
			
			sb.append(lca(a, b) + "\n");
		}
		System.out.println(sb);
	}
	public static void dfs(int v, int d) {
		visit[v] = true;
		depth[v] = d;
		for (int i = 0; i < adj[v].size(); i++) {
			int next = adj[v].get(i);
			if (visit[next])
				continue;
			
			parent[next][0] = v;
			dfs(next, d + 1);
		}
	}
	public static void calcParent() {
		for (int j = 1; j < 21; j++)
			for (int i = 1; i <= n; i++)
				parent[i][j] = parent[parent[i][j - 1]][j - 1];
	}
	public static int lca (int x, int y) {
		int a, b;
		if (depth[x] > depth[y]) {
			a = y;
			b = x;
		}
		else {
			a = x;
			b = y;
		}
		
		for (int i = 20; i >= 0; i--) {
			if (depth[b] - depth[a] >= (1 << i)) 
				b = parent[b][i];
		} // 1 << i : 1�� i��ŭ left-shift ����, ��, 2^i �̴�.
		// ���� ���� 2^i���� �۾����� ������ ���̰� ���� ������ parent[b][i]�� ����
		// ���Ӱ� ���ŵ�, �� 2^i��ŭ �ö�� ��ġ���� �ٽ� ���̸� �����Ѵ�.
		// �̷��� 2^i�� �Ž��� �ö� �� �ִ�. (1,2,4,8.. ĭ�� �ö���Ƿ�)
		// �������� �������� �� ������.
		
		if (a == b) return a;
		// => ���� ���̱��� �ö�� b�� a�� ���� ����, �� LCA�� a�϶�.
		
		for (int i = 20; i >= 0; i--) {
			if (parent[a][i] != parent[b][i]) {
				a = parent[a][i];
				b = parent[b][i];
			}
		}
		// ���� ���� �ʴٸ�, ���� �θ� ���ö����� ã���� ����.
		// a�� b�� 2^i��° ������ ���� ���� �ʴٸ�,
		// �� ��ġ���� �ٽ� 2^i��° ������ �Ž��� ã���� ����.
		// �̶� 2^20��°���� ã����, Ʈ���� �۾Ƶ�
		// 2^20��°�� ������ 0 (��Ʈ�� ��)�� �Ǵ��ϹǷ� �������.
		// �̷��� ���������� ã�� �� ������ a�� b�� ������ ���� ��ġ���� ���� �Ǹ�,
		// ã�� ���� ���������� �ٷ� �ڽ��� ����̰� �ȴ�.
		
		return parent[a][0];
	}
}
// LCA (Lowest Common Ancestor) �ּ� ���� ����

// �� �������� (�ڽ��� ������) �������� �ö� �� ó������ ����ǰ� ������ ����
// => LCA�� ���ϴ� �� ������ ����(����)�� �����ϰ� ���� ��
// ���������� Ž���� �����Ͽ� LCA�� ���� �� �ִ�.
// �Ϲ��� �������δ�  ���������� �Ǳ� ������ O(N)

// �ణ�� ��ó���� ���Ͽ� O(logN)���� LCA�� ���� �� �ִ�
// �ϴ� DFS�� �����Ͽ� �� ������ ���̿� �θ� �����Ѵ�.
// �̶� par[x][i] (������ȣ�� x�� ������ 2^i ��° ����) �� ����
// par[x][0]�� �θ� ä���ش�.
// ���� par[x][i] = par[par[x][i-1]][i-1] ���� 2^i��° ������ ä������ �� �ִ�.

// ��ó��(calcParent)���� j�� 20������ ����ϴ� ������,
// N�� 10���̶�, 2^20�� 100�� �̻�����, ���ǿ� �����

// ���⼭ 2^i��° ������ ���س��� ������, 2^i�� ���̸� �÷����� ������ Ž���� �� �ִ�
// 2^i�� Ž���ϹǷ� O(logN) �ð��븦 ���´�.
