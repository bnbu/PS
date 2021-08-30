import java.util.*;
import java.io.*;
public class Main {
	public static int div = 1000000007;
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
			arr[i] = Integer.parseInt(br.readLine());
		
		makeSegTree(1, n, 1);
		
		for (int i = 0; i < m + k; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken()),
				c = Integer.parseInt(st.nextToken());
			
			if (a == 1) {
				update(1, n, 1, b, c);
				arr[b] = c;
			}
			else 
				sb.append(get(1, n, 1, b, c) + "\n");
		}
		System.out.print(sb);
	}
	public static long makeSegTree(int start, int end, int curr) {
		if (start == end)
			return segTree[curr] = arr[start];
		
		int mid = (start + end) / 2;
		return segTree[curr] = ((makeSegTree(start, mid, curr*2) % div) *
				(makeSegTree(mid + 1, end, curr*2 + 1) % div)) % div;
	}
	public static long get(int start, int end, int curr, int left, int right) {
		if (left > end || right < start)
			return 1;
		if (left <= start && right >= end)
			return segTree[curr];
		
		int mid = (start + end) / 2;
		return ((get(start, mid, curr*2, left, right) % div) * 
				(get(mid + 1, end, curr*2 + 1, left, right)) % div) % div;
	}
	public static long update(int start, int end, int curr, int idx, long value) {
		if (idx < start || idx > end)
			return segTree[curr];
		
		if (start == end && start == idx)
			return segTree[curr] = value;
		
		int mid = (start + end) / 2;
		return segTree[curr] = ((update(start, mid, curr*2, idx, value) % div) *
				(update(mid + 1, end, curr*2 + 1, idx, value) % div)) % div;
	}
}
// 2021-01-22 01:13
// 세그트리를 활용한 구간 곱
// 근데 이거 거의 대충 이렇겐가? 싶은 느낌으로 막 코딩했는데 이게 되네

// 구간합은 루트부터 내려가면서 변경된 idx가 포함된 구간의 값을 모두 차이만큼 더했다면
// 구간곱은 루트부터 변경된 위치의 값까지 쭉 내려가면서 값을 모두 재계산한다.
// 모듈러 연산떄문에 값을 나눴다가 곱하는 것은 절대 불가능이고
// start == end이면서 동시에 start == idx이면, 리프노드에 도달한 것이므로
// 여기서 값을 변경시키고 다시 재귀를 거슬러 올라오며 걸쳐져 있는 구간들의 값을
// 구간 곱으로 다시 재계산한다.
