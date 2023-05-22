import java.util.*;
import java.io.*;
public class Main {
	static long w, h, k;
	static ArrayList<Long> x, y;
	static int upperBoundSearch(ArrayList<Long> arr, long k) {
    	int s = 0,
    		e = arr.size() - 1;
    	while(s < e) {
        	int mid = (s + e) / 2;
            
            if(arr.get(mid) <= k) {
            	s = mid + 1;
            }
            else {
            	e = mid;
            }
        }
    	 return e;
    }
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		w = Long.parseLong(st.nextToken());
		h = Long.parseLong(st.nextToken());
		k = Long.parseLong(st.nextToken());
		
		int n = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		y = new ArrayList<>();
		long last = 0;
		for (int i = 0; i < n; i++) {
			long curr = Long.parseLong(st.nextToken());
			y.add(curr - last);
			last = curr;
		}
		y.add(h - last);
		y.add(0L);
		y.add(h);
		y.sort((l1, l2) -> Long.compare(l1, l2));
		
		int m = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		x = new ArrayList<>();
		last = 0;
		for (int i = 0; i < m; i++) {
			long curr = Long.parseLong(st.nextToken());
			x.add(curr - last);
			last = curr;
		}
		x.add(w - last);

		long ans = 0;
		for (long width : x) {
			long toFind = k / width;
			int idx = upperBoundSearch(y, toFind);
			ans += idx - 1;
		}
		System.out.println(ans);
	}
}
// lowerbound가 아니라 upperbound를 했어야 하기도 했다 (중복값이 있을 수 있으므로)
// 좌표가 최대 10만개씩 -> x, y의 각 구간은 100001개씩 나올 수 있다
// 100001^2 은 인트 범위 초과라 답 값을 long으로 했어야 했다 아니 이런
