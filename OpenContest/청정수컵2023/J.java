package test;
import java.util.*;
import java.io.*;
public class J {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine());
		//8000 * 12 = 96002
		
		int[] imos = new int[96002];
		for (int i = 0; i < n; i++) {
			String[] str = br.readLine().split(" ");
			st = new StringTokenizer(str[0], "-");
			int year = Integer.parseInt(st.nextToken()),
				month = Integer.parseInt(st.nextToken());
			imos[(year-2000)*12 + month]++;
			
			st = new StringTokenizer(str[1], "-");
			year = Integer.parseInt(st.nextToken());
			month = Integer.parseInt(st.nextToken());
			imos[(year-2000)*12 + month + 1]--;
		}
		
		int[] arr = new int[96002];
		int max = 0, idx = 0;
		for (int i = 1; i < imos.length; i++) {
			arr[i] = arr[i - 1] + imos[i];
			if (arr[i] > max) {
				max = arr[i];
				idx = i;
			}
		}
		
		int year = idx / 12,
			month = idx % 12;
		
		if (month == 0) {
			year--;
			month += 12;
		}
		System.out.printf("%d-%02d", 2000 + year, month);
	}
}
// 2000-01 ~ 9999-12 까지를 배열로 변경해서
// 96000개의 배열을 만든다.

// 단 imos법을 적용해야하므로 앞 뒤 유령배열을 추가해서 96002개의 배열을 생성

// 입대일 ~ 전역일을 각각 배열의 인덱스로 적절히 변환시켜서
// imos법을 진행한 후
// 구간합배열을 완성시킨 다음 최대값을 갖는 곳의 가장 첫 인덱스를
// 다시 역으로 날짜로 변환시키면 된다.