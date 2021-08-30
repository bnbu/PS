import java.util.*;
import java.io.*;
public class Main {
	public static class Point {
		long x;
		long y;
		public Point(long x, long y) {
			this.x = x;
			this.y = y;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		Point[][] line = new Point[2][2];
		
		for (int i = 0; i < 2; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 2; j++)
				line[i][j] = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		
		long ccw1 = ccw(line[0][0], line[0][1], line[1][0]),
			ccw2 = ccw(line[0][0], line[0][1], line[1][1]),
			ccw3 = ccw(line[1][0], line[1][1], line[0][0]),
			ccw4 = ccw(line[1][0], line[1][1], line[0][1]);
		
		if (((ccw1 < 0 && ccw2 > 0) || (ccw1 > 0 && ccw2 < 0)) &&
				((ccw3 < 0 && ccw4 > 0) || (ccw3 > 0 && ccw4 < 0)))
			System.out.println(1);
		else
			System.out.println(0);
	}
	public static long ccw (Point p1, Point p2, Point p3) {
		return (p1.x*p2.y + p2.x*p3.y + p3.x*p1.y)
				- (p1.y*p2.x + p2.y*p3.x + p3.y*p1.x);
	}
}
// 2021-01-16 20:31
// ccw를 활용한 선분교차 체크
// 오버플로우나서 틀렸던거;;
