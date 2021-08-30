import java.util.*;
import java.io.*;
public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int t = Integer.parseInt(br.readLine());
		PriorityQueue<Integer> maxpq = new PriorityQueue<>(Collections.reverseOrder());
		PriorityQueue<Integer> minpq = new PriorityQueue<>();
		int mid = 0;
		for (int i = 0; i < t; i++) {
			int n = Integer.parseInt(br.readLine());
			if (i == 0)
				mid = n;
			else {
				if (n < mid)
					maxpq.add(n);
				else
					minpq.add(n);
				
				if (maxpq.size() >= minpq.size() + 1) {
					minpq.add(mid);
					mid = maxpq.poll();
				}
				else if (maxpq.size() + 2 <= minpq.size()) {
					maxpq.add(mid);
					mid = minpq.poll();
				}
			}
			bw.write(mid + "\n");
		}
		bw.flush();
	}
}
// 2020-10-05 00:23 해결
// 최소힙 최대힙 절댓값힙 으로 내부 구성을 공부한 후
// 라이브러리로 만들어져있는 우선순위큐를 사용
// 내부의 구성이 이진트리로 구성되어 있다는 점을 생각시, 시간 복잡도가 NlogN이라 괜찮
// 사용 한 방법은
// mid값을 기준으로 크면 상대적 우측인 최소힙에 저장
// mid값 기준으로 작으면 상대적 좌측인 최대힙에 저장
// 저장시킨 후 양측의 사이즈를 비교하여 mid값 재설정
