package D0410;

import java.util.*;
import java.io.*;
public class ArticulationPoint {
	public static int v, e, cnt = 0;
	public static int[] discover;
	public static boolean[] isCut;
	public static ArrayList<Integer>[] adj;
	public static int dfs(int curr, boolean isRoot) {
		int ret = discover[curr] = ++cnt;
		// 방문 순서대로 카운팅
		
		int child = 0;
		// 자식 수 카운트
		
		for (int next : adj[curr]) {
			if (discover[next] != -1) {
				ret = Math.min(ret, discover[next]);
				continue;
			}
			// 방문한 적이 있다면, 지금 정점과 먼저 방문한 정점간 빠른 순서를 체크

			
			child++;
			int prev = dfs(next, false);
			// dfs를 계속 진행 및 자식 수 계산
			
			System.out.println(curr + " " + prev + " " + next + " " + discover[curr]);
			if (!isRoot && prev >= discover[curr]) isCut[curr] = true;
			// root가 아닌데, 방문 결과 앞선 순서의 정점으로 
			
			ret = Math.min(ret, prev);
		}
		
		if (isRoot)
			isCut[curr] = (child >= 2);
		
		return ret;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		v = Integer.parseInt(st.nextToken());
		e = Integer.parseInt(st.nextToken());
		
		discover = new int[v + 1];
		isCut = new boolean[v + 1];
		Arrays.fill(discover, -1);
		
		adj = new ArrayList[v + 1];
		for (int i = 1; i <= v; i++) adj[i] = new ArrayList<>();
		
		for (int i = 0; i < e; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken());
			
			adj[a].add(b);
			adj[b].add(a);
		}
		
		for (int i = 1; i <= v; i++)
			if (discover[i] == -1) dfs(i, true);
		
		int ans = 0;
		for (int i = 1; i <= v; i++)
			if (isCut[i]) ans++;
		
		for (int i = 1; i <= v; i++) 
			System.out.println(i + " " + isCut[i]);
		
		System.out.println(ans);
	}
}

// 단절점 (Articulation Point)

// 단절점은 다음과 같은 특성을 가진다
// 해당 정점을 제거시, 그래프는 두개의 component로 나뉘어진다.

// 이 특성으로부터 알 수 있는 사실은
// 어떤 정점이 단절점이려면 이 정점에 인접한 정점들이 해당 정점을 지나지 않고서 도달할 수 없어야 한다.

// 이를 체크하기 위해, 어느 한 정점(ROOT)로부터 DFS를 시행했을 때
// Tarjan때처럼 각 정점에 방문 순서를 매겼을 때
// 어느 정점에서 자식 노드들이 그 정점을 거치지 않고 그 정점보다 방문 순서가 더 빠른 정점에 갈 수 없다면 단절점이다.

// 예외로, root의 경우는 자식의 수가 2개 이상이면 반드시 단절점이다.

// ex )

//8 9
//1 2
//2 3
//2 4
//3 4
//4 5
//4 8
//5 6
//6 7
//6 8

// 다시 말해서, discover[curr] **현재 정점의 방문 순서
// prev **dfs시 도달 가능한 가장 빠른 순서의 정점
// prev가 더 작은 경우는 다시 말해서 더 앞선 정점까지 도달이 가능한 경로가 있다는 뜻 => curr은 단절점이 아니다
// 그렇지 않은 경우는 경로가 curr에서 오는 경로밖에 없다는 뜻이 된다 => curr은 단절점이다.