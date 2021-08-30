import java.util.*;
import java.io.*;
public class Main {
	public static int[] parent;
	public static String[] name;
	public static HashMap<String, Integer> map;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());

		parent = new int[n + 1];
		name = new String[n + 1];
		map = new HashMap<>();
		for (int i = 1; i <= n; i++) {
			parent[i] = i;
			String[] s = br.readLine().split(" ");
			name[i] = s[2];
			map.put(name[i], i);
		}
		
		while (m-- > 0) {
			String[] s = br.readLine().split(",");
			String[][] splited = new String[3][];
			for (int i = 0; i < 3; i++)
				splited[i] = s[i].split(" ");

			int num1 = map.get(splited[0][2]),
				num2 = map.get(splited[1][2]),
				win = Integer.parseInt(splited[2][0]);
			if (win == 1) 
				union(num1, num2);
			else 
				union(num2, num1);
		}
		
		HashSet<Integer> set = new HashSet<>();
		for (int i = 1; i <= n; i++)
			set.add(find(i));
		
		ArrayList<String> al = new ArrayList<>();
		for (int i : set)
			al.add(name[i]);
		
		Collections.sort(al);
		
		sb.append(al.size() + "\n");
		for (String s : al)
			sb.append("Kingdom of " + s + "\n");
		
		System.out.println(sb);
	}
	public static void union(int a, int b) {
		int x = find(a);
		int y = find(b);
		if (x != y)
			parent[y] = x;
		else {
			parent[y] = a;
			parent[a] = a;
		}

	}
	public static int find(int a) {
		if (a == parent[a])
			return a;
		else
			return parent[a] = find(parent[a]);
	}
}
// 2021-01-10 19:12 해결
// 문제 자체는 이전의 전국시대(15809)와 다른게 없다
// 근데 문자열로 입력 및 유니온파인드 -> 종주국 출력까지 파싱을 해야해서
// 좀 까다로웠음
