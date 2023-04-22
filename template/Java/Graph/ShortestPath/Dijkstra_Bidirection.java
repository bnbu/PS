import java.util.*;
import java.io.*;
public class Dijkstra_Bidirection {
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
			arr[u].add(new Edge(v, w));
			arr[v].add(new Edge(u, w));
		} // ���� ���� ��������Ʈ �Է�
	
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
			if (d[curr.v] != curr.w)
				continue;
			// => ���� ������ �Ÿ��� �ִܰŸ��� �̹� �ִܰŸ��� �ƴ϶��,
			// ���� ���� �ִܰŸ��� �� �� �����Ƿ� �˻����� �ʴ´�.
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
// �ȸ��߰� ������ Ž���� �� ��������
// �ٽ� �� �����غ���? ������ �ּڰ� ���Ÿ� �ϸ� ������ �������� ���ϴ�
// ������ Ž���� ���ؼ��� �߰� Ž���� �̷������ �ʱ� ������ �ܼ��ϰ�
// ����� ���� ������ �ؼ� Ž���� �����ص� ū ����� ����.
// O(ElogV) ���⵵�� ����