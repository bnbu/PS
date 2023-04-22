import java.util.*;
import java.io.*;
public class FloydWarshall {
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
				d[i][j] = i == j ? 0 : Integer.MAX_VALUE;
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

// ��� ���� �� �� �ִܰŸ��� ���ϴ� �÷��̵�-�ͼ�
// ���� ���� V�� ���� O(V^3)�� ���⵵�� ����
// ���� i�� j������ ��� �������� Ž���Ͽ�, �ִ� ��θ� ã�� �������,
// �׷����� ������ 1~V������� �Ѵٸ�, �������� k�̴�.
// ��, ������ k�� ��ġ�� ���� i�� j������ �Ÿ� �� �ִܰŸ��� �����ϴ°�.
// �ٽ� ����, d[i][j] �� ���ݱ����� �ִܰŸ� d[i][j]�� k�� ������ �ִܰŸ�
// d[i][k] + d[k][j] �� ���ϰ� �Ǵ� ���̴�.

// ������ pair Ŭ���� �ʿ����
// 2���� �迭�� ��������� �����Ͽ� �Ѵ�.

// 1->2 / 1->3 / 1->4 / 2->3 / 2->4 / 3->4 �� ������ ������ �׷����� �ִٰ� ����
// �켱 ���� ����� ������ ������, ��������� ���� �����Ѵ�
// ����, K=1,2,3,4 �϶�, �� ���� ���� 1,2,3,4�϶��� �Ÿ��� ��� ����Ͽ�
// ���� �Ÿ��� ���Ͽ� �ִܰŸ��� �������ش�.

// ����
//4
//8
//1 2 7
//1 3 5
//2 4 3
//3 1 2
//3 2 1
//3 4 8
//4 2 4
//4 3 2

//���� ������ �Է�//
//0 7 5 INF 
//INF 0 INF 3 
//2 1 0 8 
//INF 4 2 0 
//
//Floyd-Warshall ���� ��
//0 6 5 9 
//7 0 5 3 
//2 1 0 4 
//4 3 2 0 