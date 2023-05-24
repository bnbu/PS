import java.io.*;
import java.util.Arrays;
public class G {
	public static int[] arr;
	public static int[] answer;
	public static int cnt = 0;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String[] s = br.readLine().split(" ");
		int x1 = Integer.parseInt(s[0]),
			y1 = Integer.parseInt(s[1]),
			z1 = Integer.parseInt(s[2]),
			x2 = Integer.parseInt(s[3]),
			y2 = Integer.parseInt(s[4]),
			z2 = Integer.parseInt(s[5]);
		
		int n = Integer.parseInt(br.readLine());
		int[] stick = new int[n];
		int total = 0;
		s = br.readLine().split(" ");
		for (int i = 0; i < n; i++) {
			stick[i] = Integer.parseInt(s[i]);
			total += stick[i];
		}
		Arrays.sort(stick);
		
		double distance = 
				Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1,  2));
		
		if (distance <= total) {
			int sub = 0;
			int height = 0;
			int status = 1;
			boolean chk = true;
			int idx = 0;
			for (int i = 0; i < stick.length; i++) {
				if (sub + stick[i] < distance) {
					sub += stick[i];
				} else if (sub + stick[i] >= distance && chk) {
					chk = false;
					idx = i;
				}
			}
		}
		else
			sb.append("NO");
		
		System.out.println(sb);
		System.out.println(distance);
	}
}