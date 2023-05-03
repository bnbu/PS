import java.io.*;
public class PriorityQueue_based_Heap {
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
		last = 0;
	}
	public void add(int n) {
		arr[++last] = n;
		
		for (int i = last; i > 1; i /= 2) {
			if (arr[i / 2] < arr[i]) {
				int temp = arr[i / 2];
				arr[i / 2] = arr[i];
				arr[i] = temp;
			}
			else
				break;
		}
	}
	public int poll() {
		if (last == 0) {
			return 0;
		}
		else {
			int ret = arr[1];
			arr[1] = arr[last];
			arr[last] = 0;
			last--;
			
			int idx = 1;
			while (2 * idx <= last) {
				if (arr[2 * idx] < arr[idx] && arr[2 * idx + 1] < arr[idx])
					break;
				else if (arr[2 * idx] > arr[2 * idx + 1]) {
					int temp = arr[2 * idx];
					arr[2 * idx] = arr[idx];
					arr[idx] = temp;
					idx *= 2;
				}
				else {
					int temp = arr[2 * idx + 1];
					arr[2 * idx + 1] = arr[idx];
					arr[idx] = temp;
					idx *= 2;
					idx += 1;
				}
			}
			return ret;
		}
	}
}
// 완전 이진트리 기반 힙으로 구현.
// 삽입시, 마지막 위치의 다음 노드위치에 값을 삽입 -> up-heap 과정
// 제거시, 최상단(root)값을 제거, 마지막 위치의 노드를 root로 옮김 -> down-heap 과정
