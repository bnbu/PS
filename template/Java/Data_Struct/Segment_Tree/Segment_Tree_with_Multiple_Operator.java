import java.util.*;
import java.io.*;
public class Main {
	static final long DIV = 1000000007;
	static int n;
	static long[] arr, segTree, plusLazy, mulLazy;
	static long makeSegTree(int start, int end, int node) {
		if (start == end) return segTree[node] = arr[start];
		int mid = (start + end) / 2;
		segTree[node] = (makeSegTree(start, mid, 2*node) + makeSegTree(mid + 1, end, 2*node + 1)) % DIV;
		return segTree[node];
	}
	static void propagate(int start, int end, int node) {
		if (plusLazy[node] != 0 || mulLazy[node] != 1) {
			segTree[node] *= mulLazy[node];
			segTree[node] %= DIV;
			segTree[node] += plusLazy[node] * (end - start + 1);
			segTree[node] %= DIV;
			if (start != end) {
				mulLazy[2*node] *= mulLazy[node];
				mulLazy[2*node] %= DIV;
				mulLazy[2*node + 1] *= mulLazy[node];
				mulLazy[2*node + 1] %= DIV;
				plusLazy[2*node] *= mulLazy[node];
				plusLazy[2*node] %= DIV;
				plusLazy[2*node + 1] *= mulLazy[node];
				plusLazy[2*node + 1] %= DIV;
				plusLazy[2*node] += plusLazy[node];
				plusLazy[2*node] %= DIV;
				plusLazy[2*node + 1] += plusLazy[node];
				plusLazy[2*node + 1] %= DIV;
			}
			plusLazy[node] = 0;
			mulLazy[node] = 1;
		}
	}
	static void update(int start, int end, int node, int left, int right, long plus, long mul) {
		propagate(start, end, node);
		if (right < start || end < left) return;
		if (left <= start && end <= right) {
			plusLazy[node] += plus;
			plusLazy[node] %= DIV;
			mulLazy[node] *= mul;
			mulLazy[node] %= DIV;
			propagate(start, end, node);
			return;
		}
		int mid = (start + end) / 2;
		update(start, mid, 2*node, left, right, plus, mul);
		update(mid + 1, end, 2*node + 1, left, right, plus, mul);
		segTree[node] = (segTree[2*node] + segTree[2*node + 1]) % DIV;
	}
	static long get(int start, int end, int node, int left, int right) {
		propagate(start, end, node);
		if (right < start || end < left) return 0;
		if (left <= start && end <= right) {
			return segTree[node] % DIV;
		}
		int mid = (start + end) / 2;
		return (get(start, mid, 2*node, left, right) + get(mid + 1, end, 2*node + 1, left, right)) % DIV;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		
		segTree = new long[4*n];
		plusLazy = new long[4*n];
		mulLazy = new long[4*n]; Arrays.fill(mulLazy, 1);
		arr = new long[n + 1];
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) arr[i] = Long.parseLong(st.nextToken());
		makeSegTree(1, n, 1);
		
		int m = Integer.parseInt(br.readLine());
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				x = Integer.parseInt(st.nextToken()),
				y = Integer.parseInt(st.nextToken());
			
			if (a == 4) {
				sb.append(get(1, n, 1, x, y)).append("\n");
				continue;
			}
			
			long v = Long.parseLong(st.nextToken());
			if (a == 1)
				update(1, n, 1, x, y, v, 1);
			else if (a == 2) 
				update(1, n, 1, x, y, 0, v);
			else {
				update(1, n, 1, x, y, 0, 0);
				update(1, n, 1, x, y, v, 1);
			}
		}
		System.out.print(sb);
	}
}
