import java.util.*;
import java.io.*;
public class Main {
	static int n, m;
	static int[] segTree, lazy;
	static void propagate(int start, int end, int node) {
		lazy[node] %= 2;
		if (lazy[node] != 0) {
			if (start != end) {
				lazy[2*node] += lazy[node];
				lazy[2*node + 1] += lazy[node];
			}
			segTree[node] = (end - start + 1) - segTree[node]; 
			lazy[node] = 0;
		}
	}
	static void update(int start, int end, int node, int left, int right) {
		propagate(start, end, node);
		if (right < start || end < left) return;
		if (left <= start && end <= right) {
			lazy[node] += 1;
			propagate(start, end, node);
			return;
		}
		int mid = (start + end) / 2;
		update(start, mid, 2*node, left, right);
		update(mid + 1, end, 2*node + 1, left, right);
		segTree[node] = segTree[2*node] + segTree[2*node + 1];
	}
	static int get(int start, int end, int node, int left, int right) {
		propagate(start, end, node);
		if (right < start || end < left) return 0;
		if (left <= start && end <= right) return segTree[node];
		int mid = (start + end) / 2;
		return get(start, mid, 2*node, left, right) + get(mid + 1, end, 2*node + 1, left, right);
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		segTree = new int[4*n];
		lazy = new int[4*n];
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int o = Integer.parseInt(st.nextToken()),
				s = Integer.parseInt(st.nextToken()),
				t = Integer.parseInt(st.nextToken());
			
			if (o == 0) 
				update(1, n, 1, s, t);
			else
				sb.append(get(1, n, 1, s, t)).append("\n");
		}
		System.out.print(sb);
	}
}
/*
예를들어 어느 한 구간에 통째로 스위치가 반전되었다고 하자
그 구간은 스위치가 8개가 존재한다고 하고, 현재 3개가 켜져 있으면
전체가 반전되면 스위치는 8 - 3 해서 5개만 켜져있게 된다
이렇게 "구간" 반전으로 생각을 해보면
길이가 1인 구간, 즉 스위치 1개 그 자체는 0일때는 1로, 1일때는 0으로 알아서 반전되며
길이가 2인 구간에서는 스위치가 0개였으면 2로, 스위치가 1개였으면 1로, 스위치가 2개였으면 0으로 반전되게 된다
lazy propagation으로 구간 갱신을 하게 되면 어차피, 각 구간에 걸쳐서 걸리게 되므로
각 구간에서의 갱신을 (해당 구간의 길이 - 현재값) 으로 하게 하면 의도한대로 스위치 반전을 구현할 수 있다
조심해야할 것이, 갱신값이 누적될 경우인데
어느 한 구간의 스위치를 5번을 반전시켰다는 것은 5 % 2 = 1번 반전시킨것과 동일하다
따라서, 갱신 전에 lazy[node] % 2를 통해 갱신할 필요가 있는지를 먼저 따져야 한다
이렇게 스위치를 반전하게 되면 스위치 개수는 자동적으로 지켜지므로 나머지는 기존 세그먼트 트리 하던데로만 하면 된다
*/
