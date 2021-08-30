import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static int emptyCount = 0;
	public static ArrayList<Integer> emptyPointX = new ArrayList<>();
	public static ArrayList<Integer> emptyPointY = new ArrayList<>();
	public static boolean[][] check;
	public static int[][] sudoku = new int[9][9];
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		for (int i = 0; i < sudoku.length; i++) {
			String line = scan.nextLine();
			for (int j = 0; j < sudoku[i].length; j++) {
				int input = (int)line.charAt(j) - 48;
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
				System.out.print(i);
			System.out.println();
		}
	}
}

// 2020-09-02 해결,
// 기존 그 스도쿠와 비슷했는데, 인풋만 수정함
// 사전순서상 먼저오는걸 출력하는건데, 내가 이전에 그거로 해서 걍 그대로 가져다 씀.
