import java.util.*;
public class Main {
	public static int bcnt = 0;
	public static int wcnt = 0;
	public static int n;
	public static ArrayList<Point> black = new ArrayList<>();
	public static ArrayList<Point> white = new ArrayList<>();
	public static Stack<Point> bishop = new Stack<>();
	public static Point chkPoint;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		n = scan.nextInt();
		
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				int temp = scan.nextInt();
				
				if (temp == 1) {
					if (i % 2 == 0) {
						if (j % 2 == 0)
							white.add(new Point(j, i));
						else
							black.add(new Point(j, i));
					}
					else {
						if (j % 2 == 0)
							black.add(new Point(j, i));
						else
							white.add(new Point(j, i));
					}
				}
			}
		blackSearch(0);
		whiteSearch(0);
		System.out.println(bcnt + wcnt);
	}
	public static void blackSearch(int idx) {
		bcnt = Math.max(bishop.size(), bcnt);
		for (int i = idx; i < black.size(); i++) {
			int tempX = black.get(i).x, tempY = black.get(i).y;
			if (check(bishop.size(), tempX, tempY)) {
				bishop.push(black.get(i));
				blackSearch(i);
				bishop.pop();
			}
		}
	}
	public static void whiteSearch(int idx) {
		wcnt = Math.max(bishop.size(), wcnt);
		for (int i = idx; i < white.size(); i++) {
			int tempX = white.get(i).x, tempY = white.get(i).y;
			if (check(bishop.size(), tempX, tempY)) {
				bishop.push(white.get(i));
				whiteSearch(i);
				bishop.pop();
			}
		}
	}
	public static boolean check (int size, int x, int y) {
		for (int i = 0; i < size; i++) {
			chkPoint = bishop.get(i);
			if (Math.abs(x - chkPoint.x) == Math.abs(y - chkPoint.y)) {
				return false;
			}
		}
		return true;
	}
}
class Point {
	public int x;
	public int y;
	public int color;
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
// 2020-09-24 22:49 해결/
// 비숍을 놓은 지점에서 이후에 비숍을 놓을 수 없는 지점을 만드는 것을 할것.
// ==> 비숍은 대각선, 즉 놓인 위치의 색깔만 간다.

// 정답은 흑색과 백색을 쪼개서 탐색하는 것이었다.
// 흑색 + 백색을 동시에 포함해서 탐색시, 봐야할 경우의 수가 존1나게 많음
// 근데 흑색 백색 쪼개서 생각하면 그 많은걸 그나마 줄여볼 수 있음
// ** 흑색타일에 놓인 비숍과 백색타일에 놓인 비숍은 서로 독립관계로 영향을 미치지 않아 가능한 것.
