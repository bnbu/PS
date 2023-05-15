import java.util.*;
import java.io.*;
class Pair {
	int l, r;
}
public class Main {
	static final long unsigned = 0xFFFFFFFFL;
	static int n, cnt;
	static int[] size, nodeDepth, chainDepth, chainHead, chainParent;
	static long[] segTree, plusLazy, mulLazy;
	static Pair[] hldRange;
	static ArrayList<Integer>[] adj;
	static void propagate(int start, int end, int node) {
		if (plusLazy[node] == 0 && mulLazy[node] == 1) return;
		segTree[node] *= mulLazy[node];
		segTree[node] &= unsigned;
		segTree[node] += plusLazy[node] * (end - start + 1);
		segTree[node] &= unsigned;
		if (start != end) {
			mulLazy[2*node] *= mulLazy[node];
			mulLazy[2*node] &= unsigned;
			mulLazy[2*node + 1] *= mulLazy[node];
			mulLazy[2*node + 1] &= unsigned;
			plusLazy[2*node] *= mulLazy[node];
			mulLazy[2*node] &= unsigned;
			plusLazy[2*node + 1] *= mulLazy[node];
			mulLazy[2*node + 1] &= unsigned;
			
			plusLazy[2*node] += plusLazy[node];
			mulLazy[2*node + 1] &= unsigned;
			plusLazy[2*node + 1] += plusLazy[node];
			mulLazy[2*node + 1] &= unsigned;
		}
		mulLazy[node] = 1;
		plusLazy[node] = 0;
	}
	static void update(int start, int end, int node, int left, int right, long plus, long mul) {
		propagate(start, end, node);
		if (right < start || end < left) return;
		if (left <= start && end <= right) {
			plusLazy[node] += plus;
			mulLazy[node] *= mul;
			propagate(start, end, node);
			return;
		}
		int mid = (start + end) / 2;
		update(start, mid, 2*node, left, right, plus, mul);
		update(mid + 1, end, 2*node + 1, left, right, plus, mul);
		segTree[node] = segTree[2*node] + segTree[2*node + 1];
		segTree[node] &= unsigned;
	}
	static long get(int start, int end, int node, int left, int right) {
		propagate(start, end, node);
		if (right < start || end < left) return 0;
		if (left <= start && end <= right) return segTree[node];
		int mid = (start + end) / 2;
		
		return (get(start, mid, 2*node, left, right) + get(mid + 1, end, 2*node + 1, left, right)) & unsigned;
	}
	
	// HLD
	static void getSize(int curr, int parent) {
		size[curr] = 1;
		for (int next : adj[curr]) {
			if (next == parent) continue;
			getSize(next, curr);
			size[curr] += size[next];
		}
	}
	static void dfs(int curr, int parent, int depth, int cDepth) {
		hldRange[curr].l = ++cnt;
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
		hldRange[curr].r = cnt;
	}
	
	// query
	static void updateSubtree(int x, int plus, int mul) {
		update(1, n, 1, hldRange[x].l, hldRange[x].r, plus, mul);
	}
	static long getSubtree(int x) {
		return get(1, n, 1, hldRange[x].l, hldRange[x].r);
	}
	static void updatePath(int u, int v, int plus, int mul) {
		while (chainHead[u] != chainHead[v]) {
			if (chainDepth[u] > chainDepth[v]) {
				int temp = u;
				u = v;
				v = temp;
			}
			update(1, n, 1, hldRange[chainHead[v]].l, hldRange[v].l, plus, mul);
			v = chainParent[v];
		}
		if (nodeDepth[u] > nodeDepth[v]) {
			int temp = u;
			u = v;
			v = temp;
		}
		update(1, n, 1, hldRange[u].l, hldRange[v].l, plus, mul);
	}
	static long getPath(int u, int v) {
		long ret = 0;
		while (chainHead[u] != chainHead[v]) {
			if (chainDepth[u] > chainDepth[v]) {
				int temp = u;
				u = v;
				v = temp;
			}
			ret += get(1, n, 1, hldRange[chainHead[v]].l, hldRange[v].l);
			ret &= unsigned;
			v = chainParent[v];
		}
		if (nodeDepth[u] > nodeDepth[v]) {
			int temp = u;
			u = v;
			v = temp;
		}
		ret += get(1, n, 1, hldRange[u].l, hldRange[v].l);
		ret &= unsigned;
		return ret;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		int q = Integer.parseInt(st.nextToken());
		
		segTree = new long[4*n];
		plusLazy = new long[4*n];
		mulLazy = new long[4*n]; Arrays.fill(mulLazy, 1);
		size = new int[n + 1];
		nodeDepth = new int[n + 1];
		chainDepth = new int[n + 1];
		chainHead = new int[n + 1];
		chainParent = new int[n + 1];
		hldRange = new Pair[n + 1];
		adj = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++) {
			hldRange[i] = new Pair();
			adj[i] = new ArrayList<>();
		}
		
