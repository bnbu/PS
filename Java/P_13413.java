import java.io.*;
public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int t = Integer.parseInt(br.readLine());
		for (int i = 0; i < t; i++) {
			int n = Integer.parseInt(br.readLine());
			char[][] othello = new char[2][n];
			int[] b = new int[2];
			for (int j = 0; j < othello.length; j++) {
				String input = br.readLine();
				for (int k = 0; k < othello[j].length; k++) {
					othello[j][k] = input.charAt(k);
					if (othello[j][k] == 'B')
						b[j]++;
				}
			}		

			int dif = b[0] < b[1] ? b[1] - b[0] : b[0] - b[1];
			int cnt = 0;
			for (int j = 0; j < n; j++)
				if (othello[0][j] != othello[1][j])
					cnt++;
			
			bw.write((dif + (cnt - dif) / 2) + "\n");
		}
		bw.flush();
	}
}
// 2020-09-03 02:33 해결
// 서로 다른 돌의 색 갯수 + (위치가 다른 돌의 수 - 서로 다른 돌의 색 갯수) / 2 => 총 동작 횟수
