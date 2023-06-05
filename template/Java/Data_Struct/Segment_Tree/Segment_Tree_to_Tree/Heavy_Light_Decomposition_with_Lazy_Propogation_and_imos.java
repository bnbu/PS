import java.util.*;
import java.io.*;
class Pair{
	int l, r;
	public Pair(int l, int r) {
		this.l = l;
		this.r = r;
	}
}
public class Main {
	static int n, cnt;
	static int[] hldNum, size, nodeDepth, chainDepth, chainHead, chainParent;
	static int[][] nodeParent;
	static long[] segTree, lazy;
	static ArrayList<Integer>[] adj;
	// Segment Tree with lazy propagation
	static void propagate(int start, int end, int node) {
		if (lazy[node] != 0) {
			if (start != end) {
				lazy[2*node] += lazy[node];
				lazy[2*node + 1] += lazy[node];
			}
			segTree[node] += lazy[node] * (end - start + 1);
			lazy[node] = 0;
		}
	}
	static void update(int start, int end, int node, int left, int right, int k) {
		propagate(start, end, node);
		if (right < start || end < left) return;
		if (left <= start && end <= right) {
			lazy[node] += k;
			propagate(start, end, node);
			return;
		}
		int mid = (start + end) / 2;
		update(start, mid, 2*node, left, right, k);
		update(mid + 1, end, 2*node + 1, left, right, k);
		segTree[node] = segTree[2*node] + segTree[2*node + 1];
	}
	static long get(int start, int end, int node, int left, int right) {
		propagate(start, end, node);
		if (right < start || end < left) return 0;
		if (left <= start && end <= right) return segTree[node];
		int mid = (start + end) / 2;
		return get(start, mid, 2*node, left, right) + get(mid + 1, end, 2*node + 1, left, right);
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
		hldNum[curr] = ++cnt;
		nodeDepth[curr] = depth;
		chainDepth[curr] = cDepth;
		boolean isFirst = true;
		for (int next : adj[curr]) {
			if (next == parent) continue;
			nodeParent[next][0] = curr;
			if (isFirst) {
				chainHead[next] = chainHead[curr];
				chainParent[next] = chainParent[curr];
				dfs(next, curr, depth+1, cDepth);
				isFirst = false;
			}
			else {
				chainHead[next] = next;
				chainParent[next] = curr;
				dfs(next, curr, depth+1, cDepth + 1);
			}
		}
	}
	
	// LCA
	static void calcParent() {
		for (int j = 1; j < 21; j++)
			for (int i = 1; i <= n; i++)
				nodeParent[i][j] = nodeParent[nodeParent[i][j - 1]][j - 1];
	}
	static int getLca(int x, int y) {
		int a, b;
		if (nodeDepth[x] > nodeDepth[y]) {
			a = y;
			b = x;
		}
		else {
			a = x;
			b = y;
		}
		
		for (int i = 20; i >= 0; i--) {
			if (nodeDepth[b] - nodeDepth[a] >= (1 << i)) 
				b = nodeParent[b][i];
		}
		
		if (a == b) return a;
		
		for (int i = 20; i >= 0; i--) {
			if (nodeParent[a][i] != nodeParent[b][i]) {
				a = nodeParent[a][i];
				b = nodeParent[b][i];
			}
		}
		return nodeParent[a][0];
	}
	
	// query
	static void queryUpdate(int u, int v) {
		ArrayList<Pair> minus = new ArrayList<>(), plus = new ArrayList<>();
		int lca = getLca(u, v);
		while (chainHead[u] != chainHead[lca]) {
			minus.add(new Pair(chainHead[u], u));
			u = chainParent[u];
		}
		while (chainHead[v] != chainHead[lca]) {
			plus.add(new Pair(chainHead[v], v));
			v = chainParent[v];
		}

		if (nodeDepth[u] < nodeDepth[v]) plus.add(new Pair(u, v));
		else minus.add(new Pair(v, u));
		Collections.reverse(plus);
		
		int k = 0;
		for (Pair p : minus) {
			int len = hldNum[p.r] - hldNum[p.l] + 1;
			k += len;
			
			update(1, n, 1, hldNum[p.l], hldNum[p.r], -1);
			// 경로 구간 내 minus 구간은 -1 로 갱신
			
			update(1, n, 1, hldNum[p.l], hldNum[p.l], k);
			update(1, n, 1, hldNum[p.r] + 1, hldNum[p.r] + 1, -k + len);
			// 각각 순서대로, 경로 구간의 시작점 조정값, 경로 구간의 끝점 조정값이다
		}
		for (Pair p : plus) {
			int len = hldNum[p.r] - hldNum[p.l] + 1;
			update(1, n, 1, hldNum[p.l], hldNum[p.r], 1);
			// 경로 구간 내 plus 구간은 +1 로 갱신
			update(1, n, 1, hldNum[p.l], hldNum[p.l], k - 1);
			update(1, n, 1, hldNum[p.r] + 1, hldNum[p.r] + 1, -(k - 1) - len);
			// 각각 순서대로, 경로 구간의 시작점 조정값, 경로 구간의 끝점 조정값이다
			
			k += len;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		
		segTree = new long[4*n];
		lazy = new long[4*n];
		size = new int[n + 1];
		hldNum = new int[n + 1];
		nodeDepth = new int[n + 1];
		chainDepth = new int[n + 1];
		chainHead = new int[n + 1];
		chainParent = new int[n + 1];
		nodeParent = new int[n + 1][21];
		adj = new ArrayList[n + 1];
		
		for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();
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
		chainHead[1] = 1;
		dfs(1, 0, 1, 1);
		calcParent();
		
		int m = Integer.parseInt(br.readLine());
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int q = Integer.parseInt(st.nextToken());
			if (q == 1) {
				int u = Integer.parseInt(st.nextToken()),
					v = Integer.parseInt(st.nextToken());
				queryUpdate(u, v);
			}
			else {
				int x = Integer.parseInt(st.nextToken());
				sb.append(get(1, n, 1, 1, hldNum[x])).append("\n");
			}
		}
		System.out.print(sb);
	}
}
// Heavy-Light Decomposition을 통한 간선 단위로 구간값을 갱신하는데
// 갱신 값이 등차수열을 이루는 상황이다.

