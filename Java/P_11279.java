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
// 2020-10-04 해결
// => 힙 구조를 완전 이진트리로 구현 하는 문제.
// 포인트는 배열로 구현시, 편의를 위해 index 1부터 시작
// 부모 인덱스 i를 기준
// 자식 인덱스는 2*i 와 2*i + 1이고, 
// 반대로 부모 인덱스는  자식 인덱스 i 기준, i / 2이다.
// 이 점을 염두해두고 삽입/삭제 마다 크기를 비교하여 삽입한 값의 위치를 맞춰 저장시킨다
// ** 항상 최상위 노드에는 제일 큰 값(우선순위도 높은 값) 이 위치해야 함.
