import java.util.*;
import java.io.*;
public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int n = Integer.parseInt(br.readLine());
		int[] arr = new int[n];
		int[] temp = new int[n];
		String[] s = br.readLine().split(" ");
		for (int i = 0; i < n; i++) {
			temp[i] = arr[i] = Integer.parseInt(s[i]); 
		}
		
		Arrays.sort(temp);
		HashMap<Integer, Integer> map = new HashMap<>();
		map.put(temp[0], 0);
		int cnt = 0;
		for (int i = 1; i < n; i++) {
			if (temp[i] == temp[i - 1])
				continue;
			else if (temp[i] > temp[i - 1]) {
				map.put(temp[i], ++cnt);
			}
		}
		
		for (int i = 0; i < n; i++)
			sb.append(map.get(arr[i]) + " ");
		System.out.println(sb);
	}
}
// 2020-09-26 해결
// 해쉬 맵을 사용하여 정렬된 수열로부터 압축시킨 좌표값 (0부터 한 그것)과 기존 좌표값을 매칭.
// 이후 기존 수열로 매칭시킨 좌표값을 되찾아가며 출력.
