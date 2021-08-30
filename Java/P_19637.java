import java.util.*;
import java.io.*;
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		String[] input = br.readLine().split(" ");
		int n = Integer.parseInt(input[0]), m = Integer.parseInt(input[1]);
		String[] title = new String[n];
		int[] value = new int[n];
		int idx = 0;
		for (int i = 0; i < n; i++) {
			input = br.readLine().split(" ");
			int j = Integer.parseInt(input[1]);
			if (Arrays.binarySearch(value, j) > 0) {
				continue;
			}
				
			else {
				title[idx] = input[0];
				value[idx++] = j;
			}
		}
		
		for (int i = 0; i < m; i++) {
			int power = Integer.parseInt(br.readLine());
			bw.write(title[getIndex(value, power)] + "\n");
		}
		bw.flush();
	}
	
	public static int getIndex(int[] al, int value) {
		int start = 0, end = al.length - 1;
		while (start < end) {
			int mid = start + (end - start) / 2;
			if (value <= al[mid]) end = mid;
			else start = mid + 1;
		}
		
		return start;
	}
}
// 2020-09-08 00:44 해결
// 이 문제는 부모중 한명이 없습니다.
// 일단 로직상 틀린건 없었는데 자료구조를 ArrayList 썻다고 이러기야?
// 그거때문에 엄한대만 고치고 있었잖아
// 일단 이분탐색으로 하한선 탐색해서 하한선의 index의 문자열을 출력시키는 문제였음
