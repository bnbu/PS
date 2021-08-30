import java.util.*;
import java.io.*;
public class Main {
	public static int[] parent;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		parent = new int[n * m];
		for (int i = 0; i < n * m; i++)
			parent[i] = i;
		
		for (int i = 0; i < n; i++) {
			String s = br.readLine();
			for (int j = 0; j < m; j++) {
				char c = s.charAt(j);
				if (c == 'U') 
					union(i * m + j, (i - 1) * m + j);
				else if (c == 'D')
					union(i * m + j, (i + 1) * m + j);
				else if (c == 'L') 
					union(i * m + j, i * m + j - 1);
				else if (c == 'R') 
					union(i * m + j, i * m + j + 1);
			}
		}
		
		HashSet<Integer> set = new HashSet<>();
		for (int i = 0; i < n * m; i++)
			set.add(find(i));
		
		System.out.println(set.size());
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
		else
			return parent[a] = find(parent[a]);
	}
}
// 2020-12-31 05:22 해결
// U,D,L,R에 맞춰서 다음 칸이랑 유니온을 진행
// 모든 유니온 작업 진행 후 존재하는 최상위 개체의 갯수만 따로 카운트
