import java.util.*;
import java.io.*;
public class H {
	 static int lowerBoundSearch(long[] arr, long k) {
	    	int s = 0,
	    		e = arr.length - 1;
	        while (s < e) {
	            int m = (s + e) / 2;
	            if (arr[m] < k) s = m + 1;
	            else e = m;
	        }
	        return s;
	    }
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		long w = Long.parseLong(st.nextToken()),
			h = Long.parseLong(st.nextToken()),
			k = Long.parseLong(st.nextToken());
			
		int n = Integer.parseInt(br.readLine());
		long[] y = new long[n + 1];
		long last = 0;
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) {
			long curr = Long.parseLong(st.nextToken());
			y[i] = curr - last;
			last = curr;
		}
		y[n] = h - last;
		Arrays.sort(y);
		
		int m = Integer.parseInt(br.readLine());
		long[] x = new long[m + 1];
		last = 0;
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < m; i++) {
			long curr = Long.parseLong(st.nextToken());
			x[i] = curr - last;
			last = curr;
		}
		x[m] = w - last;
		
		long ans = 0;
		for (long width : x) {
			int a = lowerBoundSearch(y, k / width);
			if (y[a] <= k / width) ans += a + 1;
			else ans += a;
		}
		
		System.out.println(ans);
	}
}
// 세로축의 좌표점으로부터 세로의 각 구간 길이를 정렬시키고
// 마찬가지로 가로축의 좌표점으로부터 가로의 각 구간 길이를 구한다 (여기는 정렬시킬 필요 없다)

// 이후는 가로로 스위핑을 하면서
// 이전에 구한 길이는 정렬되어져 있기 때문에, lower bound를 통해 너비가 k 이하가 되게 하는 높이의 상한선의 인덱스를 찾으면
// 그 이전까지의 모든 값들은 전부 가능한 높이값이 되므로, 각 x구간마다의 가능한 개수를 logn 시간으로
// 종합적으로 nlogn 구해올 것이라 생각했으나 어디선가 코드가 미스가 난 것인지 계속 틀려서 실패함
