import java.util.*;
import java.io.*;
public class Main {
	static int n, cnt;
	static int[] size, hldNum, toOrig, nodeDepth, chainDepth, chainHead, chainParent, segTree;
	static ArrayList<Integer>[] adj;
	static void update(int start, int end, int node, int idx) {
		if (idx < start || end < idx) return;
		if (start == end) {
			segTree[node] = segTree[node] == 0 ? idx : 0;
			return;
		}
		
		int mid = (start + end) / 2;
		update(start, mid, 2*node, idx);
		update(mid + 1, end, 2*node + 1, idx);
		int left = segTree[2*node],
			right = segTree[2*node + 1];
		
		segTree[node] = nodeDepth[toOrig[left]] < nodeDepth[toOrig[right]] ? left : right;
	}
	static int get(int start, int end, int node, int left, int right) {
		if (right < start || end < left) return 0;
		if (left <= start && end <= right) return segTree[node];
		int mid = (start + end) / 2;
		int leftValue = get(start, mid, 2*node, left, right),
			rightValue = get(mid + 1, end, 2*node + 1, left, right);
		
		if (nodeDepth[toOrig[leftValue]] < nodeDepth[toOrig[rightValue]]) return leftValue;
		else return rightValue;
	}
	static int queryGet(int u, int v) {
		int ret = 0;
		
		while (chainHead[u] != chainHead[v]) {
			if (chainDepth[u] > chainDepth[v]) {
				int temp = u;
				u = v;
				v = temp;
			}
			int curr = get(1, n, 1, hldNum[chainHead[v]], hldNum[v]);
			if (nodeDepth[toOrig[ret]] > nodeDepth[toOrig[curr]]) ret = curr;
			v = chainParent[v];
		}
		int last = get(1, n, 1, hldNum[u], hldNum[v]);
		if (nodeDepth[toOrig[ret]] > nodeDepth[toOrig[last]]) ret = last;
		return ret == 0 ? -1 : toOrig[ret];
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
		toOrig[cnt] = curr;
		nodeDepth[curr] = depth;
		chainDepth[curr] = cDepth;
		boolean isFirst = true;
		for (int next : adj[curr]) {
			if (next == parent) continue;
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
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		
		segTree = new int[4*n];
		size = new int[n + 1];
		hldNum = new int[n + 1];
		toOrig = new int[n + 1];
		nodeDepth = new int[n + 1];
		chainDepth = new int[n + 1];
		chainHead = new int[n + 1];
		chainParent = new int[n + 1];
		adj = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();
		
		for (int i = 0; i < n - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken());
			adj[u].add(v);
			adj[v].add(u);
		}
		
		getSize(1, 0);
		for (int i = 1; i <= n; i++)
			adj[i].sort((i1, i2) -> {
				return -Integer.compare(size[i1], size[i2]);
			});
		chainHead[1] = 1;
		dfs(1, 0, 1, 1);
		nodeDepth[0] = Integer.MAX_VALUE;
	
		int m = Integer.parseInt(br.readLine());
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken());
			
			if (a == 1) update(1, n, 1, hldNum[b]);
			else 
				sb.append(queryGet(1, b)).append("\n");
		}
		System.out.print(sb);
	}
}
//세그먼트 트리를 구축하고
//HLD로 트리를 분할하는데, 주의할 점은 (노드번호->hld번호)도 필요하지만
//후에 서술할 부분 때문에 (hld번호->노드번호)도 필요하니 같이 구해야 한다
//세그먼트 트리에 이제 값을 저장을 하는데
//트리의 각 구간에 저장된 값은 다음을 의미한다 : 가장 노드의 깊이가 얕은 검정색 노드의 hld번호
//이렇게 저장을 하면, 1번부터 어느 정점 v까지 경로 중 가장 먼저 마주치는 노드의 번호를 알아낼 수 있다
