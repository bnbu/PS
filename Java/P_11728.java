import java.util.*;
import java.io.*;
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		String[] input = br.readLine().split(" ");
		int n = Integer.parseInt(input[0]), m = Integer.parseInt(input[1]);
		int[] A = new int[n], B = new int[m];
		input = br.readLine().split(" ");
		for (int i = 0; i < n; i++)
			A[i] = Integer.parseInt(input[i]);
		input = br.readLine().split(" ");
		for (int i = 0; i < m; i++)
			B[i] = Integer.parseInt(input[i]);
		
		int[] merged = new int[n + m];
		int aIdx = 0, bIdx = 0, mIdx = 0;
		while (aIdx + bIdx < m + n) {
			if (aIdx < n && bIdx < m) {
				if (A[aIdx] < B[bIdx]) 
					merged[mIdx++] = A[aIdx++];
				else 
					merged[mIdx++] = B[bIdx++];
			}
			else if (aIdx == n) {
				while (bIdx < m)
					merged[mIdx++] = B[bIdx++];
			}
			else if (bIdx == m) {
				while (aIdx < n)
					merged[mIdx++] = A[aIdx++];
			}
		}
		for (int i : merged)
			bw.write(i + " ");
		bw.write("\n");
		bw.flush();
	}
}
// 2020-09-17 해결
// 병합 정렬에서 병합만 쓰는거 ㅋㅋ