// 등차수열을 이룰때 구간에 값을 더하는 과정은 세그먼트 트리가 저장하는 값이
// 원본 배열 그 자체가 아니라
// 원본배열 따로 있고, imos법에서 처럼 변화값만을 따로 추적하게 한다
// 그리고  처음부터 그 특정 정점까지 변화값을 따라가서 원본값 + 최종변화값 을 더하면 결과값을 얻어올 수 있는데

// 지금 등차수열을 이룬다는 것은, 각 이웃한 두 값의 차이가 같게 된다(공차)
// 따라서 각 경로에 따른 구간에 공차를 더해주고
// 시작과 끝 지점에 다시 원래대로 돌아오는 조정값만 해주면 imos법으로 결과를 얻어올 수 있는데
// 이때 스위핑을 통한 O(N)이 아니라 세그먼트 트리로 구간합을 관리하여 O(logN)으로 구할 수 있게 한다

// 다시 돌아와서, Heavy-Light Decomposition을 통한 경로별 구간이 있고
// 여기에 변화값들을 세그먼트 트리를 이루어 저장할 건데

// 문제는 2가지이다.
// 1. 구간이 단순 배열의 구간처럼 전부 연속적이지 않다는 점
// 2. 같은 구간일지라도, 시작 정점에 따라 역순등차수열을 이룰 수도 있다는 점

// 이 2가지를 모두 고려하여 imos법을 활용한 Segment Tree 및 Lazy propagation을 해야한다

// 1번의 경우이다.
// 연속적이지는 않지만, HLD를 통해 일정 경로(chain) 단위로 구간을 나눌 수 있다
// 어느 한 경로를 나타내는 세부 경로들에 대해, 각 경로의 [시작-끝] 지점에 조정값을 넣는 것으로
// 연속적이지는 않으나, 연속적이게 처리를 할 수는 있을 것이다
// 이를 위해서는 u -> v를 이루는 각 경로를 모두 구분해내야 한다.

// 2번의 경우이다.
// 일단 방향을 root -> leaf로 내려 가는 것을 정방향이라 두고 생각해보자
// 경로 u -> v에서 u와 v의 LCA를 기준으로 경로를 일단 쪼개보면
// u -> LCA 까지는 역방향으로, 올라간다고 볼 수 있고
// LCA -> v 까지는 정방향으로, 내려간다고 볼 수 있다
// 이 관점에서 볼 때, 역방향은 등차수열이 역순으로 이루어져 더해져야 하기 때문에 공차를 -1로
// 정방향은 그대로 공차가 1로 두고 하면 되므로 아무런 문제 없다.

// 종합적으로 보면
// 정방향의 경우는 구간 [L, R] 에 대해 모두 +1을 갱신한 후 R+1 지점에 (-구간길이)만큼 조정값을 해주었으므로
// 역방향은 구간 [L, R]에 대해 모두 -1을 갱신한 다음 R+1 지점에 (+구간길이) 만큼 조정값을 해야한다
// 근데 이 구간들이 경로단위로 떨어져 있기 때문에
// 경로 u -> v의 전체 길이를 N이라 할 때
// HLD를 통한 경로 u -> v를 구성하는 k번째 세부 구간의 길이를 M(k)라 하자.

// 이 k번째 경로가 LCA보다 이전, 즉 역방향 구간을 구성하는 경로라고 할 때 imos법을 통한 구간 처리는
// 구간 [L, R]에 대해 모두 -1을 갱신한 다음 
// L지점에 지금 구간을 포함한 지금까지의 세부 구간 길이의 합을 더하여 조정값 갱신 
//		=> (M(1)+M(2)+...+M(k))을 갱신해준다
// R지점에 지금 구간을 포함하지 않고, 지금까지의 세부 구간 길이의 합을 빼는 것으로 갱신
//		=> -(M(1)+M(2)+...+M(k-1))을 갱신해준다

// 반대로, 정방향 구간을 구성하는 경로라 하면
// 구간 [L, R]에 대해 모두 +1을 갱신한 다음
// L지점에 지금 구간을 포함하지 않고, 지금까지의 세부 구간 길이의 합에서 지금 칸에 이미 +1이 갱신됐으므로 1을 빼서 갱신
//		=> (M(1)+M(2)+...+M(k-1) - 1)을 갱신해준다
// R지점에 지금 구간을 포함하지 않고, 지금까지의 세부 구간 길이의 함에서, 지금 구간의 길이만큼 갱신이 됐으므로 그만큼 더 빼서 갱신
// 		=> -(M(1)+M(2)+...+M(k-1)+M(k))을 갱신해준다

// 이렇게 갱신을 잘 해줬다면
// 이전 imos법을 통한 결과값을 얻어올때처럼 x번 정점의 결과값은 구간 [1,x]의 합을 구해오면 알 수 있다


// 일단 경로 u -> v에 대해 lca를 기준으로 -1구간, +1구간 까지는 생각을 했으나
// 연속된 구간에서만 imos법이 통한다고 생각하여
// root->x 까지의 경로로 imos법을 해야하는건가 싶어서 시도하다가
// 자식이 여러개인 노드의 경우, 경로에 포함되지 않는 자식노드에 대해서도 조정값 갱신이 들어가야 해서
// 여기서 잘못생각했음을 깨달았다.



