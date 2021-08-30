import java.util.*;
import java.io.*;
public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		int t = Integer.parseInt(br.readLine());
		while (t-- > 0)
			pq.add(Integer.parseInt(br.readLine()));
		
		int result = 0;
		if (pq.size() > 1)
			while (pq.size() > 1) {
				while (!pq.isEmpty()) {
					int temp = pq.poll();
					if (!pq.isEmpty()) {
						temp += pq.poll();
						pq.add(temp);
						result += temp;
					}
				} 
			}
		System.out.println(result);
	}
}
// 2020-10-12 22:00 해결
// 2개씩 합치는데, 그거를 가장 작은거부터 해야함
// => 즉 우선순위큐에 합친거 다시 넣고 다시 2개 뺴고를 반복.
