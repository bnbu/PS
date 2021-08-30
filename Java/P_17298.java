import java.util.*;
import java.io.*;
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine());
		int[] arr = new int[n];
		int[] ans = new int[n];
		
		st = new StringTokenizer(br.readLine());
		Stack<Integer> stack = new Stack<>();
		Stack<Integer> last = new Stack<>();
		for (int i = 0; i < n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			stack.push(arr[i]);
			ans[i] = -1;
		}
		
		int idx = n - 2;
		while (idx >= 0) {
			int i = stack.pop();
			if (arr[idx] < i) {
				ans[idx] = i;
				last.push(i);
			}
			else {
				while (!last.isEmpty()) {
					int j = last.peek();
					if (arr[idx] < j) {
						ans[idx] = j;
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
// 2021-01-09 04:47 해결
// 얼떨결에 해결
// 아이디어는 다음과 같다

// 배열을 모두 스택에 삽입
// 하나씩 pop 시키면서 오큰수를 비교해간다
// ex)
// 20
// 5 4 6 9 8 41 3  2 1 5 4 7 5 52 4  5 5 4 5 45 
//   5 4 6 9 8  41 3 2 1 5 4 7 5  52 4 5 5 4 5 

// 45를 pop -> 5와 비교했을떄 더 크므로 5의 오큰수는 45 및 이전 오큰수 목록(스택)에 45 삽입
// 5를 pop -> 4와 비교했을때 더 크므로 4의 오큰수는 5 및 이전 오큰수 목록에 5 삽입
// 4를 pop -> 5와 비교했을때 크지 않으므로 이전 오큰수 목록과 비교
//			=> 이전 오큰수 목록 중 가장 최근인 5와 비교했을떄도 크지 않으므로  이전 목록에서 5를 pop
//			=> 이전 다음 목록은 45, 비교했을때 더 크므로 5의 오큰수는 45
// 5를 pop -> 5와 비교했을때 크지 않으므로, 이전목록과 비교, 5의 오큰수는 45
// 5를 pop -> 4와 비교했을때 더 크므로, 4의 오큰수는 5 및 이전 오큰수 목록에 5 삽입
// 4를 pop -> 52와 비교했을때 크지 않으므로, 이전 목록과 비교
//			=> 45까지 비교했을때 크지 않으며, 이전 목록은 비어있으므로 52의 오큰수는 없다. -1
// 52를 pop -> 5와 비교했을때 더 크므로 5의 오큰수는 52 및 이전 오큰수 목록에 52 삽입
// 5를 pop -> 7과 비교했을떄 크지 않으므로, 이전 목록과 비교, 7의 오큰수는 52
// .. 의 과정을 거쳐서 나온 오큰수는
// 6 6 9 41 41 52 5 5 5 7 7 52 52 -1 5 45 45 5 45 -1 이다.
// 참고로 최우측은 항상 -1임. 오른쪽이 없으므로
