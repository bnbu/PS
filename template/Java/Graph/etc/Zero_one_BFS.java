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
//다익스트라처럼 현재의 최소거리가 갱신이 될때, 갱신 후 큐에 삽입한다.
//이때 그냥 큐를 쓰지 않으며, 우선순위큐도 쓰지 않는다.
//deque를 사용하며,
//다음 정점까지의 비용이 1이면, deque의 뒤에 추가
//다음 정점까지의 비용이 0이면, deque의 앞에 추가