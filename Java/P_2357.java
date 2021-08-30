import java.util.*;
import java.io.*;
public class Main {
	public static int[] arr, maxSegTree, minSegTree;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		arr = new int[n + 1];
		maxSegTree = new int[4*n + 1];
		minSegTree = new int[4*n + 1];
		Arrays.fill(minSegTree, Integer.MAX_VALUE);
		
		for (int i = 1; i <= n; i++)
			arr[i] = Integer.parseInt(br.readLine());
		
		makeMaxSegTree(1, n, 1);
		makeMinSegTree(1, n, 1);
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken());
			
			sb.append(getMin(1, n, 1, a, b) + " " + getMax(1, n, 1, a, b) + "\n");
		}
		System.out.print(sb);

	}
	public static int makeMaxSegTree(int start, int end, int curr) {
		if (start == end)
			return maxSegTree[curr] = arr[start];
		
		int mid = (start + end) / 2;
		return maxSegTree[curr] = Math.max(makeMaxSegTree(start, mid, curr * 2),
				makeMaxSegTree(mid + 1, end, curr * 2 + 1));
	}
	public static int getMax(int start, int end, int curr, int left, int right) {
		if (left > end || right < start)
			return 0;
		if (left <= start && right >= end)
			return maxSegTree[curr];
		
		int mid = (start + end) / 2;
		return Math.max(getMax(start, mid, curr*2, left, right),
				getMax(mid + 1, end, curr*2 + 1, left, right));
	}
	
	public static int makeMinSegTree(int start, int end, int curr) {
		if (start == end)
			return minSegTree[curr] = arr[start];
		
		int mid = (start + end) / 2;
		return minSegTree[curr] = Math.min(makeMinSegTree(start, mid, curr * 2),
				makeMinSegTree(mid + 1, end, curr * 2 + 1));
	}
	public static int getMin(int start, int end, int curr, int left, int right) {
		if (left > end || right < start)
			return Integer.MAX_VALUE;
		if (left <= start && right >= end)
			return minSegTree[curr];
		
		int mid = (start + end) / 2;
		return Math.min(getMin(start, mid, curr*2, left, right),
				getMin(mid + 1, end, curr*2 + 1, left, right));				
	}
}
// 2021-01-22 22:19
// 세그먼트트리를 이번엔 최대/최소로 적용
// 이로써 구간합/구간곱/구간최대,최소 를 다 해봤다.
