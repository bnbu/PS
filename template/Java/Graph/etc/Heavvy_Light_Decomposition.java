package D0511;
import java.util.*;
import java.io.*;
class Pair {
	int l, r;
}
public class Main {
	static int n, cnt, chainNum;
	static int[] subSize, depth, parent, head, idx, chain;
	static long[] segTree, arr;
	static Pair[] range;
	static boolean[] visit;
	static ArrayList<Integer>[] adj;
	// arr : 각 노드에 저장된 값
	// subSize : 각 노드의 서브트리 갯수
	// depth : 각 노드의 깊이
	// parent : 각 노드의 부모
	// head : 각 노드가 속한 체인의 가장 첫번째 노드
	// chain : 각 노드가 속한 체인의 번호
	// range : 각 노드의 HLD 방문 순서 기록
	// idx : 세그먼트 트리의 리프노드로 바로 이어지는 노드번호
	// segTree : 세그먼트 트리, 이때 트리의 구간은 HLD방문순서로 구간을 결정한다
	
	// segment tree 처리부분
	static long makeSegTree(int start, int end, int node) {
		if (start == end) return segTree[node];
		int mid = (start + end) / 2;
		return segTree[node] = makeSegTree(start, mid, 2*node) + makeSegTree(mid + 1, end, 2*node + 1);
	}
	static void treeUpdate(int node, int k) {
		if (node == 0) return;
		segTree[node] += k;
		treeUpdate(node / 2, k);
	}
	static long get(int start, int end, int node, int left, int right) {
		if (right < start || end < left) return 0;
		if (left <= start && end <= right) return segTree[node];
		int mid = (start + end) / 2;
		return get(start, mid, 2*node, left, right) + get(mid + 1, end, 2*node + 1, left, right);
	}
	static int findNode(int start, int end, int node, int idx) {
		if (start == end) return node;
		int mid = (start + end) / 2;
		if (idx <= mid) return findNode(start, mid, 2*node, idx);
		return findNode(mid + 1, end, 2*node + 1, idx);
	}
	
	// Heavy-Light Decomposition
	static void getSize(int curr) {
		visit[curr] = true;
		subSize[curr] = 1;
		for (int i = 0; i < adj[curr].size(); i++) {
			int next = adj[curr].get(i);
			if (visit[next]) continue;
			visit[next] = true;
			depth[next] = depth[curr] + 1;
			parent[next] = curr;
			getSize(next);
			subSize[curr] += subSize[next];
			
			if (subSize[next] > subSize[adj[curr].get(0)]) {
				int temp = adj[curr].get(0);
				adj[curr].set(0, next);
				adj[curr].set(i, temp);
			}
			// 서브트리의 크기가 가장 큰 노드를 최우선적으로 방문하도록 제일 앞에 위치시킴 (순서 조정)
		}
	}
	static void dfs(int curr) {
		// 실제 HLD가 이루어지는 부분
		visit[curr] = true;
		range[curr].l = ++cnt;
		chain[curr] = chainNum;
		boolean isFirst = true;
		for (int next : adj[curr]) {
			if (visit[next]) continue;
			
			if (isFirst) {
				head[next] = head[curr];
				dfs(next);
				isFirst = false;
				// 각 노드에서 가장 처음에 방문하는 노드들은 같은 체인으로 처리한다
			}
			else {
				head[next] = next;
				chainNum++;
				dfs(next);
			}
		}
		range[curr].r = cnt;
	}
	
