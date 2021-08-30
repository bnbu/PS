import java.util.*;
import java.io.*;
public class Main {
	public static int[] parent;
	public static int[] length;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int t = Integer.parseInt(br.readLine());
		while (t-- > 0) {
			int n = Integer.parseInt(br.readLine());
			parent = new int[n + 1];
			length = new int[n + 1];
			for (int i = 1; i <= n; i++)
				parent[i] = i;
			while (true) {
				st = new StringTokenizer(br.readLine());
				String command = st.nextToken();
				if (command.equals("E")) {
					int i = Integer.parseInt(st.nextToken());
					
					find(i);
					sb.append(length[i] + "\n");
				}
				else if (command.equals("I")) {
					int i = Integer.parseInt(st.nextToken()),
						j = Integer.parseInt(st.nextToken());
					
					addEdge(i, j);
				}
				else if (command.equals("O"))
					break;
			}
		}
		System.out.println(sb);
	}
	public static void addEdge(int a, int b) {
		length[a] = Math.abs(a - b) % 1000;
		parent[a] = b;
	}
	public static int find(int a) {
		if (a == parent[a])
			return a;
		else {
			int ret = find(parent[a]);
			length[a] += length[parent[a]];
			return parent[a] = ret;
		}
	}
}
// 2021-01-02 03:55 복습할 것
// 기본적인 틀은 유니온-파인드
// 하지만, 유니온 작업을 바로 진행하는게 아닌,
// 우선적으로 두 정점을 잇는다 (a의 부모를 b로 지정 -> 이래야 부모 == 센터(제일 마지막으로 연결) 이 된다)
// a의 부모를 b로 지정했다면, length[a]의 정의는 다음과 같다.
// length[a] : a의 부모 까지의 거리
// 따라서 현재까지의 length[a]는 b까지의 거리이다.

// 이제 여기에 b-c를 연결했다고 하자
// 마찬가지의 작업으로 b의 부모는 c, length[b]에는 b에서 c까지의 거리이다.
// 그리고 현재의 센터는 c

// 여기서 E a 명령을 입력시, 여기서 실제 union과정이 이루어진다고 보면 된다.
// 정확히는 find를 하면서 union을 동시 진행하여 경로압축을 하는 셈
// find(a)는
// a와 parent[a]가 같다면, (즉 a가 최상위 개체) a를 반환하고 종료
// a와 parent[a]가 다르다면, 경로의 길이를 수정하며 동시 최상위 개체로의 탐색을 진행한다
// -> 우선, a의 최상위 개체를 찾기위해 find(parent[a]) 즉 a의 부모의 부모를 다시 탐색
// 이러면 find(b)가 되고, find(b)는 다시 find(c)가 최상위 개체는 c임을 알아온다.
// 기존 length[a] (현재상으로는 a-b까지의  거리)에 length[parent[a]]를 더한다.
// 지금 상황에서는 parent[a] = b 이므로, length[b], 즉 b-c까지의 거리가 더해진다
// 이로써 length[a] 는 a-b-c, 즉 a-c의 거리가 된다.
// 그리고 이전에 알아온 최상위 개체로 a의 부모를 바꾸면 경로압축.
// 즉 union 작업이 진행된다.

// ** 포인트는 union을 바로 진행하지 않고, 경로계산을 위하여 두 경로를 이어놓기만 한다.
