import java.util.*;
import java.io.*;
public class Tarjan {
	public static int v, e, cnt = 0;
	public static int[] discover;
	public static boolean[] scc_chk;
	public static Stack<Integer> stack;
	public static ArrayList<Integer>[] adj;
	public static ArrayList<ArrayList<Integer>> scc;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		v = Integer.parseInt(st.nextToken());
		e = Integer.parseInt(st.nextToken());
		
		stack = new Stack<>();
		scc = new ArrayList<>();
		adj = new ArrayList[v + 1];
		discover = new int[v + 1];
		scc_chk = new boolean[v + 1];
		for (int i = 0; i <= v; i++) {
			adj[i] = new ArrayList<>();
			discover[i] = -1;
		}
		
		while (e-- > 0) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken());
			
			adj[a].add(b);
		}
		
		for (int i = 1; i <= v; i++) 
			if (discover[i] == -1)
				dfs(i);
		
		for (int i = 0; i < scc.size(); i++)
			Collections.sort(scc.get(i));
		Collections.sort(scc, new Comparator<ArrayList<Integer>> () {
			public int compare(ArrayList<Integer> al1, ArrayList<Integer> al2) {
				if (al1.size() == al2.size()) {
					if (al1.get(0) < al2.get(0))
						return -1;
					else 
						return 1;
				}
				return al2.size() - al1.size();
			}
		});
		
		for (ArrayList<Integer> al : adj)
			System.out.println(al);
		System.out.println(scc);
		
		sb.append(scc.size() + "\n");
		for (ArrayList<Integer> al : scc) {
			for (int i : al)
				sb.append(i + " ");
			sb.append("-1\n");
		}
		System.out.println(sb);
	}
	public static int dfs(int n) {
		int ret = discover[n] = cnt++;
		stack.push(n);
		for (int next : adj[n]) {
			if (discover[next] == -1)
				ret = Math.min(ret, dfs(next));
			else if (!scc_chk[next])
				ret = Math.min(ret, discover[next]);
		}
		
		System.out.println(ret + " " + discover[n]);
		if (ret == discover[n]) {
			ArrayList<Integer> temp = new ArrayList<>();
			while (true) {
				int t = stack.pop();
				scc_chk[t] = true;
				temp.add(t);
				if (t == n)
					break;
			}
			Collections.sort(temp);
			scc.add(temp);
		}
		return ret;
	}
}
// Ÿ�� �˰���
// DFS ����� �����Ǵ� DFS Ʈ���� ���� ������ �̿��ϸ� ��� ���� DFS �ѹ����� SCC�� ���ϴ� ���
// ��� ���� DFS�� �����ϸ� spanning tree�� ���� �� ����
// DFS�� ȣ�� ������ ���� ������ stack�� push ��Ų��.

// ���� ���� �з��� ���Ͽ� ���� ȣ��Ǵ� ������ �� ���� ��ġ(�θ�)�� �����ٰ� ������ ��
// ���� ���� �ö� �� �ִ� ������ ã�´�. (dfs�� ���ϰ����� Ȱ�� ����)
// �̶� n->next ��������������, next�� ���� SCC�� �������� �ʾҴٸ�
// discover[next] ���� �������� �Ѵ�.

// DFS�� ������ �� ret�� discover[n]�� ���ٸ�
// �̴� ����Ŭ�� ù ���� �����̶�� ���̹Ƿ�,
// stack���� pop�ϸ鼭 n�� ���ö����� ��� ���� SCC�� �з��Ѵ�.

// ���������� �̿��� ������� �����Ǵ� SCC�� ���������� �������� �����ȴ�.

// �ð��� O(V+E)
// ������ ������ �׷����� �������� �ʾƵ� �Ǹ�
// �������� DFS 1������ �����ϴ�.