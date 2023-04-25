package D0423;

import java.util.*;
import java.io.*;
public class BipariteMatching {
	static int n, m, match;	// n A그룹 크기 / m B그룹 크기
	static int[] a, b;	// a, b는 각 그룹의 매칭 상태
	static boolean[] visit;
	static ArrayList<Integer>[] adj;
	static boolean dfs(int curr) {
		visit[curr] = true;
		for (int next : adj[curr]) {
			if (b[next] == -1 || !visit[b[next]] && dfs(b[next])) {
				// B그룹이 아직 매칭되지 않았거나
				// 매칭됐었는데, 다른 점으로 다시 매칭이 가능하다면 매칭 성공
				a[curr] = next;
				b[next] = curr;
				return true;
			}
		}
		// 매칭 실패
		return false;
	}
	static void bipariteMatching() {
		match = 0;
		a = new int[n];
		b = new int[m];
		visit = new boolean[n];
		
		Arrays.fill(a, -1);
		Arrays.fill(b, -1);
		// A 그룹과 B 그룹을 매칭
		for (int i = 0; i < n; i++)
			if (a[i] == -1) {
				Arrays.fill(visit, false);
				if (dfs(i)) match++;
			}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		adj = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			adj[i] = new ArrayList<>();
			st = new StringTokenizer(br.readLine());
			int k = Integer.parseInt(st.nextToken());
			while (k-- > 0) {
				int u = Integer.parseInt(st.nextToken());
				adj[i].add(u - 1);
			}
		}
		
		bipariteMatching();
		System.out.println(match);
	}
}
// 이분 매칭
// source -> A 그룹 -> B 그룹 -> sink 로 이어지는 그래프에서
// source, sink를 생략하고 A 그룹 -> B 그룹만 그린 그래프를 이분 그래프
// 에드몬드 카프를 통해 최대 유량을 구하게 되면 O(VE^2) 이지만
// 이분 매칭을 진행하면 최대 O(VE)으로 더 빠르게 구할 수 있는 최적화가 있다.

// 이때의 가정은 각 간선들의 용량은 모두 1이고, 반드시 A에서 B꼴로만 이어져야 한다.

// 평소 네트워크 플로우 식으로 생각을 한다면 증가 경로를 찾아 유량을 흘려보내고 식으로 진행했겠지만
// 이분 그래프에서는 source와 sink를 모두 생략한 채 시작하므로 다르게 생각해야 한다.

// 기본적으로 경로는 source -> a -> b -> sink 꼴이지만
// a와 b 사이에 무수히 많은 역방향 간선을 타서 이동이 가능
// 양쪽 source, sink는 어차피 같으므로 배제하고 중간 과정을 간소화 하는것이 핵심

// A (a b c d e) -> B (1 2 3 4 5)
// a 2 5
// b 2 3 5
// c 1 5
// d 1 2 5
// e 2

// 1. A그룹의 아무 정점부터 시작해서 (그냥 제일 첫번째거부터 해도 무방함)
//	  선택한 정점에서 아직 매칭되지 않은 곳에 대해 매칭을 시도
// 	  a부터 시작한다고 하면, (a-2)가 먼저 매칭

// 2. 다음 정점인 b는 (2, 3, 5)로의 간선이 존재
//	  이때, (b-2)는 이미 매칭된 (a-2)가 존재
// 	  따라서, a가 (a-2)이외에 가능한 매칭이 있는지 체크해보고 체크 결과 (a-5)가 가능하므로
//	  (a-5)를 매칭시키고 b는 (b-2)로 매칭시킨다

// 3. c는 바로 (c-1)로 매칭이 가능

// 4. d는 (1, 2, 5)로의 간선이 존재
//	  (d-1)을 하기엔 이미 (c-1)이 존재하므로 c의 다른 매칭 존재 가능성을 찾는다
//	  (c-5)를 하기엔 (a-5)가 존재하는 상황
// 	  (a-2)를 하면 (b-2)가 사라지지만 b는 (b-3)으로의 매칭이 가능

// 5. e는 이어진 정점이 하나뿐인데 어떠한 매칭도 이루어질 수 없다.
// 	  (e-2)를 진행하면 (a-2)가 끊어지고, (a-5)로 매칭
//	  (a-5)가 매칭되면 (c-5)가 끊어지고, (c-1)로 매칭
// 	  (c-1)이 매칭되면 (d-1)이 끊어지고, (d-5)로 매칭
//	  (d-5)가 매칭되면 (a-5)를 다시 건들게 되므로 dead end 발생

// 따라서 가능한 최대 매칭 수는 4개다.
