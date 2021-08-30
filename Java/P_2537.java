import java.util.*;
public class Main {
	public static final int[] dx = {0, 0, -1, 1};
	public static final int[] dy = {1, -1, 0, 0}; // 상 하 좌 우
	public static int visitCount = 1;
	public static boolean[][] visit;
	public static int[][] map;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt(), m = scan.nextInt();
		visit = new boolean[n][m];
		map = new int[n][m];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				map[i][j] = scan.nextInt();
		
		ArrayList<Integer> xList = new ArrayList<>();
		ArrayList<Integer> yList = new ArrayList<>();
		ArrayList<Integer> decreaseIndex = new ArrayList<>();
		ArrayList<Integer> decreaseDegree = new ArrayList<>();
		int year = 0;
		while (true) {
			xList.clear();
			yList.clear();
			decreaseIndex.clear();
			decreaseDegree.clear();
			
			visitCount = 1;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					visit[i][j] = false;
					if (map[i][j] != 0) {
						xList.add(j);
						yList.add(i);
					}
				}
			}
			
			if (xList.size() > 0)
				dfs(xList.get(0), yList.get(0));
			else {
				year = 0;
				break;
			}
//			System.out.println(visitCount);
//			for (boolean[] arr : visit) {
//				for (boolean b : arr)
//					System.out.print(b + " ");
//				System.out.println();
//			}
			if (visitCount < xList.size())
				break;
			
			year++;
			for (int i = 0; i < xList.size(); i++) {
				int x = xList.get(i), y = yList.get(i);
				int cnt = 0;
				boolean chk = false;
				for (int j = 0; j < 4; j++) {
					if (map[y + dy[j]][x + dx[j]] == 0) {
						cnt++;
						chk = true;
					}
				}

				if (chk) {
					decreaseIndex.add(i);
					decreaseDegree.add(cnt);
				}
			}

			for (int i = 0; i < decreaseIndex.size(); i++) {
				map[yList.get(decreaseIndex.get(i))][xList.get(decreaseIndex.get(i))] -= decreaseDegree.get(i);
				if (map[yList.get(decreaseIndex.get(i))][xList.get(decreaseIndex.get(i))] < 0)
					map[yList.get(decreaseIndex.get(i))][xList.get(decreaseIndex.get(i))] = 0;
			}
			
//			for (int[] arr : map) {
//				for (int i : arr)
//					System.out.print(i + " ");
//				System.out.println();
//			}
//			System.out.println();
		}
		System.out.println(year);
	}
	public static void dfs(int x, int y) {
		visit[y][x] = true;
		
		for (int i = 0; i < 4; i++) {
			if (map[y + dy[i]][x + dx[i]] != 0 && !visit[y + dy[i]][x + dx[i]]) {
				visitCount++;
				dfs(x + dx[i], y + dy[i]);
			}
		}
	}
}

// 2020-09-21 23:28 해결
