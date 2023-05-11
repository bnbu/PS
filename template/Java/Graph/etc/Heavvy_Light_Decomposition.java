import java.util.*;
import java.io.*;
class Pair {
	int l, r;
}
public class Main {
	static int n, cnt;
	static int[] size, depth, head, chainParent;
	static long[] segTree, arr;
	static Pair[] range;
	static ArrayList<Integer>[] adj;
	// arr : 각 노드에 저장된 값
	// size : 각 노드의 서브트리 갯수
	// head : 각 노드가 속한 체인의 가장 첫번째 노드
	// depth : 각 노드가 속한 깊이
	// chainParent : 해당 체인의 head의 부모 노드
	// range : 각 노드의 HLD 방문 순서 기록
	// segTree : 세그먼트 트리, 이때 트리의 구간은 HLD방문순서로 구간을 결정한다
	
	// segment tree 처리부분
	static long makeSegTree(int start, int end, int node) {
		if (start == end) return segTree[node];
		int mid = (start + end) / 2;
		return segTree[node] = makeSegTree(start, mid, 2*node) + makeSegTree(mid + 1, end, 2*node + 1);
	}
	static void update(int start, int end, int node, int idx, long k) {
		if (idx < start || end < idx) return;
		if (start == end) {
			segTree[node] = k;
			return;
		}
		int mid = (start + end) / 2;
		update(start, mid, 2*node, idx, k);
		update(mid + 1, end, 2*node + 1, idx, k);
		segTree[node] = segTree[2*node] + segTree[2*node + 1];
	}
	static long get(int start, int end, int node, int left, int right) {
		if (right < start || end < left) return 0;
		if (left <= start && end <= right) return segTree[node];
		int mid = (start + end) / 2;
		return get(start, mid, 2*node, left, right) + get(mid + 1, end, 2*node + 1, left, right);
	}
	
	// Heavy-Light Decomposition
	static void getSize(int curr, int parent) {
		size[curr] = 1;
		for (int next : adj[curr]) {
			if (next == parent) continue;
			getSize(next, curr);
			size[curr] += size[next];
		}
	}
	static void dfs(int curr, int parent, int num) {
		range[curr].l = ++cnt;
		depth[curr] = num;
		
		boolean isFirst = true;
		for (int next : adj[curr]) {
			if (next == parent) continue;
			if (isFirst) {
				head[next] = head[curr];
				chainParent[next] = chainParent[curr];
				dfs(next, curr, num);
				isFirst = false;
			}
			else {
				head[next] = next;
				chainParent[next] = curr;
				dfs(next, curr, num + 1);
			}
		}
	}
	
	// 그래프상의 쿼리
	static long queryGet(int u, int v) {
		long ret = 0;
		
		while (head[u] != head[v]) {
			if (depth[u] > depth[v]) {
				int temp = u;
				u = v;
				v = temp;
			}
			// 깊이가 더 깊은 정점을 우선적으로 이동시킨다
			ret += get(1, n, 1, range[head[v]].l, range[v].l);
			v = chainParent[v];
		}
		// 두 정점을 같은 체인이 될때까지 상위 체인으로 이동
		
		ret += get(1, n, 1, Math.min(range[u].l, range[v].l), Math.max(range[u].l, range[v].l));
		// 같은 체인 내에서는  쉽게 구간을 통해 계산
		
		return ret;
	}
	static void queryUpdate(int v, long k) {
		update(1, n, 1, range[v].l, k);
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		
		segTree = new long[4*n];
		arr = new long[n + 1];
		size = new int[n + 1];
		head = new int[n + 1];
		depth = new int[n + 1];
		chainParent = new int[n + 1];
		range = new Pair[n + 1];
		adj = new ArrayList[n + 1];
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) {
			arr[i] = Long.parseLong(st.nextToken());
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
		getSize(root, 0);
		for (int i = 1; i <= n; i++)
			Collections.sort(adj[i], (i1, i2) -> {
				return -Integer.compare(size[i1], size[i2]);
			});
		dfs(root, 0, 1);
		
		System.out.println(Arrays.toString(depth));
		System.out.println(Arrays.toString(head));
			
		for (int i = 1; i <= n; i++) 
			update(1, n, 1, range[i].l, arr[i]);
		
		int q = Integer.parseInt(br.readLine());
		while (q-- > 0) {
			st = new StringTokenizer(br.readLine());
			int op = Integer.parseInt(st.nextToken());
			if (op == 1) {
				int v = Integer.parseInt(st.nextToken()),
					k = Integer.parseInt(st.nextToken());
				queryUpdate(v, k);
			}
			else {
				int u = Integer.parseInt(st.nextToken()),
					v = Integer.parseInt(st.nextToken());
				sb.append(queryGet(u, v)).append("\n");
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
//		옮기는 각 과정의 체인의 구간은  [해당 체인의 head, 해당 체인으로 올라온 위치]의 연속된 구간으로 표현이 된다
//		그러고 나서 같은 체인에 속하는 순간부터는 a번의 문제와 같아진다

// 위의 예시는 각 그래프의 정점에 값이 있고
// 쿼리로는 어느 두 정점 사이의 단순 경로에 속하는 정점들의 값의 합을 구하는 쿼리이다.
