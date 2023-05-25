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
	static int[] arr, ans;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		sqrtN = (int)Math.sqrt(n);
		arr = new int[n + 1];
		ans = new int[m];
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) arr[i] = Integer.parseInt(st.nextToken());
		
		ArrayList<Query> q = new ArrayList<>();
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			q.add(new Query(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), i));
		}
		
		q.sort((q1, q2) -> {
			if (q1.left / sqrtN == q2.left / sqrtN)
				return Integer.compare(q1.right, q2.right);
			return Integer.compare(q1.left / sqrtN, q2.left / sqrtN);
		});
		
		int sum = 0,
			start = q.get(0).left,
			end = q.get(0).right;
		for (int i = start; i <= end; i++)
			sum += arr[i];
		ans[q.get(0).idx] = sum;
		
		for (int i = 1; i < m; i++) {
			Query curr = q.get(i);
			int left = curr.left,
				right = curr.right;
      
      // 구간의 시작을 좌측으로 넓힘
			while (start > left) sum += arr[--start];
      // 구간의 시작을 우측으로 좁힘
			while (start < left) sum -= arr[start++];
      // 구간의 끝을 좌측으로 좁힘
			while (end > right) sum -= arr[end--];
      // 구간의 끝을 우측으로 넓힘
			while (end < right) sum += arr[++end];
			ans[curr.idx] = sum;
		}
		for (int i : ans) sb.append(i).append("\n");
		System.out.print(sb);
	}
}

// mo's 알고리즘
// 실제로 평방 분할로 세그먼트 트리처럼 구간단위의 값을 저장하는게 아닌
// 쿼리의 구간을 평방 분할하여 재사용하기 유리한 순서로 정렬하여 진행하는 
// 일종의 오프라인 쿼리 기법 응용
// 단, 전제조건은 값의 수정이 발생하지 않아야 함

// 이전 결과의 일부(혹은 전체)를 다음 쿼리에 재활용을 최대한 한다.
// 이전 쿼리의 범위를 (s1, e1), 다음 쿼리의 범위를 (s2, e2)
// 이전 쿼리를 재활용하여 다음 쿼리의 결과를 계산하는 시간은
// O(|s2 - s1| + |e2 - e1|) 이 된다.
// 이 시간을 최소화 시킬 수 있도록
// 모든 쿼리의 구간 (s, e)에 대해서
// s/sqrt(n) 의 오름차순, 같은 경우 e의 오름차순으로 정렬을 하게 되면 저 시간을 최소화할 수 있다.

// 정렬한 이후 최초 1회 쿼리는 모두 계산한 후
// 이후의 쿼리부터는 이전의 쿼리 결과로부터 현재의 구간을 의미하는 (start, end)를
// 지금 쿼리의 구간을 의미하는 (left, right)를 통해 범위를 적절히 수정해 나간다

// 구간을 루트값을 기준으로 나눠서
// s/sqrtN 값이 일치한다는 것은, 아무리 많아봐야 sqrtN번 (각 평방분할 구간의 시작과 끝 지점)
// e값은 sqrtN 단위로 묶여있진 않으나, 단조증가 하게 되므로 많아봐야 O(N)번 변화하므로 O(N*sqrtN)
// s/sqrtN 값이 다르면, 위의 e처럼 많아봐야 O(N)번 변화하지만
// 이미 sqrtN단위로 분할했기 때문에, 이러한 경우는 최대 O(N*sqrtN)번 발생하므로
// 종합적으로는 O(N*sqrtN)이라 볼 수 있다.

// 길이 N, 쿼리수 M에 대홰
// 쿼리 정렬 O(MlogM)
// 쿼리 처리 O(M + T(N)*N*sqrtN)
// 이떄 T(N)=구간을 한칸 조정하는 시간이 된다.

