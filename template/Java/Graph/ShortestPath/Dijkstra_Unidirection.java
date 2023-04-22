import java.util.*;
import java.io.*;
public class Dijkstra_Unidirection {
	public static ArrayList<Edge>[] arr; // ���� ����
	public static int[] d; // �ּҺ��
	static class Edge implements Comparable<Edge>{
		int v;
		int w;
		public Edge(int v, int w)  {
			this.v = v;
			this.w = w;
		}
		public int compareTo(Edge o) {
			if (o.w < this.w) 
				return 1;
			else if (o.w > this.w)
				return -1;
			else 
				return 0;
		}
	}
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int V = scan.nextInt(); // ���� ���� (voltex)
		int E = scan.nextInt(); // ���� ���� (edge)
		int K = scan.nextInt(); // ������
		
		arr = new ArrayList[V + 1];
		for (int i = 1; i <= V; i++)
			arr[i] = new ArrayList<>();
		
		for (int i = 1; i <= E; i++) {
			int u = scan.nextInt(); 
			int v = scan.nextInt(); // u, v�� �̾��� ���� 2��
			int w = scan.nextInt(); // �ش� ������ ����ġ
			arr[u].add(new Edge(v, w)); // �ܹ����̹Ƿ�, �����-������ ���� ����.
		} // ���� ���� ��������Ʈ �Է�
		// �̶��� ���� ������ �ܹ��� �������� �����ض�.
	
		d = new int[V + 1]; // ������ �̵� �ּҺ��
		for (int i = 1; i <= V; i++)
			d[i] = Integer.MAX_VALUE;
		// �ּڰ��� ���� �� �ְ�, �ִܰŸ� �迭���� �ϴ� ��� int�� �ִ��� ����
		
		dijkstra(K);
		
		for (int i = 1; i <= V; i++) {
			if (d[i] == Integer.MAX_VALUE)
				System.out.print("INF ");
			else
				System.out.print(d[i] + " ");
		}
		System.out.println();
	}
	public static void dijkstra(int start) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		// �ش� �������� ���� ���������� �ִܰŸ��� �ٷ� �������� �켱���� ť
		pq.add(new Edge(start, 0));
		// => �̶�, ���������� ������������ �̵��Ÿ��� 0�̹Ƿ� 0���� �����Ѵ�.
		d[start] = 0;
		// ������������ �ִܰŸ��� 0�� �� �ۿ� ����.
		while (!pq.isEmpty()) {
			Edge curr = pq.poll();
			for (int i = 0; i < arr[curr.v].size(); i++) {
				Edge next = arr[curr.v].get(i);
				if (d[next.v] > d[curr.v] + next.w) {
					d[next.v] = d[curr.v] + next.w;
					pq.add(new Edge(next.v, next.w + d[curr.v]));
				}
				// ���� ���� ��ġ������ �̵��Ÿ��� �������� �� �۴ٸ�, ���� �������� �̵��Ÿ� ����
			}
		}
	} // => ������, while�� ������ ����� �Ÿ� ���� �� �ݺ������� ������ ������ �κи� �����ϰ� ť�� �߰�
	
	public static void dijkstra2(int start) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		pq.add(new Edge(start, 0));
		while (!pq.isEmpty()) {
			Edge curr = pq.poll();
			
			if (d[curr.v] > curr.w) {
				d[curr.v] = curr.w;
				for (int i = 0; i < arr[curr.v].size(); i++) {
					pq.add(new Edge(arr[curr.v].get(i).v, arr[curr.v].get(i).w + d[curr.v]));
				}
			}
		}
	} // ������ �ϴ� ��� (while�� ���ͼ�, ���� ����) => ������ �� ���� ���ϴ� ������ ť�� �ְ� ��ȿ��.
}
// �ܹ��� �׷��������� ���ͽ�Ʈ��
// �����->������ �� ����ġ�� �̵������ �ּڰ��� ���ϴ� ���.

//5 6
//1
//5 1 1
//1 2 2
//1 3 3
//2 3 4
//2 4 5
//3 4 6

// �Է��� ��������,
// ���� ������ 1������ ����,
// 1�� ���������� dijkstra[1] (���� d[curr.v]�� ǥ��)��
// int�� �ִ��̹Ƿ�, ������ �̵��Ÿ� 0���� ����
// �� ��������Ʈ���� ����� ���� ������ ���� bfs ����.
// �켱����ť(compareTo�� �񱳸� �Ѵ�)�� �����Ͽ�  �ּҰŸ��� �ڵ� ����
// �̋� ���� �ð����⵵�� O(logN)�̴�
// 1���� ���� ���� ��������Ʈ�� �ִ� ������ 2��, �Ÿ�2 / 3�� �Ÿ� 3�� ť�� ����
//���� ť : 2/2 3/3

// 2�� ����.
// 2�� ���� �Ÿ��� ���ŵ� ���� �����Ƿ�, ��������� �Ÿ� 2�� ����
// ���� ��������Ʈ 3��, �Ÿ�6 / 4��, �Ÿ�7 �� ť�� ����
// ���� ť : 3/3 3/6 4/7

// 3�� ����.
// 3�� ���� �Ÿ��� ���ŵ� ���� �����Ƿ�, ��������� �Ÿ� 3���� ����
// ���� ��������Ʈ 4��, �Ÿ� 9�� ť�� ����.
// ���� ť : 3/6 4/7 4/9

// �ٽ� 3��
// 1->2->3 �� ���� �� �Ÿ��̱⿡ 6.
// ���� d[3]���� �Ÿ��� 3�̹Ƿ�, �������� �ʰ� ����Ѵ�.
// ���� ť : 4/7 4/9

// 4�� ����.
// 4���� �Ÿ��� ���ŵ� ���� �����Ƿ�, ��������� �Ÿ� 7�� ����
// 4���� ��������Ʈ�� ������ ť�� �߰��� �����Ѵ�.
// ���� ť: 4/9

// �ٽ� 4��.
// d[4]���� 7�̹Ƿ�, �������� �ʰ� ���
// ���� ť : x

// ť�� ��� ������� �����Ѵ�.
// �� ������ ����, 5���� ���ŵ� ���� �����Ƿ� INF�� ǥ��.
// �� ������ �� ����.
// ����� �̰� �ܹ���, �� �̵������� ������ �׷��������� �ּ� �̵� ��ġ�̴�.
// ���� �Է��� ��� 5->1 / 1->2 / 1->3 / 2->3 / 2->4 / 3->4 �θ� �̵��� ����.
// ����, �������� 4������ �� ���, �ٸ� �������� �̵��� �Ұ���
// INF�� 4���� ������ ���� �� �� �ִ�.