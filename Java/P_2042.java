import java.util.*;
import java.io.*;
public class Main {
	public static long[] arr, segTree;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken()),
			k = Integer.parseInt(st.nextToken());
		
		arr = new long[n + 1];
		segTree = new long[4*n + 1];
		for (int i = 1; i <= n; i++)
			arr[i] = Long.parseLong(br.readLine());
		
		makeSegTree(1, n, 1);
		
		for (int i = 0; i < m + k; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken());
			
			if (a == 1) {
				long c = Long.parseLong(st.nextToken());
				long diff = c - arr[b];
				arr[b] = c;
				updateSegTree(1, n, 1, b, diff);
			}
			else {
				int c = Integer.parseInt(st.nextToken());
				sb.append(getSum(1, n, 1, b, c) + "\n");
			}
		}
		System.out.print(sb);
	}
	public static long makeSegTree(int start, int end, int curr) {
		if (start == end) 
			return segTree[curr] = arr[start];
		
		int mid = (start + end) / 2;
		return segTree[curr] = makeSegTree(start, mid, curr * 2) +
				makeSegTree(mid + 1, end, curr * 2 + 1);
	}
	public static long getSum(int start, int end, int curr, int left, int right) {
		if (left > end || right < start)
			return 0;
		if (left <= start && end <= right)
			return segTree[curr];
		
		int mid = (start + end) / 2;
		return getSum(start, mid, curr * 2, left, right) +
				getSum(mid + 1, end, curr * 2 + 1, left, right);
	}
	public static void updateSegTree(int start, int end, int curr, int idx, long diff) {
		if (idx < start || idx > end)
			return;
		
		segTree[curr] += diff;
		if (start == end)
			return;
		int mid = (start + end) / 2;
		updateSegTree(start, mid, curr * 2, idx, diff);
		updateSegTree(mid + 1, end, curr * 2 + 1, idx, diff);
	}
}
// 2021-01-21 02:04
// 세그먼트 트리의 기초
