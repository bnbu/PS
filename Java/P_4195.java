import java.util.*;
import java.io.*;
public class Main {
	public static int[] parent;
	public static int[] cnt;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int t = Integer.parseInt(br.readLine());
		while (t-- > 0) {
			int f = Integer.parseInt(br.readLine()); 
			HashMap<String, Integer> map = new HashMap<>();
			parent = new int[f * 2 + 1];
			cnt = new int[f * 2 + 1];
			for (int i = 1; i <= f * 2; i++) {
				parent[i] = i;
				cnt[i] = 1;
			}
			int num = 0;
			while (f-- > 0) {
				st = new StringTokenizer(br.readLine());
				String f1 = st.nextToken();
				String f2 = st.nextToken();
				if (!map.containsKey(f1) && !map.containsKey(f2)) {
					map.put(f1, ++num);
					map.put(f2, ++num);
				}
				else if (map.containsKey(f1) && !map.containsKey(f2)) 
					map.put(f2, ++num);
				else if (!map.containsKey(f1) && map.containsKey(f2)) 
					map.put(f1, ++num);

				
				int v1 = map.get(f1);
				int v2 = map.get(f2);
				
				int p1 = find(v1);
				int p2 = find(v2);
				
				if (p1 != p2) {
					cnt[p1] += cnt[p2];
				}
				union(v1, v2);

				sb.append(cnt[p1] + "\n");
			}
		}
		System.out.println(sb);
	}
	public static void union(int a, int b) {
		int x = find(a);
		int y = find(b);
		
		if (x != y)
			parent[y] = x;
	}
	public static int find(int a) {
		if (a == parent[a])
			return a;
		else {
			return parent[a] = find(parent[a]);
		}
	}
}
// 2020-12-30 05:18 해결
// 해쉬맵을 이용한 테이블링과 유니온-파인드를 이용하여 구분
// 이때 해당 집합의 최상위 개체가 가지는 하위개체수(자기자신 포함)을 상시 추적시켜
// 하위개체수를 구하는 시간 소요를 O(1)로 줄였다.
