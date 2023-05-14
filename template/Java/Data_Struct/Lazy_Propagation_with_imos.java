import java.util.*;
import java.io.*;
public class Main {
	static int n;
	static long[] segTree, lazy, arr;
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
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		segTree = new long[4*n];
		lazy = new long[4*n];
		arr = new long[n + 1];
		
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) arr[i] = Long.parseLong(st.nextToken());
		
		int m = Integer.parseInt(br.readLine());
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int q = Integer.parseInt(st.nextToken());
			if (q == 1) {
				int l = Integer.parseInt(st.nextToken()),
					r = Integer.parseInt(st.nextToken());
				
				update(1, n, 1, l, r, 1);
				update(1, n, 1, r + 1, r + 1, -(r - l + 1));
				// [L, R] 까지 구간 갱신 1씩 해서 등차수열 계산
				// R 다음 지점인 R+1 지점에서는 등차수열이 끝나버리는 지점이므로
				// 길이만큼 다시 값을 빼서 뒤의 배열값에 영향이 가지 않도록 조정
			}
			else {
				int x = Integer.parseInt(st.nextToken());
				sb.append(get(1, n, 1, 1, x) + arr[x]).append("\n");
				// imos법을 통한 변화값 + 원본값
			}
		}
		System.out.print(sb);
	}
}
// 수열의 특정 구간 [L, R]에 구간 갱신을 시도하는데
// 기존의 세그먼트 트리와는 다르게 하나의 값으로만 갱신을 하는 것이 아닌
// L부터 R까지 1, 2, 3, 4 ... , R-L+1 씩, 등차수열을 이루는 값들로
// 바꿔말해서, 값들간 규칙을 가지는 모두 서로 다른 값을 구간에 갱신시켜야 한다

// 일단 기존의 Lazy Propagation으로는 서로 다른 값을 구간에 갱신시키는 것은 불가능하다
// 가능하게 한다 하더라도 O(NlogN)이 되버릴 것

// 여기서 등차수열을 이루는 것과, Lazy propagation은 구간 내에 같은 값을 갱신 시키는것만 가능한 것을 주목해서
// 등차수열의 어느 위치든, 이웃한 두 값의 차이는 항상 같다. 이를 공차라 부르는데
// 이전 누적합의 imos법을 통해, 구간의 시작과 끝 지점에 변화값만 저장한 후
// 변화값 배열을 스위핑하여 O(N)으로 원본배열로부터, 변화를 모두 적용시킨 배열을 만들어 낼 수 있었다

// 여기서 착안하여, 세그먼트 트리는 모두 같은 값인 공차만을 저장하여
// 구간 [L, R] 에서 길이가 k인 등차수열의 갱신이 일어났음을 저장할 수 있다

// 하나의 예시로, 길이가 6인, 원본배열 [0, 0, 0, 0, 0, 0] 에서
// [0, 4]까지 구간 갱신이 일어났다면, 등차수열은 1, 2, 3, 4, 5가 된다
// 이를 imos법으로 한번의 스위핑으로 얻어내고자 한다면

// 변화값 배열을 [1, 1, 1, 1, 1, -5]로 잡아두고 스위핑을 통해 결과값을 얻어오면
// [1, (1+1), (1+1+1), (1+1+1+1), (1+1+1+1+1), (1+1+1+1+1-5)] 로 계산되어
// [1, 2, 3, 4, 5, 0] 의 배열을 얻어올 수 있다

// 기존 imos법으로 결과 배열의 특정 값을 계산하려면 스위핑을 통해 그 특정 정점까지 O(N)시간이 걸렸지만
// 이러한 변화값 배열을 세그먼트 트리로 관리하여 계산하는데 O(logN)으로 줄일 수 있었다.
