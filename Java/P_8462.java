import java.util.*;
import java.io.*;
class Query {
	int left, right, idx;
	public Query(int left, int right, int idx) {
		this.left = left;
		this.right = right;
		this.idx = idx;
	}
}
public class Main {
	static int n, sqrtN;
	static int[] arr;
	static long[] cnt, ans;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		int t = Integer.parseInt(st.nextToken());
		
		sqrtN = (int)Math.sqrt(n);
		arr = new int[n + 1];
		cnt = new long[1000001];
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) arr[i] = Integer.parseInt(st.nextToken());
		
		ArrayList<Query> query = new ArrayList<>();	
		ans = new long[t];
		for (int i = 0; i < t; i++) {
			st = new StringTokenizer(br.readLine());
			query.add(new Query(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), i));
		}
		
		query.sort((q1, q2) -> {
			if (q1.left / sqrtN == q2.left / sqrtN)
				return Integer.compare(q1.right, q2.right);
			return Integer.compare(q1.left / sqrtN, q2.left / sqrtN);
		});
		
		Query curr = query.get(0);
		int start = curr.left,
			end = curr.right;
		long sum = 0;
		for (int i = start; i <= end; i++) 
			sum += (2*cnt[arr[i]]++ + 1) * arr[i];
		ans[curr.idx] = sum;
		
		for (int i = 1; i < t; i++) {
			curr = query.get(i);
			int left = curr.left,
				right = curr.right;
		
			while (start < left) {
				sum -= (2L*--cnt[arr[start]] + 1) * arr[start]; 
				start++;
			}
			while (start > left) {
				start--;
				sum += (2L*cnt[arr[start]]++ + 1) * arr[start];
			}
			while (end < right) {
				end++;
				sum += (2L*cnt[arr[end]]++ + 1) * arr[end];
			}
			while (end > right) {
				sum -= (2L*--cnt[arr[end]] + 1) * arr[end];
				end--;
			}
			ans[curr.idx]= sum;
		}
		
		for (long i : ans) sb.append(i).append("\n");
		System.out.print(sb);;
	}
}

//mo's 알고리즘을 돌리는데
//k^2 + s 값을 계산해야한다
//이때 0 1 4 9 16 25 .. 꼴로 제곱이 되는데
//그 인접한 두 사이의 차이는 1 3 5 7 9 11.. 로 홀수로 2씩 증가하는 값이 된다
//이렇게 현재 cnt 값에 따른 홀수값을 구해서 넓히거나 좁히는 구간에 따라 값을 더하거나 빼면 된다
// 답이 long 범위가 될건데, 곱하는 과정에서 int로 짤리지 않게만 주의
