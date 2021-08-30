import java.io.*;
public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int n = Integer.parseInt(br.readLine());
		int reward = 0, cnt = 0, max = 0;
		int passP = 0, passX = 0, passR = 0;
		boolean pass = false, cng = false;
		for (int i = 0; i < n; i++) {
			String[] input = br.readLine().split(" ");
			int x = Integer.parseInt(input[0]), p = Integer.parseInt(input[1]);
			
			if (reward <= x) {
				cnt++;
				reward += p;
				if (!pass)
					max = Math.max(max, p); // => 처음으로 패스한 대회 이전 상금 중 최대값을 구함
			}
			else {
				if (!pass) {
					pass = true;
					passP = p;
					passX = x;
					passR = reward;
				}
				else {
					if (!cng) {
						if (passR - max > passX)
							break;
						
						reward -= max;
						if (reward <= x) {
							reward += p + passP;
							cnt++;
							cng = true;
						}
					}
					else 
						break;
				}
			}
		}
		if (cnt >= n - 1)
			bw.write("Kkeo-eok\n");
		else
			bw.write("Zzz\n");
		bw.flush();
	}
}
// 2020-09-23 02:20 해결
// => 처음으로 패스한 대회 이전까지 중의 최대값이였음을 잘 생각해야함.
