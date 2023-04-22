import java.util.*;
import java.io.*;
public class Prim {
	public static class Point implements Comparable<Point>{
		int d;
		int w;
		public Point(int d, int w) {
			this.d = d;
			this.w = w;
		}
		public int compareTo(Point p) {
			return this.w - p.w;
		}
	}
	public static boolean[] selected;
	public static ArrayList<Point>[] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int v = Integer.parseInt(st.nextToken()),
			e = Integer.parseInt(st.nextToken());
		
		selected = new boolean[v + 1];
		adj = new ArrayList[v + 1];
		for (int i = 1; i <= v; i++)
			adj[i] = new ArrayList<>();
		while (e-- > 0) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken()),
				d = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			adj[s].add(new Point(d, w));
			adj[d].add(new Point(s, w));
		}	
		System.out.println(prim(v));
	}
	public static int prim(int v) {
		PriorityQueue<Point> pq = new PriorityQueue<>();
		pq.add(new Point(1, 0)); // ������
		int cost = 0;
		for (int i = 1; i <= v; i++) {
			Point curr = null;
			int min = Integer.MAX_VALUE;
			
			while (!pq.isEmpty()) {
				curr = pq.poll();
				if (!selected[curr.d]) {
					System.out.println(curr.d + " " + curr.w);
					min = curr.w;
					break;
				}
			}
			// ���� ó�� ���ۿ��� ����� 1�� (1,0)�� ����
			// while�� ���� �� �ؿ��� curr�� ������� 1�� ����Ű�Ƿ�
			// 1�� ����� �������� ��� pq�� �߰�
			// ���� �ٽ� pq���� 1�� ����� ������� �ݺ��Ѵ�
			
			if (min == Integer.MAX_VALUE)
				return Integer.MAX_VALUE;
			// => ����׷����� �ƴ϶�� ��.
			
			cost += min;
			selected[curr.d] = true; // ���� ������ Ʈ���� ���ԵǾ��ٰ� ǥ��
			
			for (int j = 0; j < adj[curr.d].size(); j++) 
				pq.add(adj[curr.d].get(j));
			// => �����Ų ������ ���� �������� �߰�
			// �켱����ť���� ���� �������� ���� ������ ���Ӱ� �߰��� �������� ���� �ִ�.
			
			System.out.println();
		}		
		return cost;
	}
}
// �켱����ť�� ����ϴ� ���� �˰���, ������ �������� ���ϴ� ���
// ������ �������� �����Ͽ� �� ������ ����ִ� Ʈ�� T�� ���Խ�Ų��
// T�� �ִ� ���� T�� ���� ��� ���� ���� �� ����ġ�� �ּ��� ������ ã�´�.
// ã�� ������ �����ϴ� �� ��� �� T�� ���� ��带 T�� ����
// �̸� ��� ��尡 ����, �� T�� ��� ��尡 ���Ե� ������ �ݺ��Ѵ�.

// ex)
// 6�� ����(1,2,3,4,5,6) 9���� ������ �ִ� ����
// 1 2 5
// 1 3 4
// 2 3 2
// 2 4 7
// 3 4 6
// 3 5 11
// 4 5 3
// 4 6 8
// 5 6 8

// => 1�� �������� �����Ѵٰ� ����
// 1���� T�� ���� (T���� 1�� �ִ�.)
// T�� �ִ� 1�� ����� ���� �� ����ġ�� �ּ��� 1-3(4)�� ����
// 3���� T�� ����  (T���� 1,3�� �ִ�.)
// T�� �ִ� 1,3�� ����� ������ ����ġ�� �ּ��� 2-3(2)�� ����
// 2���� T�� ���� (T���� 1,2,3�� �ִ�.)
// T�� �ִ� 1,2,3�� ����� ���� �� ����ġ�� �ּ��� 3-4(6)�� ����
// 4���� T�� ���� (T���� 1,2,3,4�� �ִ�.)
// T�� �ִ� 1,2,3,4�� ����� ���� �� ����ġ�� �ּ��� 4-5(3)�� ����
// 5���� T�� ���� (T���� 1,2,3,4,5�� �ִ�.)
// T�� �ִ� 1,2,3,4,5�� ����� ���� �� ����ġ�� �ּ��� 5-6(8) (��� ������ �̰Ż�)�� ����
// 6���� T�� ���� (T���� 1,2,3,4,5,6�� �ִ�.)
// ���� T�� ��� ��尡 �� ���ԵǾ���. => ����
// �ּҺ���� ������ ������ ����ġ�� �� (4+2+6+3+8)�� �ȴ�.

// �̸� �켱����ť�� ����Ͽ� ���ͽ�Ʈ��� ����� ������� Ʈ���� ���Ե�
// �������� �����ϴ� �ּҺ�밣���� ã�� �� �ִ�.
// �켱 ��������� ����- ������� ����� ����-����ġ �� ��� �켱����ť�� ����
// �ּҺ���� ������״ٸ�, �ݺ����� Ż��.
// => ���� �켱����ť���� ������ �߰���Ų ����-����ġ���
// �ּҺ������ �����Ų ���������� ����� ����-����ġ�� ��� ������ �ִ�.
// �̸� �̿��Ͽ� ��� �������� Ž���� �����ϰ� �� �߿��� �ּҺ�� �����鸸 �߷� Ž���� �����ϴ�.

// �ð����⵵�� ũ�罺Į�� ���� O(ElogV)