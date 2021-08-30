import java.util.*;
import java.io.*;
public class SparseTable {
	public static int[][] f;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int n = Integer.parseInt(br.readLine());
		f = new int[n + 1][20];
		
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) {
			f[i][0] = Integer.parseInt(st.nextToken());
		}
		calc(n);
		
		int m = Integer.parseInt(br.readLine());
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken());
			
			sb.append(func(b, a) + "\n");
		}
		System.out.print(sb);
	}
	public static void calc(int n) { 
		for (int j = 1; j < 20; j++)
			for (int i = 1; i <= n; i++)
				f[i][j] = f[f[i][j - 1]][j - 1];
	}
	public static int func(int x, int n) {
		int ret = x;
		for (int i = 19; i >= 0; i--) {
			if (n >= 1 << i) {
				ret = f[ret][i];
				n -= 1 << i;
			}
		}
		return ret;
	}
}

//O(logN)�� LCA�� ����ϱ� ���� ����� ��ҹ迭�� ����

//���� ��ü�� �ռ��Լ� f^n(x)�� ���� ������ ã�� �������� ���� ����� ����ϴ� ��.

//�̶�, f^n(x)�� Ʈ������ �θ�� �Ž��� �ö󰡵�, ������ ������ ��ã�ư��� ����� �ִ�.
//������, �̶��� �ϳ��� �Ž������� O(N)�� �ȴ�.

//���⼭, ������ ��� ���� 2�� �ŵ������� ������ ��Ÿ�� �� ������ ����
//f^n(x)�� 2^0 ~ 2^19���� �̸� ����� �� �� ����
//������ �Է¹��� n�� ���� 2^i�� �Ž��� �ö󰡴� �۾��� �Ѵ�
//�̷��� O(logN)���� ����� �����ϴ�.