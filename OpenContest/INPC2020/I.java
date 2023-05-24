import java.util.*;
import java.io.*;
public class I {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int t = Integer.parseInt(br.readLine());
		while (t-- > 0) {
			String[] s = br.readLine().split(" ");
			int h = Integer.parseInt(s[0]), w = Integer.parseInt(s[1]),
				o = Integer.parseInt(s[2]), f = Integer.parseInt(s[3]),
				sx = Integer.parseInt(s[4]), sy = Integer.parseInt(s[5]),
				ax = Integer.parseInt(s[6]), ay = Integer.parseInt(s[7]);
			int[][] map = new int[h][w];
			for (int i = 0; i < o; i++) {
				s = br.readLine().split(" ");
				int x = Integer.parseInt(s[0]), y = Integer.parseInt(s[1]),
					l = Integer.parseInt(s[2]);
				
				map[y - 1][x - 1] = l;
			}
			
			dfs(0);
		}
		

		System.out.println(sb);
	}
	public static void dfs(int depth) {
		// basic
		// recursive
	}
}