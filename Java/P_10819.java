import java.util.*;
public class Main {
	public static int[] arr;
	public static int[] index;
	public static int max = Integer.MIN_VALUE;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		arr = new int[n];
		index = new int[n];
		for (int i = 0; i < n; i++)
			arr[i] = scan.nextInt();
		
		dfs(0, index.length);
		System.out.println(max);
	}

	public static void dfs (int num, int length) {
		if (num == length) {
			boolean overlapped = false;
			for (int i = 0; i < index.length; i++) {
				int temp = index[i];
				for (int j = 0; j < index.length; j++) {
					if (i == j)
						continue;
					
					if (temp == index[j]) {
						overlapped = true;
						break;
					}
				}
			}
			if (!overlapped) {
				int sum = 0;
				for (int i = 0; i < index.length - 1; i++) {
//					System.out.print(index[i] + " " + index[i + 1]);
					sum += arr[index[i]] < arr[index[i + 1]] ? arr[index[i + 1]] - arr[index[i]]
							: arr[index[i]] - arr[index[i + 1]];
//					System.out.println();
				}
				if (sum == 62) {
					for (int i = 0; i < index.length; i++)
						System.out.print(arr[index[i]] + " ");
					System.out.println();
				}
				max = max < sum ? sum : max;
//				System.out.println();
				return;
			}
			else
				return;
		}
		
		for (int i = 0; i < arr.length; i++) {
			index[num] = i;
			dfs(num + 1, length);
		}
	}
}
// 2020-09-04 20:05 해결
// 백트래킹을 통한 순회. 어쨋든 2개씩 매칭되기 때문에 2개씩 매칭 시켜 구한 최소값을 구한다.
// 물론 2번의 연산을 반복할 수 있다.
// 심지어 8개 이상의 케이스부턴 속도 저하가 눈에 보일정도고
// 10개부턴 거의 안나온다 봐야함. => 16193의 경우처럼 그리디 알고리즘으로 현재 경우에 대한 최선의 방법을 택하는 것으로 해결
// 메모리랑 시간은 좀 놀랍던데 어떻게 해야할까?
