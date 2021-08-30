import java.io.*;
import java.util.Arrays;
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
			if (Math.abs(arr[i]) < Math.abs(arr[i / 2])) {
				int temp = arr[i];
				arr[i] = arr[i / 2];
				arr[i / 2] = temp;
			}
			else if (Math.abs(arr[i]) == Math.abs(arr[i / 2])) {
				if (arr[i] < arr[i / 2]) {
					int temp = arr[i];
					arr[i] = arr[i / 2];
					arr[i / 2] = temp;
				}
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
			arr[last--] = Integer.MAX_VALUE;
			
			int idx = 1;
			while (idx * 2 <= last) {
				if (Math.abs(arr[idx]) < Math.abs(arr[idx * 2]) &&
						Math.abs(arr[idx]) < Math.abs(arr[idx * 2 + 1]))
					break;
				
				if (arr[idx * 2 + 1] == Integer.MAX_VALUE) {
					if (Math.abs(arr[idx]) == Math.abs(arr[idx * 2])) {
						if (arr[idx] > arr[idx * 2]) {
							int temp = arr[idx];
							arr[idx] = arr[idx * 2];
							arr[idx * 2] = temp;
							idx *= 2;
						}
						else
							break;
					}
					else {
						int temp = arr[idx];
						arr[idx] = arr[idx * 2];
						arr[idx * 2] = temp;
						idx *= 2;
					}
				}
				else {
					if (Math.abs(arr[idx * 2]) < Math.abs(arr[idx * 2 + 1])) {
						int temp = arr[idx];
						arr[idx] = arr[idx * 2];
						arr[idx * 2] = temp;
						idx *= 2;
					}
					else if (Math.abs(arr[idx * 2]) > Math.abs(arr[idx * 2 + 1])) {
						int temp = arr[idx];
						arr[idx] = arr[idx * 2 + 1];
						arr[idx * 2 + 1] = temp;
						idx = idx * 2 + 1;
					}
					else {
						if (arr[idx * 2] < arr[idx * 2 + 1]) {
							int temp = arr[idx];
							arr[idx] = arr[idx * 2];
							arr[idx * 2] = temp;
							idx *= 2;
						}
						else {
							int temp = arr[idx];
							arr[idx] = arr[idx * 2 + 1];
							arr[idx * 2 + 1] = temp;
							idx = idx * 2 + 1;
						}
					}
				}
			}
			return ret;
		}
	}
}
// 2020-10-04 21:37 해결
// 이 참 절대값으로 하려니 고려할게 많았음
