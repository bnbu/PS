import java.util.*;
import java.io.*;
public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int t = Integer.parseInt(br.readLine());
		PriorityQueue pq = new PriorityQueue();
		while (t-- > 0) {
			int n = Integer.parseInt(br.readLine());
			if (n == 0) 
				bw.write(pq.poll() + "\n");
			else 
				pq.add(n);
		}
		bw.flush();
	}
}
class PriorityQueue {
	private int[] arr;
	private int last;
	public PriorityQueue() {
		arr = new int[100001];
		Arrays.fill(arr, Integer.MAX_VALUE);
		last = 0;
	}
	public void add(int n) {
		arr[++last] = n;
		for (int i = last; i > 1; i /= 2) {
			if (arr[i] < arr[i / 2])  {
				int temp = arr[i];
				arr[i] = arr[i / 2];
				arr[i / 2] = temp;
			}
			else 
				break;
		}
	}
	public int poll() {
		if (last == 0)
			return 0;
		else {
			int ret = arr[1];
			arr[1] = arr[last];
			arr[last] = Integer.MAX_VALUE;
			last--;

			int idx = 1;
			while (idx * 2 <= last) {
				if (arr[idx] < arr[idx * 2] && arr[idx] < arr[idx * 2 + 1])
					break;
				else if (arr[idx * 2] < arr[idx * 2 + 1]) {
					int temp = arr[idx];
					arr[idx] = arr[idx * 2];
					arr[idx * 2] = temp;
					idx *= 2;
				}
				else {
					int temp = arr[idx];
					arr[idx] = arr[idx * 2 + 1];
					arr[idx * 2 + 1] = temp; 
					idx *= 2;
					idx++;
				}
			}
			return ret;
		}
	}
}
// 2020-10-04 04:50 해결
// => 우선순위 큐에서 이거는 최대힙과 반대로 최소힙이기 때문에, 초기값이 Integer의 맥스값.
