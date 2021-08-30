import java.util.*;
import java.io.*;
public class Union_Find {
	public static int parent[]; // �� ������ ���� ������ �� (�θ��� ��)
	// �ʱ⿡�� �ڱ� �ڽ��� �θ�� ���´�, �� �ڱ� �ڽ��� �ֻ��� ��ü�ν� ���� ���� �����Ѵ�
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()), // 1 ~ n������ �� 
			m = Integer.parseInt(st.nextToken()); // union Ȥ�� find ���� ��
		
		parent = new int[n + 1];
		for (int i = 1; i <= n; i++)
			parent[i] = i;
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int k = Integer.parseInt(st.nextToken());
			if (k == 0) {
				int a = Integer.parseInt(st.nextToken()),
					b = Integer.parseInt(st.nextToken());
				union(a, b);
			} // union ���� -> b�� a�� ���� ��ü�� union
			else {
				int a = Integer.parseInt(st.nextToken()),
					b = Integer.parseInt(st.nextToken());
				if (find(a) == find(b))
					System.out.println("YES");
				else
					System.out.println("NO");
			} // find ���� b�� a�� ��ü�� ���� ���տ� �����ϴ��� find
			for (int i = 1; i <= n; i++)
				System.out.print(find(i) + " ");
			System.out.println();
		}
	} 
	public static void union(int a, int b) {
		int x = find(a);
		int y = find(b);
		if (x != y) // �� ������ �ֻ�����ü�� ���� �ٸ��ٸ�, union ��Ų��.
			parent[y] = x; // b�� �ֻ��� ��ü�� a�� �ֻ��� ��ü�� ���� ��ü�� ����
	}
	public static int find(int a) {
		if (a == parent[a])
			return a; // a�� a�� �θ�(������ü)�� ���� ���ٸ�, a ��ü�� �ֻ��� ��ü.
		else {
			return parent[a] = find(parent[a]);
			// a�� a�� ������ü�� �ٸ��ٸ�, b(a�� ������ü�� ������ü)�� �ٽ� a�� ������ü��
			// �̷��� �۾��� �����ϸ�, a�� ���ϴ� �ֻ�����ü�� �ٷ� ������ü�� ������ �� �ִ�.
			// �̸� ��� �ݺ���Ű�� �ʱ� O(n)�۾� �ѹ�����
			// ������ find�� ��� O(1)�� ����.
		}
	}
	/*
	7 10
	0 7 6
	1 2 3 4 5 7 7 
	0 5 7
	1 2 3 4 5 7 5 
	0 4 5
	1 2 3 4 4 7 5 
	0 3 4
	1 2 3 3 4 7 5 
	0 2 3
	1 2 2 3 4 7 5 
	0 1 2
	1 1 2 3 4 7 5 
	1 1 6
	YES
	1 1 1 1 1 1 1 => �������� �����ϴ� 6�� find�ϴ� ���� 
	parent[a] = find(parent[a])�� ���� ��� 1�� ���� ���� ��ü�� �Ǵ� ����̴�.
	*/
}
