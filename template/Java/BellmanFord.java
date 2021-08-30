import java.util.*;
import java.io.*;
public class BellmanFord {
	public static ArrayList<Pair>[] arr;
	public static int[] d;
	public static int n, m;
	public static boolean negativeCycle = false;
	static class Pair implements Comparable<Pair> {
		int x;
		int w;
		public Pair(int x, int w) {
			this.x = x;
			this.w = w;
		}
		public int compareTo(Pair p) {
			return this.w - p.w;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken()); // ���� ����
		m = Integer.parseInt(st.nextToken()); // ���� ����
		
		d = new int[n + 1];
		Arrays.fill(d, Integer.MAX_VALUE);
		arr = new ArrayList[n + 1];
		for (int i = 1; i < arr.length; i++)
			arr[i] = new ArrayList<>();
		
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			arr[u].add(new Pair(v, w));
		}
		
		System.out.println("Negative-Cycle�� ���� ���, �־��� ���̽� �� ���� ������ BellmanFord ���� ����");
		bellmanford(1);
		System.out.println();
		
		System.out.println("Negative-Cycle ���θ� �˾ƺ��� BellmanFord�� ���� ���");
		bellmanford_negativeCycle(1);
		System.out.println("negativeCycle : " + negativeCycle);
	}
	
	public static void bellmanford(int start) {
//		Arrays.fill(d, Integer.MAX_VALUE);
		d[start] = 0; // �������� 0���� �ؼ� ����.
		for (int i = 1; i < n; i++) {
			for (int j = 1; j <= n; j++)
				for (int k = 0; k < arr[j].size(); k++)
					if (d[j] != Integer.MAX_VALUE && d[arr[j].get(k).x] > d[j] + arr[j].get(k).w)
						d[arr[j].get(k).x] = d[j] + arr[j].get(k).w;
			print();
		}
		// ���� N���� ����  �̾��� �������� ��� �� Ž��.
		// �̶� ��� Ž���� ���� �־��� ���̽��� �Ϸķ� ���ִ� ���
		// �� ���� N���� ��, N-1���� �����۾��� ��ġ�� �ȴ�.
		// �̋� ������� �Ÿ��� 0���� ����.
	}
	public static void bellmanford_negativeCycle(int start) {
		Arrays.fill(d, Integer.MAX_VALUE);
		
		d[start] = 0; // �������� 0���� �ؼ� ����.
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++)
				for (int k = 0; k < arr[j].size(); k++)
					if (d[j] != Integer.MAX_VALUE && d[arr[j].get(k).x] > d[j] + arr[j].get(k).w) {
						d[arr[j].get(k).x] = d[j] + arr[j].get(k).w;
						if (i == n) 
							negativeCycle = true;
					}
			print();
		}
		// ���� ����Ŭ�� ���� ���,
		// �־��� ���̽��� ��, N-1�� �����۾� ����
		// �Ÿ��� ������ �̷������ �ȵǴµ�,
		// ���� ����Ŭ�� ������ ��쿡�� ���Ŀ��� �Ÿ� ������ �̷������
		// �̸� �̿��� N-1�� ����, N��° �������� ������ ���� ��
		// ���� ����Ŭ�� �ִٰ� �Ǵ��Ѵ�.
	}
	public static void print() {
		for (int i = 1; i < d.length; i++) {
			if (d[i] == Integer.MAX_VALUE)
				System.out.print("INF ");
			else
				System.out.print(d[i] + " ");
		}
		System.out.println();
	}
}
// ���������� ���� �̾��� ������� ���� �������� �Ÿ��� ����
// ���� �� �������� �̾��� ������� �ٽ� ���� �������� �Ÿ��� ����ϸ�
// �ּ� �Ÿ��� �Ǵ� ��� �ٽ� ������ �ݺ��Ѵ�.
// (�̶�, ������-�������� ���� �̾����� ��η� ���� �ȴ�)
// �� ��� ������ ��� ������ �� Ȯ���ϴ� �������
// ��� ������ ������ �� ���ǰ� �ǹǷ�, ���ͽ�Ʈ�󺸴ٴ� ���� O(VE)����
// ��� ������ ������ �� ���ǰ� �ǹǷ�, ���� ����ġ�� ������ �Ÿ��� �ִܰŸ��� ����� ����.
// �־��� ��쿡�� ������ ���� - 1������ (��� �Ϸķ� ����� ���)
// �����۾��� �ݺ��ؾ� ��� ������ �ȴ�.

// �׸��� ����, ���� ����Ŭ (����ġ�� ��� ������ �̷���� ����Ŭ)�� �����Ѵٸ�,
// �̷��� ����-���� �˰����� 1ȸ �� ������ ��,
// ���� �� �� ���� �ִܰŸ���
// ���� ����Ŭ�� ���ؼ� �� �پ���, �� ���ŵǴ� ��찡 �߻��Ѵ�
// �̷� ���� ���� ����Ŭ�� ���θ� �Ǵ��� �� �ִ�!
// �̸� ����Ű��, V���� �����϶�, 

// ���� ����Ŭ�� ��� �κп� ���� ����Ǿ� �ִ�����.. �װ� �𸣰ڰ�

// ����
// ���� ����Ŭ (X)
//3 4
//1 2 4
//1 3 3
//2 3 -1
//3 1 -2

// ���� ����Ŭ (O)
//3 4
//1 2 4
//1 3 3
//2 3 -4
//3 1 -2
