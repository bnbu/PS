import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int[] time = new int[2];
		int[] in = new int[n], out = new int[n];
		for (int i = 0; i < n; i++) {
			in[i] = scan.nextInt();
			out[i] = scan.nextInt();
		}
		time[1] = Integer.MAX_VALUE;
		for (int i = 0; i < n; i++) {
			time[0] = time[0] < in[i] ? in[i] : time[0];
			time[1] = time[1] < out[i] ? time[1] : out[i];
		}
		// [0] => 시작하는 거중 가장 큰거
		// [1] => 끝나는 거중 가장 작은거
		System.out.println(time[0] < time[1] ? 0 : time[0] - time[1]);
	}
}
// 2020-09-04 19:30 해결
// 알아낸 규칙을 통해 보자면, 학교에 들어온 시간 중 가장 나중에 들어온 시간과
// 학교를 나가는 시간중 가장 일찍 나가는 시간
// 이 둘을 비교했을때 나가는 시간이 더 크다면 반드시 모두가 겹치는 시간대가 존재한다.
// 반대라면, 겹치지 않는 시간대가 존재하므로 그 시간동안 학교에 있으면 된다.
