import java.util.*;
import java.io.*;
public class KnapSack {
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static StringBuilder sb = new StringBuilder();
	public static int max = 0;
	public static int[][] memo;
	public static void main(String[] args) throws IOException {
		String[] s = br.readLine().split(" ");
		int n = Integer.parseInt(s[0]), k = Integer.parseInt(s[1]);
		int[] w = new int[n + 1], v = new int[n + 1];
		memo = new int[n + 1][k + 1];
		// n��° ������ ���� �� �ְ�, ���� ������ k �϶� �ִ� ��ġ�� �����ϴ� �迭.
		// ���ǻ� 0�� �ε����� ���X
		for (int i = 1; i < n + 1; i++) {
			s = br.readLine().split(" ");
			w[i] = Integer.parseInt(s[0]);
			v[i] = Integer.parseInt(s[1]);
		} // ���Կ� ��ġ ����
		
		for (int i = 1; i < memo.length; i++) {
			for (int j = 1; j < memo[i].length; j++) {
				if (w[i] <= j)
					memo[i][j] = Math.max(v[i] + memo[i - 1][j - w[i]], memo[i - 1][j]);
				else
					memo[i][j] = memo[i - 1][j];
				
				max = Math.max(max, memo[i][j]);
			}
		}
		// i��° ���Ǹ� ���� �� �ִ� ��Ȳ�̰�, ���� ������ j��� ����
		// i��° ������ ���� w[i]�� ���� w[i] <= j �� ���� �� �ִ� ��Ȳ
		// ���� �� ������, ������ �ְ�, ���� ���� ���� j - w[i]����
		// ���� ���� ���� �� j - w[i] ���� �����ϴ� �ִ� ��ġ�� �ִٸ�
		// �װͰ� ���ؼ� ���� ��������, ������° �����϶��� �ִ�ġ�� ���ؼ�
		// �ִ��� �����Ѵ� (memo[i][j]���� �׻� �ִ񰪸� ��)
		
		// ���� ������ ������Ű�� ���Ѵٸ�, ������° ���� ���������� �ִ밡ġ�� �����´�
		// **ù��° (i == 1)�϶��� 0��°�� �����ϰ�, 0��°�� �˴ٽ��� ��� 0��.
		
		// �Ϸ��� ������ ���� n��° ����, ���������� k�϶��� ������ ���Ѵ�.
		
		for (int[] arr : memo) {
			for (int i : arr)
				System.out.print(i + " ");
			System.out.println();
		}
		
		System.out.println(max);
	}
}
