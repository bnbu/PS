package D0508;
import java.util.*;
import java.io.*;
class Pair {
		int l, r;
}
public class SegmentTree_Euler_Tour_Technique {
	static int n, m, cnt;
	static long[] segTree, lazy;
	static int[] idx, ettNum, arr;
	static Pair[] range;
	static boolean[] isLeaf, visit;
	static ArrayList<Integer>[] adj;
	static void dfs(int curr) {
		ettNum[curr] = ++cnt;
		range[curr].l = cnt;
		for (int next : adj[curr]) {
			if (visit[next]) continue;
			dfs(next);
		}
		range[curr].r = cnt;
	}
	static void propagate(int start, int end, int node) {
		if (lazy[node] != 0) {
			if (!isLeaf[node]) {
        // leaf 노드 여부는 start != end로도 구분이 가능은 하다
				lazy[2*node] += lazy[node];
				lazy[2*node + 1] += lazy[node];
			}
			else 
				segTree[node] += lazy[node]; 
			lazy[node] = 0;
		}
	}
	static void update(int start, int end, int node, int left, int right, int k) {
		propagate(start, end, node);

		if (left > end || right < start) return;
		if (left <= start && end <= right) {
			lazy[node] += k;
			propagate(start, end, node);
			return;
		}
		
		int mid = (start + end) / 2;
		update(start, mid, 2*node, left, right, k);
		update(mid + 1, end, 2*node + 1, left, right, k);
	}
	static long get(int start, int end, int node, int k) {
		propagate(start, end, node);
		if (start == end) return segTree[node];		
		int mid = (start + end) / 2;
		if (k <= mid) return get(start, mid, 2*node, k);
		return get(mid + 1, end, 2*node + 1, k);
	}
	static int findNode(int start, int end, int node, int k) {
		if (start == end) return node;
		int mid = (start + end) / 2;
		if (k <= mid) return findNode(start, mid, 2*node, k);
		else return findNode(mid + 1, end, 2*node + 1, k);
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		segTree = new long[4*n];
		lazy = new long[4*n];
		isLeaf = new boolean[4*n];
		idx = new int[n + 1];
		
		arr = new int[n + 1];
		ettNum = new int[n + 1];
		range = new Pair[n + 1];
		visit = new boolean[n + 1];
		adj = new ArrayList[n + 1];
		
		for (int i = 1; i <= n; i++) {
			adj[i] = new ArrayList<>();
			idx[i] = findNode(1, n, 1, i);
			isLeaf[idx[i]] = true;
			range[i] = new Pair();
		}
		
		arr[1] = Integer.parseInt(br.readLine());
		for (int i = 2; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			arr[i] = Integer.parseInt(st.nextToken());
			adj[Integer.parseInt(st.nextToken())].add(i);
		}
		
		cnt = 0;
		dfs(1);
    // 오일러 경로를 통해 서브트리의 구간을 배열의 인덱스로 
		
		for (int i = 1; i <= n; i++) segTree[idx[ettNum[i]]] = arr[i];
		// for (int i = 1; i <= n; i++) segTree[idx[i]] = arr[ettNum[i]];
		// 맨 첨에 이렇게 해서 틀림, 
		// 정확하게는 i번 정점의 초기 값은 i번 정점이 방문하게 될 순서에 저장해야함
		
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			if (st.nextToken().equals("p")) {
				int a = Integer.parseInt(st.nextToken());
				update(1, n, 1, range[a].l + 1, range[a].r, Integer.parseInt(st.nextToken()));
			}
			else {
				sb.append(get(1, n, 1, ettNum[Integer.parseInt(st.nextToken())]));
				sb.append("\n");
			}
		}
		System.out.print(sb);
	}
}
// P_2820
// Euler Tour Technique
// 트리의 일정 구간에 특정 값의 합 등의 연산을 처리할때
// 트리를 root부터 오일러 경로를 통해 순회를 하면
// root부터 방문한 순서대로 정점에 새로운 번호를 매겨 재정렬할 수 있다

// 그리고 이전 방문한 장점으로 되돌아 왔을때, 지금의 방문 순서가
// 해당 노드의 하위 노드의 구간을 알 수 있게 된다

// 이렇게 노드별 서브트리의 구간을 알 수 있게 되면
// 이를 세그먼트 트리로 변형하여 풀 수 있게 된다.

// 위의 문제에 경우는 오일러 경로를 통한 정점의 방문 순서로 초기값을 초기화 할때 약간의 에러가 발생해서 틀렸다.
