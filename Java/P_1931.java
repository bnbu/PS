import java.util.*;
import java.io.*;
public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int n = Integer.parseInt(br.readLine());
		Pair[] time = new Pair[n];
		for (int i = 0; i < n; i++) {
			String[] input = br.readLine().split(" ");
			time[i] = new Pair(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
		}
		
		Arrays.sort(time, new Comparator<Pair>() {
			public int compare(Pair a, Pair b) {
				if (a.end > b.end)
					return 1;
				else if (a.end == b.end) {
					if (a.start > b.start)
						return 1;
				}
				return -1;
			}
		});
		Pair last = time[0];
		int cnt = 1;
		for (int i = 1; i < n; i++) {
			if (time[i].start < last.end)
				continue;
			
			last = time[i];
			cnt++;
		}
		
		System.out.println(cnt);
	}
}
class Pair{
	int start;
	int end;
	public Pair(int x, int y) {
		start = x;
		end = y;
	}
}
// 2020-09-24 00:51 해결
// 그리디, 가장 많은 회의를 하려면?
// 회의시간이 빠르게 끝나는 순서대로 많이 고르면 된다.
// 이때, 회의시간이 같은 시간에 끝이 난다면, 시작시간이 더 빠른거를 먼저 고르는게 이득임.
