import java.util.*;
import java.io.*;
public class SegmentTree {
	static int findNode(int start, int end, int node, int k) {
		if (start == end) return node;
		
		int mid = (start + end) / 2;
		if (k <= mid) return findNode(start, mid, 2*node, k);
		else return findNode(mid + 1, end, 2*node + 1, k);
	}
	static void makeSegTree2(int start) {
		for (int i = start - 1; i >= 1; i--) 
			segTree[i] = segTree[2*i] + segTree[2*i + 1];
	}
	// 어차피 이진트리로 구성이 된다면,
	// 리프노드중 가장 앞서는 노드의 바로 이전노드부터 시작해서 자식으로부터의 계산을 모두 하면
	// O(N)으로 생성이 가능한 방법
	
	public static int makeSegTree(int start, int end, int node) {
		if (start == end)
			return segTree[node] = arr[start];
		
		int mid = (start + end) / 2;
		return segTree[node] = makeSegTree(start, mid, node * 2) + 
				makeSegTree(mid + 1, end, node*2 + 1);
	}
	
	public static int getSum(int start, int end, int node, int left, int right) {
		if (left > end || right < start) 
			return 0;
		if (left <= start && end <= right)
			return segTree[node];
		
		int mid = (start + end) / 2;
		return getSum(start, mid, node * 2, left, right) +
				getSum(mid + 1, end, node * 2 + 1, left, right);
	}
	
	public static void updateSegTree(int start, int end, int node, int idx, int diff) {
		if (idx < start || idx > end)
			return;
		
		segTree[node] += diff;
		if (start == end)
			return;
		int mid = (start + end) / 2;
		updateSegTree(start, mid, node * 2, idx, diff);
		updateSegTree(mid + 1, end, node * 2 + 1, idx, diff);
	}
	public static int[] segTree, arr;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken()),
			k = Integer.parseInt(st.nextToken());
		
		int segTreeSize = (1 << ((int)Math.ceil(Math.log(n)) + 1)) * 2;
		//int segTreeSize = 4*n;
		// 2^k로 n보다 바로 큰 값을 만들 수 있는 k에 대해 2^(k + 1)
		// 귀찮으면 그냥 4*n 해도 되긴 한다.

		segTree = new int[segTreeSize + 1];
		arr = new int[n + 1];
		for (int i = 1; i <= n; i++)
			arr[i] = Integer.parseInt(br.readLine());
		
		makeSegTree(1, n, 1);
		// 세그먼트 트리 생성
		
		for (int i = 0; i < m + k; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken()),
				c = Integer.parseInt(st.nextToken());
			if (a == 1) 
				updateSegTree(1, n, 1, b, c - arr[b]);
			else if (a == 2) 
				System.out.println(getSum(1, n, 1, b, c));
		}
	}
	
}

// 세그먼트 트리는 구간 합을 저장하는 트리로, 리프노드는 원본 배열과 같다.
// 리프노드로 원본 배열의 갯수만큼 가지기 때문에
// 세그먼트 트리를 생성하려면 원본배열 크기보다 가장 가까우면서 큰 2^k에 대해 2^(k+1)개의 노드가
// 있어야 무리없이 세그먼트 트리를 형성하며, 귀찮으면 그냥 원본배열 크기의 4배하면 된다.

// 1. 생성
// 세그먼트 트리의 생성은 재귀함수를 통해 이루어지며
// 매개변수로는 세그먼트 트리를 만들고자 하는 배열의 시작 인덱스, 끝 인덱스 및 시작노드 1이다.
// 시작노드 1은 루트노드이므로, 노드는 후에 생성 과정에 따라 자식노드를 가리키는 숫자로 바뀜.
// 시작 인덱스와 끝 인덱스가 같은 경우 
// => 리프노드에 도달했다는 의미이므로, 원본 배열의 값을 현재 위치한 노드에 삽입
// 그 이외
// => mid = (start + end) / 2로  중간지점을 쪼갠다.
// 그리고 현재 노드의 값은 makeSegTree(start, mid, node * 2) +
//						makeSegTree(mid + 1, end, node * 2 + 1)
// 이는 현재 노드에 들어갈 구간 합의 값은
// 중간 지점을 기준으로 좌측 구간의 합 + 우측 구간의 합을 의미한다.
// 따라서 구간의 크기가 1, 즉 리프노드가 될때까지 쪼개고 나면 재귀함수를 통하여 모든 구간의 합이 구해지는 셈

// 2. 구간 값 가져오기
// 마찬가지로 재귀함수를 통해 이루어지며
// 매개변수로는 세그먼트 트리를 만든 배열의 시작 인덱스, 끝 인덱스 및 시작노드 1
// (시작 인덱스 및 끝 인덱스는 나중에 가서 현재 탐색중인 노드의 구간합의 범위가 된다)
// 그리고 추가로 구하고자 하는 구간합의 구간 시작 인덱스, 끝 인덱스. 이렇게 5개이다.
// 일단 구간 시작 인덱스와 끝 인덱스가 배열의 시작, 끝 범위를 벗어나는 경우는 0을 반환
// 구간합의 값을 반환하는 조건은
// 구간 시작 인덱스가 현재 탐색 노드의 구간 합 범위 시작 인덱스보다 크거나 같고
// 구간 끝 인덱스가 현재 탐색 노드의 구간 합 범위 끝 인덱스보다 작거나 같을 때
// => 즉 정리하면 탐색 구간이 구하고자 하는 구간합의 구간에 속하면 현재 구간을 쪼개지 않고 바로 반환
// 그러지 않은 부분에 대해서는 구간을 쪼개서 구한다.
// 이를 재귀적으로 범위를 나눠가며 탐색을 진행한다.

// 3. 특정 인덱스 값 수정
// 마찬가지로 재귀함수를 통해 이루어진다.
// 매개변수로는 세그먼트 트리를 만든 배열의 시작 인덱스, 끝 인덱스 및 시작노드 1
// (시작 인덱스 및 끝 인덱스는 나중에 가서 현재 탐색중인 노드의 구간합의 범위가 된다)
// 그리고 추가로 바꾸고자 하는 인덱스와 지금의 값으로부터의 변화값이 주어진다
// 구간 값 가져오기와 마찬가지로 범위를 벗어나는 위치에 대해서는 아무런 행동을 하지 않으며
// 범위에 속하는 경우에는 모든 구간값에 대해 변화값을 더해준다
// 최종적으로 루트부터 리프노드까지 구간에 속하는 모든 구간합의 값에 대해 변화값을 더하는 셈이 된다
