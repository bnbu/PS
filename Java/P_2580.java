import java.util.*;

public class Main {
	public static int emptyCount = 0;
	public static ArrayList<Integer> emptyPointX = new ArrayList<>();
	public static ArrayList<Integer> emptyPointY = new ArrayList<>();
	public static boolean[][] check;
	public static int[][] sudoku = new int[9][9];
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku[i].length; j++) {
				int input = scan.nextInt();
				sudoku[i][j] = input;
				
				if (input == 0) {
					emptyCount++;
					emptyPointX.add(j);
					emptyPointY.add(i);
				}
			}
		}
		check = new boolean[emptyCount][10];
		if (emptyCount != 0)
			dfs(0);
		else
			showSudoku();
	}

	public static void dfs(int index) {
		int x = emptyPointX.get(index);
		int y = emptyPointY.get(index);
		checkAround(x, y, index);
		//System.out.println(index);
		
		for (int i = 1; i <= 9; i++) {
			if (check[index][i]) {
				//System.out.println(index + " " + i + " pass");
				continue;
			}
			else {
				sudoku[y][x] = i;
				//System.out.println(index + " " + i);
				if (index < emptyCount - 1) {
					dfs(index + 1);
				}
			}
		}
		
		if (index == emptyCount - 1) {
			showSudoku();
			System.exit(0);
		}
		sudoku[y][x] = 0;
		for (int i = 1; i < 10; i++)
			check[index][i] = false;
	}
	
	public static void checkAround(int x, int y, int emptyPointIndex) {
		int startIndexX = (x / 3) * 3;
		int startIndexY = (y / 3) * 3;
		for (int i : sudoku[y]) {
			if (i == 0)
				continue;
			check[emptyPointIndex][i] = true;
		}
		for (int i = 0; i < 9; i++) {
			if (sudoku[i][x] == 0)
				continue;
			check[emptyPointIndex][sudoku[i][x]] = true;
		}
		for (int i = startIndexY; i < startIndexY + 3; i++)
			for (int j = startIndexX; j < startIndexX + 3; j++)
				check[emptyPointIndex][sudoku[i][j]] = true;
	}
	
	public static void showSudoku() {
		for (int[] arr : sudoku) {
			for (int i : arr)
				System.out.print(i + " ");
			System.out.println();
		}
	}
}
// 2020-09-02 01:24 해결...
// 난잡한 백트래킹이고, 완성된 스도쿠 1개만 출력하면 되기에 그냥 바로 하나 출력되면 멈추게 해버림.
// 하지만, 메모리와 시간이 다른 케이스들과 비교했을때 최악중 최악인 느낌이다.
// 개선책을 찾아봐야겠다.

// 2020-09-02 01:36
// 개선해봤는데, 체크할때마다 초기화시켜거 그런가본데
// 초기화하는 부분을 백트래킹 끝난 후 이전으로 되돌아갈때 하게 한 다음
// 주변 체크시 모두 배열을 새로 만들지 않고, 바로 체크하게끔 만들었더니 메모리가 비약적으로 감소.
