import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

class Pair {
	int first, second;
	public Pair(int first, int second) {
		this.first = first;
		this.second = second;
	}

	// adj
	// first -> destination
	// second -> distance(weight)
	
	// costs
	// first -> distance
	// second -> depth
}
public class Centroid_Decomposition {
	static final int INF = Integer.MAX_VALUE;
	static int ans = INF;
	static int n, k;
	static int[] size, pathCnt; // pathCnt[i] = 길이가 i인 경로를 이루는 도로의 개수 
	static boolean[] visit; // centroid decomposition시 분할정복 적용때 중복되지 않게 하기 위함
	static ArrayList<Pair> calcDepth;
	static ArrayList<Integer> toReset;
	static ArrayList<Pair>[] adj;	// 
	static int getSize(int curr, int parent) {
		size[curr] = 1;
		for (Pair p : adj[curr])
			if (parent != p.first && !visit[p.first]) size[curr] += getSize(p.first, curr);
		// 그냥 DFS 돌려서 서브트리 사이즈를 모두 더하도록 구하면 된다.
		// parent는 root에서 시작할 경우 -1로 해서 주면 된다.
		return size[curr];
	}
	static int getCentroid(int curr, int parent, int capacity) {
		// capacity는 구하고자 하는 트리의 전체 크기
		// 트리 전체의 Centroid라면 capacity가 n이 되겠지만
		// 서브트리의 Centroid를 연속적으로 구해가는 Centroid Decomposition이면 해당 서브트리의 크기가 된다
		for (Pair p : adj[curr]) 
			if (parent != p.first && !visit[p.first] && size[p.first] * 2 > capacity) 
				return getCentroid(p.first, curr, capacity);
		return curr;
	}
	static void dfs(int curr, int parent, int depth, int sum) {
		if (sum > k) return;
		calcDepth.add(new Pair(depth, sum));
		// 현재 구한 길이가 sum인 경로를 이루는 간선의 개수 depth
		
		System.out.println("dfs : " + curr + " " + parent + " " + depth + " " + sum + " // " + (ans == INF ? "*" : ans) + ", " + (pathCnt[k - sum] == INF ? "*" : pathCnt[k - sum]) + " + " + depth);
		
		if (pathCnt[k - sum] != Integer.MAX_VALUE) {
			if (ans > pathCnt[k - sum] + depth) System.out.println("current ans = " + (pathCnt[k - sum] + depth));
			ans = Math.min(ans, pathCnt[k - sum] + depth);
			// 만약 이전에 구한 k - sum인 경로를 이루는 최소 간선 개수가 있다면
			// 이를 통해 지금 구한 경로의 길이와 합쳐서 길이가 k인 경로의 최소 간선 개수를 비교해본다
		}
		
		for (Pair p : adj[curr])
			if (p.first != parent && !visit[p.first])
				dfs(p.first, curr, depth + 1, sum + p.second);
	}
	static void dnc(int curr, int parent) {
		int centroid = getCentroid(curr, -1, getSize(curr, -1));
		
		System.out.println("\ndnc : (" + curr + " " + parent + ") / centroid : " + centroid);
		
		// 현재 정점을 root로 하는 서브트리의 센트로이드를 알아온다.
		
		// 여기서 해당 센트로이드를 포함하는 부분 문제의 해결 과정을 작성 (분할점을 포함하는 영역에 해당)
		// 이때, 센트로이드가 경로의 끝점인 경우와 중간에 포함되있는 경우를 따로 생각해보면 좋다.
		System.out.print("Before dfs : ");
		for (int i : pathCnt) System.out.print(i == INF ? "* " : i + " ");
		System.out.println();
		
		for (Pair p : adj[centroid]) {
			if (p.first != parent && !visit[p.first]) {
				dfs(p.first, centroid, 1, p.second);
				
				System.out.print("After dfs(" + p.first + " " + centroid + " " + 1 + " " + p.second + ") : ");
				for (int i : pathCnt) System.out.print(i == INF ? "* " : i + " ");
				System.out.println();
				
				for (Pair c : calcDepth) {
					toReset.add(c.second);
					pathCnt[c.second] = Math.min(pathCnt[c.second], c.first);
				}
				calcDepth.clear();
			}
		}
		
		System.out.print("After All dfs : ");
		for (int i : pathCnt) System.out.print(i == INF ? "* " : i + " ");
		System.out.println();
		
		ans = Math.min(ans, pathCnt[k]);
		for (int i : toReset) pathCnt[i] = INF;
		toReset.clear();
		// 다음 분할정복에 방해되지 않도록 목록 초기화
		
		visit[centroid] = true; // 분할정복시, 진행한 센트로이드가 포함되서 중복되지 않도록 체크 
		
		// 센트로이드에 해당하는 부분이 끝났으면, 센트로이드를 포함하지 않는 각 서브트리에 대해 이를 분할정복 진행
		for (Pair p : adj[centroid]) 
			if (!visit[p.first]) dnc(p.first, centroid);
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		
		size = new int[n];
		pathCnt = new int[k + 1]; Arrays.fill(pathCnt, INF);
		visit = new boolean[n];
		calcDepth = new ArrayList<>();
		toReset = new ArrayList<>();
		adj = new ArrayList[n];
		for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
		// 정점은 0부터 n-1까지
		
		for (int i = 0; i < n - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			adj[u].add(new Pair(v, w));
			adj[v].add(new Pair(u, w));
		}
		dnc(0, -1);
		sb.append(ans == INF ? -1 : ans);
		System.out.println(sb);
	}
}

// Centroid Decomposition
// 먼저 Centroid란, 트리의 중심점이 되는 곳으로
// 어느 한 정점을 루트로 각 정점에서의 서브트리 크기를 모두 구했을 때,
// 서브트리의 크기가 전체 크기의 절반을 넘지 않는 정점 중 가장 큰 정점을 의미한다.
// => 이를 이용해서, 각 정점에서의 서브트리 크기를 구하고, 루트부터 DFS를 진행해서
// 	  다음 정점의 크기가 만약 트리의 크기의 절반을 초과한다면 해당 정점에서 다시 DFS를 통해 센트로이드를 판별한다.

// 이를 이용하여 트리에서의 분할정복을 할 수 있다.
// 센트로이드로 분할시킨 각 서브트리는 전체 트리의 크기의 절반을 초과하지 않는다는 특성떄문에
// 매 분할마다 해야할 일이 절반 이하로 감소한다 => 전체 시행횟수에 대해 log 시간대가 보장된다

// P_5820
//	이 문제에서는 길이가 K인 경로를 이루는 것 중 간선의 개수가 최소인 것을 찾아내야 하는데
//	각 정점에서 모든 경로를 파악하는 것은 n^2이 되므로 불가능이다
//	대신에, 히스토그램 너비를 구할때처럼 트리를 쪼개서 분할정복을 하여 한다면 nlogn 시간에 할 수 있을 듯 하다.
//	
//	센트로이드를 구해서 분할정복을 할 것인데
//	먼저 전체 트리를 기준으로 센트로이드를 구한 후, 다음의 두 가지로 쪼개볼 수 있다.
//	1. 센트로이드를 포함한 경로
//	2. 센트로이드를 포함하지 않는 경로
//	
//	여기서 2의 경우는 분할정복을 통해 센트로이드에 의해 쪼개진 서브트리에서 알아서 찾아질 예정이다.
//	현재의 문제에 대해서는 1에 대해서 생각을 해보면 된다
//	
//	다시 두가지로 쪼개서 생각해볼 수 있다.
//	a. 센트로이드가 경로의 끝점인 경우
//	b. 센트로이드를 중간에 포함하는 경로인 경우
//	
//	a의 경우는 센트로이드에서 DFS를 통해 각 끝까지 이어지는 경로를 탐색해봄으로써 알아낼 수 있다.
//	b의 경우가 문제인데, 여기를 이해하는데 상당시간 소요되었다.
//	하지만 다시 잘 생각해보면 b의 경우는 사실 a의 경우의 계산 결과로 구해볼 수 있다.
//	센트로이드를 중간에 포함한다는 것은, 다시 말해서 센트로이드가 끝에 있는 경로 2개를 서로 이어붙였다고 볼 수도 있기 때문
//	예를들어 k=12 이고 지금 a를 구하는 과정 중 지금 탐색중인 경로의 길이가 9, 경로를 이루는 간선의 개수가 2라면
//	자연스럽게 이전에 계산해둔 12에서 9를 제외한 길이가 3인 경로를 이루는 최소 간선 개수가 있다면
//	2개를 이어붙여서 길이가 12인 경로를 만들어 낼 수 있다고 볼 수 있기 때문.
//	또한 순서에 상관없이 어느 하나가 먼저 구해졌었다면 나머지 하나를 구하는 과정 중에 같이 계산해볼 수 있다.
//	
//	이를 통해 분할정복을 하면 결국은 모든 경로를 파악하는 것
//	당연하게도 구하는 도중에 k를 넘어서는 경로는 필요없는 경로이다.
//	
//	////
//	여담으로, 각 정점에서 자기 subtree에 속해 있는 정점들과의 거리를 map에 저장하면, small-to-large 테크닉으로 O(nlog^2n)에 풀 수 있다고 한다.

// 이 문제 같은 경우는 INF 값에 주의할 것
// INF를 넘어가서 서로 비교가 이루어지는 경우가 종종 발생함
// INF를 계산 가능한 int값 내(1e9 같은)로 하거나, long으로 두거나 아니면 INF인지 먼저 검사하는 코드를 추가할 것