import java.util.*;
import java.io.*;
class Query {
	int a, b, c;
	public Query(int a, int b) {
		this.a = a;
		this.b = b;
	}
	public Query(int a, int b, int c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
}
public class Main {
	static int n;
	static int[] currParent, parent;
	static Query[] queries;
	static void union(int u, int v) {
		int x = find(u),
			y = find(v);
		
		if (x != y)
			currParent[y] = x;
	}
	static int find(int v) {
		if (v == currParent[v]) return v;
		return currParent[v] = find(currParent[v]);
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		int q = Integer.parseInt(st.nextToken());
		
		currParent = new int[n + 1];
		parent = new int[n + 1];
		queries = new Query[n + q - 1];
		for (int i = 1; i <= n; i++) currParent[i] = i;
		
		for (int i = 2; i <= n; i++) parent[i] = Integer.parseInt(br.readLine()); 
		
		for (int i = 0; i < queries.length; i++) {
			st = new StringTokenizer(br.readLine());
			int j = Integer.parseInt(st.nextToken());
			
			if (j == 0) queries[i] = new Query(j, Integer.parseInt(st.nextToken()));
			else queries[i] = new Query(j, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		
		Stack<Boolean> stack = new Stack<>();
		for (int i = queries.length - 1; i >= 0; i--) {
			if (queries[i].a == 0) union(queries[i].b, parent[queries[i].b]);
			else {
				stack.push(find(queries[i].b) == find(queries[i].c));
			}
		}
		
		while (!stack.isEmpty()) sb.append(stack.pop() ? "YES\n" : "NO\n");
		System.out.print(sb);
	}
}
// Offline Query 기법을 사용하는 문제

// 트리의 정점 개수가 N개일 때
// 정확히 N-1번 트리의 간선을 끊어낼 때
// 어느 특정 시점의 두 정점의 경로가 존재하는지에 대해 답해야한다.

// 일단 트리 구조이기 때문에, 어느 두 정점을 잇는 간선을 끊어낸다면
// 두 정점은 같은 트리내에 속하지 않게 되는, 즉 포레스트의 구조가 되게 된다
// 이 간선을 이용하여 경로를 구성하는 모든 임의의 두 정점 쌍은 더 이상 경로가 존재하지 않는다 할 수 있다

// 일단 어느 두 정점의 경로의 존재는 단순 그래프 탐색으로도 알아낼 수 있지만
// 매번 간선이 사라진다는 점에서 어느 특정 시점에 계속 그래프를 탐색할 수는 없다

// 여기서 반대로
// 어느 두 정점을 잇는 간선을 없애는 것이 아닌
// 어느 두 정점을 잇는 간선을 추가한 다음, 임의의 두 정점을 잇는 경로가 있는지를 물어본다면
// 이는 union-find로 쉽게 생각해볼 수 있다.

// 따라서 입력받은 쿼리를 모두 한번에 저장했다가
// 역순으로 쿼리를 진행하여 union-find 방식으로 진행해나가면
// 무리없이 해낼 수 있으며, 정답을 다시 쿼리의 순서에 맞게만 출력하면 된다

// 이처럼 입력받는 즉시 바로 수행해내지 않고
// 쿼리를 저장했다가 우리에게 유리한 순서로 재배치하여 진행하는 것을 오프라인 쿼리라 함
