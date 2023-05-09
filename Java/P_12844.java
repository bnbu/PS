import java.util.*;
import java.io.*;
public class Main {
	static int n, m;
	static int[] segTree, lazy, arr;
	static int makeSegTree(int start, int end, int node) {
		if (start == end) return segTree[node] = arr[start];
		int mid = (start + end) / 2;
		return segTree[node] = makeSegTree(start, mid, 2*node) ^ makeSegTree(mid + 1, end, 2*node + 1);
	}
	static void propagate(int start, int end, int node) {
		if (lazy[node] != 0) {
			if (start != end) {
				lazy[2*node] ^= lazy[node];
				lazy[2*node + 1] ^= lazy[node];
			}
			if ((end - start + 1) % 2 == 1) segTree[node] ^= lazy[node];
			lazy[node] = 0;
		}
	}
	static void update(int start, int end, int node, int left, int right, int k) {
		propagate(start, end, node);
		if (right < start || end < left) return;
		if (left <= start && end <= right) {
			lazy[node] ^= k;
			propagate(start, end, node);
			return;
		}
		int mid = (start + end) / 2;
		update(start, mid, 2*node, left, right, k);
		update(mid + 1, end, 2*node + 1, left, right, k);
		segTree[node] = segTree[2*node] ^ segTree[2*node + 1];
	}
	static int get(int start, int end, int node, int left, int right) {
		propagate(start, end, node);
		if (right < start || end < left) return 0;
		if (left <= start && end <= right) return segTree[node];
		int mid = (start + end) / 2;
		return get(start, mid, 2*node, left, right) ^ get(mid + 1, end, 2*node + 1, left, right);
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		
		segTree = new int[4*n];
		lazy = new int[4*n];
		
		arr = new int[n];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) arr[i] = Integer.parseInt(st.nextToken());
		makeSegTree(0, n - 1, 1);
		
		m = Integer.parseInt(br.readLine());
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int q = Integer.parseInt(st.nextToken()),
				i = Integer.parseInt(st.nextToken()),
				j = Integer.parseInt(st.nextToken());
			
			if (q == 1) {
				int k = Integer.parseInt(st.nextToken());
				update(0, n - 1, 1, Math.min(i, j), Math.max(i, j), k);
			}
			else {
				sb.append(get(0, n - 1, 1, Math.min(i, j), Math.max(i, j))).append("\n");
			}
		}
		System.out.print(sb);
	}
}

/*
보면 (start - end + 1) 부분이 음수가 될 수 있는데,
C++의 경우는 음수건 양수건 일단 if문의 조건 값이 0만 아니면 true로 쳐버린다
근데 Java에서는 boolean을 따로 1비트로 둬서 true, false로 존재하기 때문에 반드시 true, false가 아니면 안된다 (int로 0, 1도 불가능)
따라서 C++의 경우는 start - end + 1이건 end - start + 1이건 아무튼 0만 아니면 되는 상황이라 홀수인 경우에 lazy가 잘 적용됐지만,
Java의 경우에는 아쉽게도 반드시 1인 상황만 홀수로 여기기 때문에, 조건이 end - start + 1이어야 했었다
반대의 경우라면 음수가 나오게 될 경우 -1이 들어가서 옳게 적용되지 못하고 있었는데
어떻게 해본 예시들이 기적적이게 다 저 경우를 발견 못하게 지나갔나 싶을정도
*/
