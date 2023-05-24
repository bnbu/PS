import java.util.*;
import java.io.*;
public class Main {
	static int n;
	static int[] dist, trace;
	static char s, e;
	static char[] c;
	static ArrayList<Integer> start, end, ans;
	static ArrayList<Integer>[] adj;
	static StringBuilder sb;
	static void getDistance() {
		dist = new int[n + 1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		Queue<Integer> q = new LinkedList<>();
		for (int i : end) {
			q.add(i);
			dist[i] = 0;
		}
		
		while (!q.isEmpty()) {
			int curr = q.poll();
			for (int next : adj[curr]) {
				if (dist[next] <= dist[curr] + 1) continue;
				dist[next] = dist[curr] + 1;
				q.add(next);
			}
		}
	}
	static boolean bfs() {
		int min = Integer.MAX_VALUE;
		for (int i : start) min = Math.min(min, dist[i]);
		ArrayList<Integer> startList = new ArrayList<>();
		for (int i : start)
			if (dist[i] == min) startList.add(i);
		if (min == Integer.MAX_VALUE) return false;
		// 최단 거리에 속하는 정점들만 골라냄

		Queue<Integer> q = new LinkedList<>();
		
		boolean[] visit = new boolean[n + 1];
		trace = new int[n + 1];
		Arrays.fill(trace, -1);
		for (int i : startList) {
			q.add(i);
			visit[i] = true;
		}
		
		sb.append(s);
		// 일단 출발점의 문자는 바로 삽입
		while (!q.isEmpty()) {
			Queue<Integer> inQue = new LinkedList<>();
			char currMin = (char)('Z' + 1);
			while (!q.isEmpty()) {
				int curr = q.poll();
				for (int next : adj[curr]) {
					if (dist[next] >= dist[curr]) continue; 
					if (visit[next]) continue;	
					// 방문했거나, 최단거리가 감소하는 순의 이동이 아니라면 거른다
					visit[next] = true;
					if (c[next] > currMin) continue;
					if (c[next] == s) continue;	
					// 출발문자와 일치하거나, 사전순으로 더 나중인 문자인 경우는 거른다
					currMin = c[next];
					inQue.add(next);
				}
			}
			// 먼저 큐에 있는 것들로 BFS를 진행
			if (currMin <= 'Z') {
				sb.append(currMin);
				if (sb.length() == min + 1) break;
				while (!inQue.isEmpty()) {
					int curr = inQue.poll();
					if (c[curr] == currMin) q.add(curr);
				}
			}
			// 다음 BFS 순서를 진행하기 위해 옮겨 담는다
		}
		return true;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		s = st.nextToken().charAt(0);
		e = st.nextToken().charAt(0);
		
		c = new char[n + 1];
		start = new ArrayList<>();
		end = new ArrayList<>();
		adj = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();

		
		String str = br.readLine();
		for (int i = 0; i < n; i++) {
			c[i + 1] = str.charAt(i);
			if (c[i + 1] == s) start.add(i + 1);
			if (c[i + 1] == e) end.add(i + 1);
		}
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken());
			adj[u].add(v);
			adj[v].add(u);
		}
		getDistance();

		if (!bfs()) sb.append("Aaak!");
		System.out.println(sb);
	}
}
// 맨 처음에는 그냥 단순 경로 역추적 문제일거라 생각했다
// 경로를 방문하는 순서를 요구하는 순서로만 정렬해두면
// 그 순서를 우선적으로 알아서 방문할 것이기 떄문에 상관 없을거라 생각했다

// 실제로 그렇게 생각하고 단순 역추적부터 시작해서 (41% 쯤에서 WA)
// 도착지까지 문자열을 만들어 가서 비교를 통해 알아보는 방법(당연히 TLE 혹은 MLE)
// 이외에 약간씩 비틀어가며 계속 해보았다.

// 그러나, 이전까지는 정점에 번호가 있고, 정점의 번호는 모두 다를 수 밖에 없으나
// 이번의 경우는 정점에 번호와 별개로, 각 번호와 이어지는 문자가 있으며
// 이 문자는 같은 문자일 수도 있다
// 즉 정점 번호로 똑같이 치환시켜 푼다 하면
// 문자순으로 정렬하여 어느 특정 문자로 이동해도 그 뒤의 순서가 보장되지 않는다는 것이 된다

/*
8 9
A B
ACCCHGFB
1 2
1 3
1 4
2 5
3 6
4 7
5 8
6 8
7 8
*/

// 이 예시에서 볼 수 있듯, 1(A) 는 2, 3, 4정점과 모두 연결되어 있으며
// 이들의 문자는 모두 C이다
// 즉 1번 정점에서는 일단 2, 3, 4로 이동은 했다만
// 문자 입장에서 보면 전부 C로 이동한거에 비해 뒤에 이어지는 문자가 모두 다른 상황이 되며
// 이 경우에는 정렬 순서와 관계없이 단순 방문 순서로만 정해지게 된다
// 일단 이 부분을 상당시간 생각해내지 못해서 한 3일정도 매달


// 문제를 해결한 방법은 다음과 같다
// 0.	기본적인 사전 작업 (그래프 만들고 문자열 등등)

// 1.	먼저 출발점이 아닌 도착점에서 BFS를 통해 최단거리를 한번 구한다
//	  	이를 통해, 가장 가까운 출발점들을 골라낼 수 있다
//	 	길이가 가장 짧은것이 우선시 되기 때문
//		당연하게도, 최단거리가 갱신되지 않은 (INF값) 출발점뿐이라면 당연히 도달 불가능이다.\

// 2.	출발점들로부터 다시 BFS를 진행하는데 기존 BFS와는 약간 다르게 진행해야한다
//		계속 쭉 진행하는데 아니라 5427번에서 진행한 것 처럼
//		일단, 큐 안에 먼저 들어와 있는 얘들만을 가지고 BFS를 한단계씩 진행해나간다
//		이번 BFS를 통해 이어지는 다음 정점들은 바로 진행하지 않고
//		한번 모아뒀다가 이번 BFS가 끝나게 되면 다음 BFs때 진행하게 된다

//		이제 BFS를 진행하는 중 다음 정점을 탐색할 때
//		탐색되는 다음 정점의 문자 중 사전순으로 앞서는 문자들만 남겨가며 진행을 하는데
//		이때, 주의할 점이 아무리 사전순으로 앞서더라도 시작문자와 같은 문자는 무시해야한다
//		이러한 경우는 최단거리에 속하지 않는 출발점이라 BFS 시작때 같이 있지 않아서 있는 경우였다

//		여기까지는 이제 어느정도 했는데
//		계속 틀려서 잘 생각못하고 있던 부분이
//		최단거리는 구했지만, 어떻게 출발점으로부터 
//		도착점이 있는 방향을 향해 갈 수 있도록 조정할 수 있는가? 였다

// 		잘 생각해보니 답은 간단했다
//		최단거리를 구할떄 얻을 수 있었던 도착점으로부터의 각 정점까지의 거리 배열에서
// 		아무튼간에 도착점으로 향해 갈 수록 이 최단거리배열의 값은 감소할 수 밖에 없다.
//		그래서 출발점으로부터 탐색을 하는데
//		다음 정점이 만약 지금 정점의 거리값보다 같거나 크다면
//		이는 도착점을 향한 방향이 아니라 판단을 할 수 있게 되어서
//		길이순, 사전순 및 도착점으로의 방향 조정까지 한 끝에 정답처리를 받을 수 있었다

// 굉장히 힘들었던 문제였다
// 시도 횟수 40번 전에 맞춰서 다행이다