import java.util.*;
import java.io.*;
public class SegmentTree {
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
		// 2^k�� n���� �ٷ� ū ���� ���� �� �ִ� k�� ���� 2^(k + 1)
		// �������� �׳� 4*n �ص� �Ǳ� �Ѵ�.

		segTree = new int[segTreeSize + 1];
		arr = new int[n + 1];
		for (int i = 1; i <= n; i++)
			arr[i] = Integer.parseInt(br.readLine());
		
		makeSegTree(1, n, 1);
		// ���׸�Ʈ Ʈ�� ����
		
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
}

// ���׸�Ʈ Ʈ���� ���� ���� �����ϴ� Ʈ����, �������� ���� �迭�� ����.
// �������� ���� �迭�� ������ŭ ������ ������
// ���׸�Ʈ Ʈ���� �����Ϸ��� �����迭 ũ�⺸�� ���� �����鼭 ū 2^k�� ���� 2^(k+1)���� ��尡
// �־�� �������� ���׸�Ʈ Ʈ���� �����ϸ�, �������� �׳� �����迭 ũ���� 4���ϸ� �ȴ�.

// 1. ����
// ���׸�Ʈ Ʈ���� ������ ����Լ��� ���� �̷������
// �Ű������δ� ���׸�Ʈ Ʈ���� ������� �ϴ� �迭�� ���� �ε���, �� �ε��� �� ���۳�� 1�̴�.
// ���۳�� 1�� ��Ʈ����̹Ƿ�, ���� �Ŀ� ���� ������ ���� �ڽĳ�带 ����Ű�� ���ڷ� �ٲ�.
// ���� �ε����� �� �ε����� ���� ��� 
// => ������忡 �����ߴٴ� �ǹ��̹Ƿ�, ���� �迭�� ���� ���� ��ġ�� ��忡 ����
// �� �̿�
// => mid = (start + end) / 2��  �߰������� �ɰ���.
// �׸��� ���� ����� ���� makeSegTree(start, mid, node * 2) +
//						makeSegTree(mid + 1, end, node * 2 + 1)
// �̴� ���� ��忡 �� ���� ���� ����
// �߰� ������ �������� ���� ������ �� + ���� ������ ���� �ǹ��Ѵ�.
// ���� ������ ũ�Ⱑ 1, �� ������尡 �ɶ����� �ɰ��� ���� ����Լ��� ���Ͽ� ��� ������ ���� �������� ��

// 2. ���� �� ��������
// ���������� ����Լ��� ���� �̷������
// �Ű������δ� ���׸�Ʈ Ʈ���� ���� �迭�� ���� �ε���, �� �ε��� �� ���۳�� 1
// (���� �ε��� �� �� �ε����� ���߿� ���� ���� Ž������ ����� �������� ������ �ȴ�)
// �׸��� �߰��� ���ϰ��� �ϴ� �������� ���� ���� �ε���, �� �ε���. �̷��� 5���̴�.
// �ϴ� ���� ���� �ε����� �� �ε����� �迭�� ����, �� ������ ����� ���� 0�� ��ȯ
// �������� ���� ��ȯ�ϴ� ������
// ���� ���� �ε����� ���� Ž�� ����� ���� �� ���� ���� �ε������� ũ�ų� ����
// ���� �� �ε����� ���� Ž�� ����� ���� �� ���� �� �ε������� �۰ų� ���� ��
// => �� �����ϸ� Ž�� ������ ���ϰ��� �ϴ� �������� ������ ���ϸ� ���� ������ �ɰ��� �ʰ� �ٷ� ��ȯ
// �׷��� ���� �κп� ���ؼ��� ������ �ɰ��� ���Ѵ�.
// �̸� ��������� ������ �������� Ž���� �����Ѵ�.

// 3. Ư�� �ε��� �� ����
// ���������� ����Լ��� ���� �̷������.
// �Ű������δ� ���׸�Ʈ Ʈ���� ���� �迭�� ���� �ε���, �� �ε��� �� ���۳�� 1
// (���� �ε��� �� �� �ε����� ���߿� ���� ���� Ž������ ����� �������� ������ �ȴ�)
// �׸��� �߰��� �ٲٰ��� �ϴ� �ε����� ������ �����κ����� ��ȭ���� �־�����
// ���� �� ��������� ���������� ������ ����� ��ġ�� ���ؼ��� �ƹ��� �ൿ�� ���� ������
// ������ ���ϴ� ��쿡�� ��� �������� ���� ��ȭ���� �����ش�
// ���������� ��Ʈ���� ���������� ������ ���ϴ� ��� �������� ���� ���� ��ȭ���� ���ϴ� ���� �ȴ�

