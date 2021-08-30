import java.util.*;
import java.io.*;
public class Zero_one_BFS {
	public static class Point {
		int v;
		int w;
		public Point(int v, int w) {
			this.v = v;
			this.w = w;
		}
	}
	public static int[] dist;
	public static ArrayList<Point>[] adj;
	public static void main(String[] args) {
		
	}
	public static void bfs(int v) {
		Deque<Point> dq = new LinkedList<>();
		dq.add(new Point(v, 0));
		dist[v] = 0;
		while (!dq.isEmpty()) {
			Point curr = dq.poll();
			for (int i = 0; i < adj[curr.v].size(); i++) {
				Point next = adj[curr.v].get(i);
				if (dist[next.v] > dist[curr.v] + next.w) {
					dist[next.v] = dist[curr.v] + next.w;
					if (next.w == 0) 
						dq.addFirst(new Point(next.v, dist[next.v]));
					else
						dq.addLast(new Point(next.v, dist[next.v]));
				}
			}
		}
	}
} 

//0-1 bfs
//���ͽ�Ʈ��ó�� ������ �ּҰŸ��� ������ �ɶ�, ���� �� ť�� �����Ѵ�.
//�̶� �׳� ť�� ���� ������, �켱����ť�� ���� �ʴ´�.
//deque�� ����ϸ�,
//���� ���������� ����� 1�̸�, deque�� �ڿ� �߰�
//���� ���������� ����� 0�̸�, deque�� �տ� �߰