import java.util.*;
public class Main {
	public static int[][] arr;
 	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		int n = scan.nextInt();
		int swapNum = 0;
		arr = new int[2][n];
		for (int i = 0; i < n; i++)
			arr[1][i] = arr[0][i] = scan.nextInt();
		
		int temp = -1;
		for (int i = 0; i < n; i++) {
			if (arr[0][i] != i + 1) {
				temp = i;
				for (int j = temp; j < arr[0].length; j++) {
					if (arr[0][j] == temp + 1) {
						swap(0, temp, j);
						sb.append((temp + 1) + " " + (j + 1) + "\n");
						swapNum++;
						temp = -1;
						break;
					}
				}
			}
		}
		
		if (swapNum > 2) {
			sb = new StringBuilder();
			swapNum = 0;
			for (int i = n - 1; i >= 0; i--) {
				if (arr[1][i] != i + 1) {
					temp = i;
					for (int j = temp; j >= 0; j--) {
						if (arr[1][j] == temp + 1) {
							swap(1, j, temp);
							sb.append((j + 1) + " " + (temp + 1) + "\n");
							swapNum++;
							temp = -1;
							break;
						}
					}
				}
			}
		}
		
		for (int i = swapNum; i < 2; i++)
			sb.append(1 + " " + 1 + "\n");
		
		System.out.println(sb.toString());
	}

 	public static void swap(int index, int start, int end) {
 		Stack<Integer> stack = new Stack<>();
 		if (start == end)
 			return;
		else {
			for (int i = start; i <= end; i++) {
				stack.push(arr[index][i]);
			}

			for (int i = start; i <= end; i++) {
				arr[index][i] = stack.pop();
			}
		}
 	}
}
// 2020-09-05 23:53 해결
// 구간별로 나뉘는 거에 쏠려서 잘못 해석함
// 그리디 기법으로, 앞에서부터 탐색 시작하여, 해당 index에 없는 번호까지 구간을 모두 뒤집는 방식으로 진행,
// 만약 뒤집은 수가 2를 초과한다면 이번엔 뒤에서부터 다시 탐색
// 그리고 탐색 끝나고 2보다 작다면, 모자란 횟수만큼 1,1 출력
// 이 문제는 내가 풀지 못했다.. 잘못된 길로 갔다가 나오지를 못햇다
