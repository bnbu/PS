import java.util.*;
import java.io.*;
public class ConvexHull {
	public static class Pair implements Comparable<Pair> {
		int x, y, p, q;
		// (x, y) 좌표
		// 기준점에 대해 상대적인 x, y거리 (p, q)

		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
			p = 1;
			q = 0;
		}
		public int compareTo(Pair p) {
			if ((long)this.q * p.p != (long)this.p * p.q) return Long.compare((long)this.q * p.p, (long)this.p * p.q);
			// 반시계 방향 정렬
			
			if (y != p.y) return Integer.compare(y, p.y);
			return Integer.compare(x, p.x);
			// 좌측하단 정렬
		}
	}
	public static int n;
	public static ArrayList<Pair> arr;
	public static Stack<Pair> stack;
	public static long ccw(Pair p1, Pair p2, Pair p3) {
		// s < 0 이면, p1->p2 벡터에 대해 p3은 오른쪽에 존재. (즉 시계방향)
		// s > 0 이면, p1->p2 벡터에 대해 p3은 왼쪽에 존재. (즉 반시계방향)
		// s == 0 이면, 일직선.
		return ((long)p1.x * p2.y + p2.x * p3.y + p3.x * p1.y) - ((long)p1.y * p2.x + p2.y * p3.x + p3.y * p1.x);
	}
	public static void scan(Pair s) {
		Queue<Pair> q = new LinkedList<>(arr);
		stack.add(s);
		stack.add(q.poll());
		// 처음 2개의 점 스택에 추가
		
		while (!q.isEmpty()) {
			Pair curr = q.poll();
			while (stack.size() >= 2) {
				Pair first, second;
				first = stack.pop();
				second = stack.peek();
				
				if (ccw(first, second, curr) < 0) {
					// first - second 으로 직선을 그었을 때 기준, curr는 왼쪽에 존재할 시
					// 그 점은 컨벡스헐을 이룬다고 판단하여 스택에 추가.
					stack.add(first);
					// 이떄 first점은 스택에서 아에 상태이므로 다시 가져감
					break;
				}
				// 오른쪽에 존재하는 경우에는
				// 기존에 있던 점 하나를 제외하고 이전 정점과의 직선으로 다시 판단
			}
			stack.push(curr);
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		arr = new ArrayList<>();
		stack = new Stack<>();
		
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			arr.add(new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
		}
		
		Collections.sort(arr);
		// 가장 좌측하단 점을 기준점으로 사용
		
		Pair start = arr.get(0);
		arr.remove(0);
		
		for (Pair p : arr) {
			p.p = p.x - start.x;
			p.q = p.y - start.y;
		}
		Collections.sort(arr);
		// 기준점을 제외하고 나머지를 반시계 방향순으로 정렬
		
		scan(start);
		System.out.println(stack.size());
	}
}

// 점 하나를 선택 (보통 x 혹은 y 좌표가 가장 작은 점을 선택) => 이렇게 해야 가장 바깥쪽부터 돌아갈 수 있다
// 점 두개를 먼저 잇고 모든 점이 한쪽 방향에만 위치하도록
// 선을 이어나가게끔 선택해나가면 된다. (보통 반시계방향으로 돌아간다)

// 먼저 기준점으로부터 반시계방향이 되도록 정점들의 순서를 정렬
// 그냥 왼쪽부터 차례로 정렬시키면 된다 (X좌표가 큰 순서부터 내림차순 정렬)

// 먼저 이은 직선에 대해 다음 좌표가 대략적으로 좌측인지, 우측인지를 판단
// 이는 CCW를 통해 판단이 가능

// 반시계 방향으로 컨벡스헐을 이루려면
// 먼저 이은 직선에 대해 다음에 이을 좌표가 오른쪽에 존재해서는 안된다
// 오른쪽에 있다는건 더 바깥에 위치하는 점이 있다는 의미가 되기 때문

// 스택에 가장 마지막 두 점을 직선으로 이었을 때, 다음 정점이 나타나는 위치를 계산한다.
// 왼쪽에 점이 나타난다면, 이를 스택에 넣어서 컨벡스헐을 이루는 것이라 판단
// 오른쪽에 점이 나타난다면, 기존에 이었던 정점 중 가장 마지막 것을 내보내고
// 오른쪽에 나타난 점을 새로 스택에 포함시켜 진행 

// 단, 간혹 볼록다각형이 아닌 직선의 형태가 되는 경우도 있다 (CCW값 0)