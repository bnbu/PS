import java.util.*;
public class Main {
	public static int[] broken, left;
	public static int[] index;
	public static int sum = 0;
	public static int minClick;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt(), m = scan.nextInt();
		String s = Integer.toString(n);
		int current = 100;
		minClick = n < current ? current - n : n - current;
		if (m > 0 && m <= 10) {
			broken = new int[m];
			left = new int[10 - m];
			index = new int[s.length() + 1];
			int idx = 0;
			for (int i = 0; i < m; i++)
				broken[i] = scan.nextInt();
			
			Arrays.sort(broken);
			for (int i = 0; i < 10; i++) {
				if (Arrays.binarySearch(broken, i) >= 0)
					continue;
				else
					left[idx++] = i;
			}

			
			dfs(0, s.length(), n);
		}
		else {
			minClick = minClick < s.length() ? minClick : s.length();
		}

		System.out.println(minClick);
		
	}
	
	public static void dfs(int num, int length, int channel) {
		if (num == length) {
			sum = 0;
			for (int i : index) {
				sum *= 10;
				sum += left[i];
				int temp = channel < sum ? sum - channel : channel - sum;
				temp += Integer.toString(sum).length();
				minClick = minClick < temp ? minClick : temp;
			}
			return;
		}
		
		for (int i = 0; i < left.length; i++) {
			index[num] = i;
			dfs(num + 1, length, channel);
		}
	}
}
// 2020-09-04 03:34 해결
// binarySearch 에서 런타임 에러가 뜨는거일줄은;
// 입력이 번호순대로 준다고 한적은 없었다.
