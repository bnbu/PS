import java.util.*;
import java.io.*;
public class Main {
	public static int[] count = new int[1000001];
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine());
		int[] ans = new int[n];
		int[] arr = new int[n];
		
		st = new StringTokenizer(br.readLine());
		Stack<Integer> stack = new Stack<>();
		Stack<Integer> last = new Stack<>();
		for (int i = 0; i < n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			count[arr[i]]++;
			stack.push(arr[i]);
			ans[i] = -1;
		}
		
		int idx = n - 2;
		while (idx >= 0) {
			int curr = stack.pop();
			if (count[arr[idx]] < count[curr]) {
				ans[idx] = curr;
				last.push(curr);
			}
			else {
				while(!last.isEmpty()) {
					int i = last.peek();
					if (count[arr[idx]] < count[i]) {
						ans[idx] = i;
						break;
					}
					last.pop();
				}
			}
			idx--;
		}
		
		for (int i : ans)
			sb.append(i + " ");
		System.out.println(sb);
	}
}
// 2021-01-12 17:18
// 이전 오큰수(17298번)에서 등장하는 수로 바뀐 응용문제
// 비교하는 값을 배열에서의 그 값이 아닌
// 배열에서 등장한 횟수(count 배열을 별도로 만듦)로 비교하면 된다.
