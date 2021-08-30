import java.util.*;
import java.io.*;	
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int[] x, y;
		x = new int[3];
		y = new int[3];
		
		for (int i = 0; i < 3; i++) {
			st = new StringTokenizer(br.readLine());
			x[i] = Integer.parseInt(st.nextToken());
			y[i] = Integer.parseInt(st.nextToken());
		}
		
		int i = ccw(x, y);
		if (i < 0)
			System.out.println(-1);
		else if (i > 0)
			System.out.println(1);
		else
			System.out.println(0);
	}
	public static int ccw(int[] x, int[] y) {
		return (x[0]*y[1] + x[1]*y[2] + x[2]*y[0])
				- (y[0]*x[1] + y[1]*x[2] + y[2]*x[0]);
	}
}
// 2021-01-15 23:07
// ccw
