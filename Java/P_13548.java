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
	static int[] arr, cnt, num, ans;
	static ArrayList<Query> query;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		sqrtN = (int)Math.sqrt(n);
		arr = new int[100001];
		cnt = new int[100001];
		num = new int[100001];
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) arr[i] = Integer.parseInt(st.nextToken());
		
		int m = Integer.parseInt(br.readLine());
		ans = new int[m];
		query = new ArrayList<>();
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			query.add(new Query(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), i));
		}
		query.sort((q1, q2) -> {
			if (q1.left / sqrtN == q2.left / sqrtN) return Integer.compare(q1.right, q2.right);
			return Integer.compare(q1.left / sqrtN, q2.left / sqrtN);
		});
		
		Query currQ = query.get(0);
		int start = currQ.left,
			end = currQ.right,
			currMax = 0;
		for (int i = start; i <= end; i++) {
			int curr = arr[i];
			num[cnt[curr]]--;
			cnt[curr]++;
			num[cnt[curr]]++;
			currMax = Math.max(currMax, cnt[curr]);
		}
		ans[currQ.idx] = currMax;
		
		for (int i = 1; i < m; i++) {
			currQ = query.get(i);
			int left = currQ.left,
				right = currQ.right;
			
			while (start < left) {
				int curr = arr[start];
				if (cnt[curr] > 0) {
					num[cnt[curr]]--;
					if (num[cnt[curr]] == 0 && cnt[curr] == currMax) currMax--;
				}
				cnt[curr]--;
				if (cnt[curr] > 0) {
					num[cnt[curr]]++;
				}
				start++;
			}
			while (start > left) {
				start--;
				int curr = arr[start];
				if (cnt[curr] > 0) num[cnt[curr]]--;
				cnt[curr]++;
				if (cnt[curr] > 0) num[cnt[curr]]++;
				currMax = Math.max(currMax, cnt[curr]);
			}
			while (end < right) {
				end++;
				int curr = arr[end];
				if (cnt[curr] > 0) num[cnt[curr]]--;
				cnt[curr]++;
				if (cnt[curr] > 0) num[cnt[curr]]++;
				currMax = Math.max(currMax, cnt[curr]);
			}
			while (end > right) {
				int curr = arr[end];
				if (cnt[curr] > 0) {
					num[cnt[curr]]--;
					if (num[cnt[curr]] == 0 && cnt[curr] == currMax) currMax--;
				}
				cnt[curr]--;
				if (cnt[curr] > 0) {
					num[cnt[curr]]++;
				}
				end--;
			}
			ans[currQ.idx] = currMax;
		}
		
		for (int i : ans) sb.append(i).append("\n");
		System.out.println(sb);
	}
}
//각 수가 등장한 카운트
//의 카운트를 둬서 이거를 통해 현재 가장 많이 등장한 카운트의 카운트로 최대를 추적한다
//어차피 구간이 한칸 변할때마다 최대값은 1씩밖에 안바뀜
//그거랑 별개로 java에서는 인덱스 초과가 계속 발생해서 감이 안잡혔다
//cnt가 음수로 내려가는 경우가 잦았나보다
