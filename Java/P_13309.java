import java.util.*;
import java.io.*;
public class Main {
	static int n, cnt;
	static int[] size, hldNum, nodeDepth, nodeParent, chainDepth, chainHead, chainParent, segTree;
	static ArrayList<Integer>[] adj;
	static void makeSegTree(int start, int end, int node) {
		if (start == end) {
			segTree[node] = start;
			return;
		}
		int mid = (start + end) / 2;
		makeSegTree(start, mid, 2*node);
		makeSegTree(mid + 1, end, 2*node + 1);
		segTree[node] = Math.max(segTree[2*node], segTree[2*node + 1]);
	}
	static void update(int start, int end, int node, int idx, int v) {
		if (idx < start || end < idx) return;
		if (start == end) {
			segTree[node] = v;
			return;
		}
		
		int mid = (start + end) / 2;
		update(start, mid, 2*node, idx, v);
		update(mid + 1, end, 2*node + 1, idx, v);
		segTree[node] = Math.max(segTree[2*node], segTree[2*node + 1]);
	}
	static int get(int start, int end, int node, int left, int right) {
		if (right < start || end < left) return 0;
		if (left <= start && end <= right) return segTree[node];
		int mid = (start + end) / 2;
		return Math.max(get(start, mid, 2*node, left, right), get(mid + 1, end, 2*node + 1, left, right));
	}
	static int queryGet(int u, int v) {
		int ret = 0;
		while (chainHead[u] != chainHead[v]) {
			if (chainDepth[u] > chainDepth[v]) {
				int temp = u;
				u = v;
				v = temp;
			}
			ret = Math.max(ret, get(1, n, 1, hldNum[chainHead[v]], hldNum[v]));
			v = chainParent[v];
		}
		
		if (nodeDepth[u] > nodeDepth[v]) {
			int temp = u;
			u = v;
			v = temp;
		}
		ret = Math.max(ret, get(1, n, 1, hldNum[u] + 1, hldNum[v]));
		return ret;
	}
	
	static void getSize(int curr, int parent) {
		size[curr] = 1;
		for (int next : adj[curr]) {
			if (next == parent) continue;
			getSize(next, curr);
			size[curr] += size[next];
		}
	}
	static void dfs(int curr, int parent, int depth, int cDepth) {
		hldNum[curr] = ++cnt;
		nodeDepth[curr] = depth;
		chainDepth[curr] = cDepth;
		boolean isFirst = true;
		for (int next : adj[curr]) {
			if (next == parent) continue;
			nodeParent[next] = curr;
			if (isFirst) {
				chainHead[next] = chainHead[curr];
				chainParent[next] = chainParent[curr];
				dfs(next, curr, depth + 1, cDepth);
				isFirst = false;
			}
			else {
				chainHead[next] = next;
				chainParent[next] = curr;
				dfs(next, curr, depth + 1, cDepth + 1);
			}
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		int q = Integer.parseInt(st.nextToken());
		
		segTree = new int[4*n];
		size = new int[n + 1];
		hldNum = new int[n + 1];
		nodeDepth = new int[n + 1];
		nodeParent = new int[n + 1];
		chainDepth = new int[n + 1];
		chainHead = new int[n + 1];
		chainParent = new int[n + 1];
		adj = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();
		makeSegTree(1, n, 1);
		
		for (int i = 2; i <= n; i++) {
			int p = Integer.parseInt(br.readLine());
			adj[p].add(i);
		}
		
		getSize(1, 0);
		for (int i = 1; i <= n; i++)
			adj[i].sort((i1, i2) -> {
				return -Integer.compare(size[i1], size[i2]);
			});
		chainHead[1] = 1;
		dfs(1, 0, 1, 1);
	
		while (q-- > 0) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken()),
				c = Integer.parseInt(st.nextToken());
			
			int ans = queryGet(a, b);
			sb.append(ans == Integer.MAX_VALUE ? "NO\n" : "YES\n");
			if (c == 1) {
				if (ans != Integer.MAX_VALUE)
					update(1, n, 1, hldNum[a], Integer.MAX_VALUE);
				else 
					update(1, n, 1, hldNum[b], Integer.MAX_VALUE);
			}
		}
		System.out.print(sb);
	}
}
//세그먼트 트리 및 hld 분할 후
//각 정점에 각자의 hld번호를 저장시킨 다음 이거로 최대값 세그트리를 구성시킨다
//간선 삭제 연산이 발생하면, 그 정점의 값을 INF 값으로 바꿔버려서
//이제 어느 두 정점 u, v가 연결되어 있다고 하면, 두 정점을 잇는 경로 내의 가장 큰 정점이 나올것이고
//그게 아니라면 INF값이 나올것
//이때, 어디까지나 간선의 상태를 생각해보는 것이기 때문에 각 정점은 자신의 부모 노드와의 연결 관계를 보여주고 있어야 한다
//따라서 INF인 경우는 간선이 끊어진 것으로 생각하고 하면 되며, 이는 마치 5916번 문제와 동일한 방법을 요구하게 된다.
//
//서로 다른 체인 -> 처음부터 정점 위치까지의 구간 값을 알아와야 정상적으로 동작
//같은 체인 -> 처음을 제외하고 그 다음부터 정점 위치까지의 구간값을 알아와야 정상적으로 동작
