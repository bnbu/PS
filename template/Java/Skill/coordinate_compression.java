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
	static int[] cnt, ans, arr;
	static int lowerBound(int[] arr, int target) {
		int begin = 0;
		int end = arr.length;
		while(begin < end) {
			int mid = (begin + end) / 2;
			if(arr[mid] >= target) end = mid;
			else begin = mid + 1;
		}
		return end;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		sqrtN = (int)Math.sqrt(n);
		
		arr = new int[n + 1];
		cnt = new int[n + 1];
		int idx = 0;

        // 여기부터
		st = new StringTokenizer(br.readLine());
		HashSet<Integer> hs = new HashSet<>(); 
		for (int i = 1; i <= n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			hs.add(arr[i]);
		}
		int[] temp = hs.stream().mapToInt(i -> i).toArray();
		Arrays.sort(temp);
		for (int i = 1; i <= n; i++) 
			arr[i] = lowerBound(temp, arr[i]);
			// arr[i] = Arrays.binearySearch(temp, arr[i])

        // 여기까지가 좌표압축 부분
        // 편하게 해쉬맵을 통한 좌표압축도 있지만
        // 충돌 등의 문제로 값이 많아질 경우 느려지는 문제가 생긴다
        // 중복 제거 및 오름차순 정렬 -> 원본 배열에서 임시배열에 각각 해당 값으로 lower bound를 찾아서 변경

		// 2023-06-22 : Arrays의 binarySearch 메서드는 반환값이, 값이 존재한다면 존재하는 곳의 위치(양수)를 반환
		// 없다면 존재했었더라면 있어야 했을 위치(음수)를 반환
		// 어차피 좌표압축은 존재하는 값만 찾게 되므로, 굳이 lower bound 함수를 따로 만들지 말고, binarySearch로 대용해도 된다.
		
		int m = Integer.parseInt(br.readLine());
		ans = new int[m];
		ArrayList<Query> query = new ArrayList<>();
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			query.add(new Query(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), i));
		}
		query.sort((q1, q2) -> {
			if (q1.left / sqrtN == q2.left / sqrtN)
				return Integer.compare(q1.right, q2.right);
			return Integer.compare(q1.left / sqrtN, q2.left / sqrtN);
		});
		
		Query currQuery = query.get(0);
		int start = currQuery.left,
			end = currQuery.right,
			currValue = 0;
		for (int i = start; i <= end; i++) {
			int curr = arr[i];
			currValue += cnt[curr]++ == 0 ? 1 : 0;
		}
		ans[currQuery.idx] = currValue;
		
		for (int i = 1; i < m; i++) {
			currQuery = query.get(i);
			int left = currQuery.left,
				right = currQuery.right;
			while (start < left) {
				int curr = arr[start];
				currValue -= --cnt[curr] == 0 ? 1 : 0;
				start++;
			}
			while (start > left) {
				start--;
				int curr = arr[start];
				currValue += cnt[curr]++ == 0 ? 1 : 0; 
			}
			while (end < right) {
				end++;
				int curr = arr[end];
				currValue += cnt[curr]++ == 0 ? 1 : 0;
			}
			while (end > right) {
				int curr = arr[end];
				currValue -= --cnt[curr] == 0 ? 1 : 0;
				end--;
			}
			ans[currQuery.idx] = currValue;
		}
		
		for (int i : ans) sb.append(i).append("\n");
		System.out.print(sb);
	}
}
// 문제 자체는 14897번
// mo's 를 사용하여 푸는 문제인데
// 값이 너무 많아서 좌표압축을 사용해야 하는데
// 해쉬맵으로 했더니 시간초과 뜸