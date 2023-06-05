package D0504;
import java.util.*;
import java.io.*;
public class Segment_Tree_Lazy_Propagation {
	static long[] segTree, lazy;
	static int[] nodeIdx;
	static boolean[] isLeaf;
	 static long makeSegTree(int start, int end, int node) {
			if (start == end) return segTree[node];
			int mid = (start + end) / 2;
			return segTree[node] = makeSegTree(start, mid, node * 2) + makeSegTree(mid + 1, end, node*2 + 1);
		}
	static int findNode(int start, int end, int node, int k) {
		if (start == end) return node;
		
		int mid = (start + end) / 2;
		if (k <= mid) return findNode(start, mid, 2*node, k);
		else return findNode(mid + 1, end, 2*node + 1, k);
	}
	static void propagate(int start, int end, int node) {
		// 갱신해야할 값이 존재한다면
		if (lazy[node] != 0) {
			// 그 중에서도 리프노드가 아니면, 갱신값을 자식노드에게도 전파
			if (!isLeaf[node]) {
				lazy[2*node] += lazy[node];
				lazy[2*node + 1] += lazy[node]; 
			}
			// 값의 갱신을 구간 길이만큼 반영하여 하고, lazy값을 다시 0으로 변경
			segTree[node] += lazy[node] * (long)(end - start + 1);
			lazy[node] = 0;
		}
	}
	static void add(int start, int end, int node, int left, int right, long k) {
		// 우선 lazy 값이 있다면 먼저 갱신
		propagate(start, end, node);
		
		if (left > end || right < start) return;
		if (left <= start && end <= right) {
			lazy[node] += k;
			propagate(start, end, node);
			return;
		}
		// 구간에 포함이 된다면, 그 곳에 바로 lazy값을 적용
		// 자식들에게 lazy를 전파해야 하므로 propagate 진행
				
		// 구간에 포함이 안된다면
		// 좌, 우로 나누어 진행을 시키고
		// 부모에 값이 적용되어야 하므로
		// 데이터 수정이 완료된 자식들로 값을 수정한다.
		int mid = (start + end) / 2;
		add(start, mid, 2*node, left, right, k);
		add(mid + 1, end, 2*node + 1, left, right, k);
		segTree[node] = segTree[2*node] + segTree[2*node + 1];
	}
	static long getSum(int start, int end, int node, int left, int right) {
		// 마찬가지로 우선 갱신해야할 값이 있는지 확인하여 갱신
		propagate(start, end, node);
		
		if (left > end || right < start) return 0;
		if (left <= start && end <= right) {
			return segTree[node];
		}
		
		int mid = (start + end) / 2;
		return getSum(start, mid, 2*node, left, right) + getSum(mid + 1, end, 2*node + 1, left, right);
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken()),
			k = Integer.parseInt(st.nextToken());
		
		segTree = new long[4 * n];
		lazy = new long[4 * n];
		isLeaf = new boolean[4 * n];
		nodeIdx = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			nodeIdx[i] = findNode(1, n, 1, i);
			segTree[nodeIdx[i]] = Long.parseLong(br.readLine());
			isLeaf[nodeIdx[i]] = true;
		}
		makeSegTree(1, n, 1);
		
		m += k;
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken()),
				c = Integer.parseInt(st.nextToken());
			
			if (a == 1) {
				add(1, n, 1, b, c, Long.parseLong(st.nextToken()));
			}
			else {
				sb.append(getSum(1, n, 1, b, c));
				sb.append("\n");
			}
			
		}
		System.out.print(sb);
	}
}
// Lazy propagation
// 기존 세그먼트 트리는 어느 구간의 특정 연산값을 알아오거나, 하나의 데이터를 수정등을 할 수 있었다
// 여기서 하나의 데이터가 아닌 구간의 데이터를 수정하고자 한다면
// 기존 수정 후 구간별 수정을 하는데 log n의 시간이 소요되었고
// 수정 구간 길이가 k라면 k*log n이 걸리게 된다.

// 이를 개선시켜서, 지금 당장 갱신시킨다 한들 바로 사용하지 않으면 의미가 없기 때문에
// 값의 수정이 발생했었고, 얼마만큼 수정할 계획이라는 세그먼트 트리와 같은 크기의 Lazy 배열을 두어서
// 실제 그 값을 사용할 일이 있을때 갱신을 하는 식으로 수정을 하면
// 구간 데이터 수정도 log n의 시간으로 해결할 수 있다.

// 구간 데이터의 수정은 다음의 과정을 통해 이루어진다
// 기존의 데이터 하나 수정과 다르게 구간 데이터 수정에는 수정하게 되는 구간의 시작, 끝 정보도 같이 주어진다
// 편의상, 현재 노드가 가리키는 구간을 start, end로 두고 수정하는 구간을 left, right로 두면
// left <= (start, end) <= right의 경우에는 해당 구간이 수정할 구간에 완전히 포함되는 상태이므로
// 이 노드의 lazy 배열에 바로 값을 추가시키고 propagate를 진행

// 벗어날 경우에는 구간합 구했던때처럼, 각 구간을 다시 분할하여 진행
// 이렇게 할 경우 나뉜 구간 갱신에 의해 바뀌는 자식 값이 존재할 것이므로
// 자신의 자식 값으로 자기 자신을 꼭 갱신을 해주어야 한다

// propagate는 다음의 과정을 통해 이루어진다
// 자기 자신의 lazy 값이 존재하고, 자기 자신이 리프노드가 아니라면 lazy 값을 그대로 자기 자식들에게 전파시키고
// 자기 자신이 포함하는 구간 길이 * lazy 값 만큼 갱신을 하게 된다.

// 주의점이 리프노드인지를 판단해야하는데
// 기존에 했던 재귀 방식으로 세그먼트 트리를 구축하게 되면
// 이진트리는 맞긴 한데, 리프노드의 레벨이 서로 상이하게 만들어진다
// 따라서 방식을 살짝 바꿔서
// 가장 앞선 데이터의 노드 인덱스 번호로부터 모두 +1 시켜가며 값을 넣고
// 이 노드 인덱스 번호의 바로 이전부터는 한단계 높은 레벨의 노드가 되므로
// 그러한 노드로부터 루트까지 역순으로 거슬러 올라가며 세그먼트 트리의 노드를 만드는 연산을 진행해가면
// 모든 리프노드의 레벨이 일치하고 (가장 앞선 데이터의 노드 인덱스보다 모두 뒤에 위치) 리프노드인지 여부를 알기 쉬워진다