	// 그래프상의 쿼리
	static long query(int u, int v) {
		long ret = 0;
		while (head[u] != head[v]) {
			if (chain[u] > chain[v]) {
				int temp = u;
				u = v;
				v = temp;
			}
			// v에 체인 번호가 더 나중인 정점이 오도록
			ret += get(1, n, 1, range[head[v]].l, range[v].l);
			v = parent[head[v]] == 0 ? head[v] : parent[head[v]];
			// 같은 체인으로 올때까지 계속 타고 올라온다
		}
		
		if (depth[u] > depth[v]) {
			int temp = u;
			u = v;
			v = temp;
		}
		ret += get(1, n, 1, range[u].l, range[v].l);	
		return ret;
	}
	static void update(int v, int k) {
		treeUpdate(idx[range[v].l], k);
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		
		segTree = new long[4*n];
		arr = new long[n + 1];
		subSize = new int[n + 1];
		depth = new int[n + 1];
		parent = new int[n + 1];
		head = new int[n + 1];
		chain = new int[n + 1];
		range = new Pair[n + 1];
		adj = new ArrayList[n + 1];
		idx = new int[n + 1];
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) {
			arr[i] = Long.parseLong(st.nextToken());
			idx[i] = findNode(1, n, 1, i);
			range[i] = new Pair();
			adj[i] = new ArrayList<>();
		}
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken());
			adj[u].add(v);
			adj[v].add(u);
		}
		
		int root = 1;
		head[root] = root;
		depth[root] = 1;
		visit = new boolean[n + 1];
		getSize(root);
		visit = new boolean[n + 1];
		chainNum = 1;
		dfs(root);
			
		for (int i = 1; i <= n; i++) segTree[idx[range[i].l]] = arr[i];
		makeSegTree(1, n, 1);
		
		int q = Integer.parseInt(br.readLine());
		while (q-- > 0) {
			st = new StringTokenizer(br.readLine());
			int op = Integer.parseInt(st.nextToken());
			if (op == 1) {
				int v = Integer.parseInt(st.nextToken()),
					k = Integer.parseInt(st.nextToken());
				update(v, k);
			}
			else {
				int u = Integer.parseInt(st.nextToken()),
					v = Integer.parseInt(st.nextToken());
				sb.append(query(u, v)).append("\n");
			}
		}
		System.out.print(sb);
	}
}
// HLD (Heavy Light Decomposition)
// 1. 	트리를 루트부터 dfs로 각 노드를 루트로 하는 서브트리의 크기를 먼저 구해준다	
// 2. 	다시 dfs로 서브트리가 큰 순서부터 방문을 하며 간선에 번호를 매긴다
//		동시에 각 노드에서의 가장 첫번째로 방문되는 노드끼리는 같은 chain에 속하는 것으로 표기하여 각 간선을 분할시킬 수 있다

//  	간선을 무거운 간선 / 가벼운 간선으로 나누었는데
//		어느 간선 u -> v가 무거운 간선이라면
//		v를 루트로 하는 서브트리의 크기가 u를 루트로 하는 서브트리의 크기의 절반 이상인 간선이 된다
//		이 특성에 따라 어느 한 정점에서의 무거운 간선은 하나씩만 존재.
//		바꿔 말하면 가벼운 간선으로 이동하여 탐색을 한다면 탐색하는 노드의 범위가 절반씩 줄어든다
//		실제 구현에서는 반드시 절반 이상이 아니라, 그냥 서브트리의 크기가 제일 큰거만 먼저 방문시킴, 나머지는 다 그냥 가벼운 간선 취급
//		이렇게 해도 큰 차이가 발생하지는 않는다.

// 활용
// Euler Tour Technique 에서는, 트리를 서브트리 구간단위로 펼쳐서 세그먼트 트리에 적용할 수 있게 도와줬다
// Heavy Light Decomposition도, 트리를 비슷하게 펼쳐서 세그먼트 트리에 적용할 수 있게 하지만
// ETT의 경우는 경로 구분 없이 서브트리 단위로만 펼쳤다면
// HLD에서는 경로를 구간으로써 표현할 수 있게 간선을 분할했기에, 경로에 있는 정점들을 구간으로써 표기하여 세그먼트 트리에 적용할 수 있다
// 여기에 경로의 구간단위로 값을 수정하거나 하면 lazy propagation까지 적용도 할 수 있을것

// 어느 두 정점의 경로의 구간을 얻어오는 방법
// a.	두 정점이 같은 체인에 속하는 경우
//		같은 체인이면, 두 정점만으로 이미 연속된 구간으로 표현이 되므로, 두 정점에 대한 HLD 번호로 구간을 얻을 수 있다
//		이때, [깊이가 얕은 정점, 깊이가 깊은 정점] 순으로 구간이 표현됨에 주의

// b.	두 정점이 서로 다른 체인에 속하는 경우
//		LCA의 개념으로 생각해보면
//		둘이 같은 체인에 속할때까지, 상위 체인으로 거슬러 올라오다 보면 만나게 되어 있다.
//		따라서, 두 정점 중 더 체인번호가 나중인 정점을 서로 같은 체인이 될때까지 상위 체인으로 계속 옮기다 보면, 둘이 결국은 같은 체인에 속하게 된다
//		옮기는 각 과정의 체인의 구간은 [해당 체인으로 올라온 위치, 해당 체인의 head]의 연속된 구간으로 표현이 된다
//		그러고 나서 같은 체인에 속하는 순간부터는 a번의 문제와 같아진다

// 위의 예시는 각 그래프의 정점에 값이 있고
// 쿼리로는 어느 두 정점 사이의 단순 경로에 속하는 정점들의 값의 합을 구하는 쿼리이다.
