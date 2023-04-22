import java.util.*;
import java.io.*;
public class KMP {
	public static ArrayList<Integer> idxList;
	public static int[] fail;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String t = br.readLine();
		String p = br.readLine();
		
		idxList = new ArrayList<>();
		fail = new int[p.length()];
		
		int j = 0;
		for (int i = 1; i < p.length(); i++) {
			while (j > 0 && p.charAt(i) != p.charAt(j))
				j = fail[j - 1];
			if (p.charAt(i) == p.charAt(j))
				fail[i] = ++j;
		}
		// �����Լ��� ���ϴ� ����
		// �����Լ��� P���� P�� ã�� �������� �����Ѵ�.
		// 
		// ex) ABCDABD
		// �̶�, fail(0)�� �׻� 0�̹Ƿ� �ǳʶڴ� -> fail(0) = 0
		// i�� 1, j�� 0�̰�, 1��(B)�� 0��(A)�� ���� ���� �����Ƿ� ��� -> fail(1) = 0
		// i�� 2, j�� 0�̰�, 2��(C)�� 0��(A)�� ���� ���� �����Ƿ� ��� -> fail(2) = 0
		// i�� 3, j�� 0�̰�, 3��(D)�� 0��(A)�� ���� ���� �����Ƿ� ��� -> fail(3) = 0
		// i�� 4, j�� 0�̰�, 4��(A)�� 0��(A)�� ���� ���� -> fail(4) = 1
		// i�� 5, j�� 1�̹Ƿ�, while�������� Ž���� ����
		// => j�� 1, 5��(B)�� 1��(B)�� ���� ����, while�� ���� -> fail(5) = 2; 
		// i�� 6, j�� 2�̹Ƿ�, while�������� Ž���� ����
		// => j�� 2, 6��(D)�� 2��(C)�� �ٸ���. j = fail[1] = 0�� �ȴ�.
		// => j�� 0, 6��(D)�� 0��(A)�� �ٸ���, j�� 0�̹Ƿ� while�� ���� -> fail(6) = 0
		
		// ���� : ������ 1�� �ε������� ����
		// j�� 0�̶��, ���� Ž����ġ�� ������ ã��
		// j�� 0���� ũ�ٸ�, j���� �ٽ� �ڷ� �ٿ����鼭 ��� ��ġ�ϴ��� Ž��
		// �̶�, j�� �ϳ��� ���̴°� �ƴ�, ���� Ž����ġ�� �����Լ����� �����´�.
		// ������ i��°���� �� -> ���̻�� �� �� �ְ�
		// ���ϴ� ���� �տ������� �� -> ���λ�� �� �� �ִ�
		// �������� �����Լ� fail(i)�� i��° �ε��������� ���ڿ� ��
		// ���λ�� ���̻��� ���� ���빮�ڿ� �����̱� ����.
		
		j = 0;
		for (int i = 0; i < t.length(); i++) {
			while(j > 0 && t.charAt(i) != p.charAt(j))
				j = fail[j - 1];
			if (t.charAt(i) == p.charAt(j)) {
				if (j == p.length() - 1) {
					idxList.add(i - p.length() + 2);
					// => j�� ������ ������idx
					// �� ��� ��ġ�Ѵ� ���̹Ƿ�, ���� �ε����� i - p.length + 1
					// �̶�, �ε����� �ƴ� ���� ��°�� �ؾ��ϹǷ� + 1�� �� ���ش�.
					j = fail[j];
					// ������ ���� �ٽ� Ž���ؾ��ϹǷ� j�� �ʱ�ȭ
					// �̶� 0���� �ʱ�ȭ ���� �ʰ�
					// fail(j)�� ���� ������
					// ���� Ž������ ��ġ���� ���� ��ġ�� ���ڿ��� �����Ͽ�
					// �ǳʶ��� �ʵ��� �ϱ� �����̴�.
				}
				else
					j++;
				// j�� ������ ������ idx�� �ƴ϶��, ������ Ȯ�� �ε����� j�� 1�� �÷���
				// ���� i�� ���� j�� ���� ������.
			}
		}
		
		System.out.println(sb);
	}
	public static ArrayList<Integer> kmp(String orign, String pattern) {
		ArrayList<Integer> idxList = new ArrayList<>();
		int[] failFunction = new int[pattern.length()];
		
		int j = 0;
		for (int i = 1; i < pattern.length(); i++) {
			while(j > 0 && pattern.charAt(i) != pattern.charAt(j))
				j = failFunction[j - 1];
			if (pattern.charAt(i) == pattern.charAt(j))
				failFunction[i] = ++j;
		}
		// �����Լ�

		for (int i : failFunction)
			System.out.print(i + " ");
		System.out.println();
		
		j = 0;
		for (int i = 0; i < orign.length(); i++) {
			while (j > 0 && orign.charAt(i) != pattern.charAt(j))
				j = failFunction[j - 1]; // ��ġ���� ������ j�� ���� idx�� ���� �Լ� ������ ����������.
			if (orign.charAt(i) == pattern.charAt(j)) {
				if (j == pattern.length() - 1) {
					idxList.add(i - pattern.length() + 1);
					j = failFunction[j];
				} // ���������� ��ġ��, ���� �ε����� ��ȯ����Ʈ�� �߰�
				else
					j++; // �������� �ƴϸ�, i�� ���� j�� 1 �ø�
			}
		}
		
		for (int i : idxList)
			System.out.print(i + " ");
		System.out.println();
		
		return idxList;
	}
}