		for (int i = 1; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken());
			adj[u].add(v);
			adj[v].add(u);
		}
		getSize(1, 0);
		for (int i = 1; i <= n; i++)
			Collections.sort(adj[i], (i1, i2) -> {
				return -Integer.compare(size[i1], size[i2]);
			});
		cnt = 0;
		chainHead[1] = 1;
		dfs(1, 0, 1, 1);
		
 		while (q-- > 0) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			if (a % 2 == 1) {
				int x = Integer.parseInt(st.nextToken());
				if (a == 5) {
					sb.append(getSubtree(x)).append("\n");
					continue;
				}
				
				int v = Integer.parseInt(st.nextToken());
				if (a == 1) {
					updateSubtree(x, v, 1);
				}
				else {
					updateSubtree(x, 0, v);
				}
			}
			else {
				int x = Integer.parseInt(st.nextToken()),
					y = Integer.parseInt(st.nextToken());
				if (a == 6) {
					sb.append(getPath(x, y)).append("\n");
					continue;
				}
				int v = Integer.parseInt(st.nextToken());
				if (a == 2) {
					updatePath(x, y, v, 1);
				}
				else {
					updatePath(x, y, 0, v);
				}
			}
		}
 		System.out.print(sb);
	}
}

//일단 논리는 맞으니까 푼거에 대해서 작성은 할건데
//Java는 unsigned 없어서 불가능한거면 이거 대체 어떻게 풀어야하지

//문제 자체는 트리에 대해 6개의 쿼리를 실행한다
//쿼리는 크게 2종류로 나눠볼 수 있다

//1. 어떤 정점 x가 루트인 서브트리에 대한 쿼리
//2. 어떤 두 정점 u, v를 잇는 단순 경로에 대한 쿼리

//1의 경우는 Euler Tour Technique을 통해
//2의 경우는 Heavy-Light Decomposition을 통해 
//트리의 쿼리에 대해 Segment Tree를 사용할 수 있다.
//1이건 2이건 구간에 대한 값의 갱신이 발생하므로 Lazy Propagation을 사용해야한다.

//우선 HLD과정에서 HLD 방문 순서를 결정할 때, ETT를 하던떄처럼
//이어진 노드의 방문이 끝나고 난 후 나올때의 HLD 방문 순서를 기록하는 것으로
//HLD를 하면서 동시에 ETT를 해낼 수 있다.

//문제는 갱신하는 쿼리가 2종류다
//a. 어느 서브트리 혹은 경로에 v를 더한다
//b. 어느 서브트리 혹은 경로에 v를 곱한다

//구간에 값을 더하는 갱신은 많이 해서 익숙하지만
//구간에 값을 곱하는 갱신은 이것만 있으면 어려움이 없는데
//더하는 갱신과 같이 발생해서 많이 고민됐다
//더하는 갱신과 곱하는 갱신의 순서에 따라 값이 전혀 다르게 바뀌기 때문

//이때 잘 생각해보면 덧샘에 대한 Lazy는 곱샘에 대해 어떠한 영향을 미치진 못한다
//하지만 그 반대로 곱샘에 대한 Lazy는 덧샘에 대해 영향을 미칠 수 밖에 없다.

//예시로, 덧샘에 대한 Lazy가 3이 있는 구간에
//곱샘을 2를 한다고 치면
//(기존값 + lazy값) * 2 가 되어야 이치가 맞고

//곱샘에 대한 Lazy가 2가 있는 구간에
//덧샘을 3을 한다고 치면
//기존값 * 3 + Lazy값 이 되므로

//이 방식에 따라 propagate를 
//덧샘에 대한 Lazy는 기존에 하던데로 덧셈에 똑같이 전파시키지만
//곱샘에 대한 Lazy는 곱샘 뿐만 아니라, 덧샘에까지 곱샘을 통해 전파를 시켜야만 한다

//이렇게 하면 문제없이 구간에 대한 덧샘, 곱샘 갱신을 할 수 있다.

// unsigned를 수동으로 적용해서 결국 자바로 풀긴 했다
// 스레기;언어
