import java.util.*;
import java.io.*;
public class FloydWarshall_CycleLength {
	public static int[][] d;
	public static int n, m;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		m = Integer.parseInt(br.readLine());
		
		d = new int[n + 1][n + 1];
		for (int i = 1; i < d.length; i++)
			for (int j = 1; j < d[i].length; j++) {
				d[i][j] = Integer.MAX_VALUE;
			}
		
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			d[u][v] = Math.min(d[u][v], w); // �ߺ� �Է��� �� ���� �����Ƿ�
			// ���� �Է°��� ���ο� ���� �� ���� ���� �����س��´�.
		}
		System.out.println("���� ������ �Է�//");
		print();
		System.out.println();
		
		System.out.println("Floyd-Warshall ���� ��");
		floyd();
		print();
		
		System.out.println("�� �������� ���� ����Ŭ ���� ����");
		for (int i = 1; i < d.length; i++) {
			System.out.print(d[i][i] == Integer.MAX_VALUE ? "INF " : (d[i][i] +  " "));
		}
		System.out.println();
		
	}
	public static void floyd() {
		// k ������ / �Ÿ��� ������ �� ����.
		for (int k = 1; k <= n; k++) {
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					if (d[i][k] != Integer.MAX_VALUE && d[k][j] != Integer.MAX_VALUE)
						d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
				}
			}
		}
	}
	public static void print() {
		for (int i = 1; i < d.length; i++) {
			for (int j = 1; j < d[i].length; j++)
				System.out.print(d[i][j] == Integer.MAX_VALUE ? "INF " : (d[i][j] + " "));
			System.out.println();
		}
	}
}

// ���� �÷��̵�-�ͼ�����, i == j�� ��쵵 �ʱⰪ�� INF�� �ΰ�
// �÷��̵�-�ͼ��� �����Ѵ�.
// �̶�, ����Ŭ�� �����Ѵٸ�, i == j�� ����� �ִܰŸ��� ����Ŭ�� ���̷� ���ŵ� ��.
// ���� �� �Ÿ��� ����Ŭ�� ������ �� �� �ִ�.

// �Ǵ�, �Ϲ� �÷��̵带 �ؼ�, i->j �� j->i �� ��� �����Ѵٸ�,
// �� ���� �Ÿ��� ���� �ٷ� ����Ŭ�� �����̴�.

// ���� �� 2���� ����� �ִ�
// 1. �Ÿ��� ��� INF�� �� ����, i == j�� ��찡 ������ �Ǵ� �ŷ� �Ǵ�
// 2. �Ϲ� �÷��̵��, i->j �� j->i�� ��� �����ϴ� �ŷ� �Ǵ�

// ����
//6
//7
//1 2 1
//2 3 2
//3 1 3
//3 4 2
//4 5 3
//5 3 4
//5 6 5
//���� ������ �Է�//
//INF 1 INF INF INF INF 
//INF INF 2 INF INF INF 
//3 INF INF 2 INF INF 
//INF INF INF INF 3 INF 
//INF INF 4 INF INF 5 
//INF INF INF INF INF INF 
//
//Floyd-Warshall ���� ��
//6 1 3 5 8 13 
//5 6 2 4 7 12 
//3 4 6 2 5 10 
//10 11 7 9 3 8 
//7 8 4 6 9 5 
//INF INF INF INF INF INF 
//�� �������� ���� ����Ŭ ���� ����
//6 6 6 9 9 INF 