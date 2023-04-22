package D0420;

import java.util.*;
import java.io.*;
public class lowerBound {
    static int lowerBoundSearch(ArrayList<Integer> arr, int k) {
    	int s = 0,
    		e = arr.size() - 1;
        while (s < e) {
            int m = (s + e) / 2;
            if (arr.get(m) < k) s = m + 1;
            else e = m;
        }
        return s;
    }
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int n = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		ArrayList<Integer> arr = new ArrayList<>();
		for (int i = 0; i < n; i++) arr.add(Integer.parseInt(st.nextToken()));
		Collections.sort(arr);
		
		System.out.println(arr);
		System.out.println(lowerBoundSearch(arr, Integer.parseInt(br.readLine())));
		// 입력받은 값의 하한선 (이 값보다 작으면서도 가장 가까운 값)을 찾아준다.
	}
}
