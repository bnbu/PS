package D0420;

import java.util.*;
import java.io.*;
public class lowerBound {
	private static int lowerBound(List<Integer> data, int target) {
		int begin = 0;
		int end = data.size();
		while(begin < end) {
			int mid = (begin + end) / 2;
			if(data.get(mid) >= target) end = mid;
			else begin = mid + 1;
		}
		return end;
	}
	private static int upperBound(List<Integer> data, int target) {
		int begin = 0;
		int end = data.size();
		while(begin < end) {
			int mid = (begin + end) / 2;
			if(data.get(mid) <= target) begin = mid + 1;
			else end = mid;
		}
		return end;
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
