import java.util.*;
import java.io.*;
public class Main {
	public static int[][] tree;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		tree = new int[26][2]; // 0-> left / 1 -> right
		
		while (n-- > 0) {
			st = new StringTokenizer(br.readLine());
			int v = st.nextToken().charAt(0) - 'A';
			tree[v][0] = st.nextToken().charAt(0) - 'A';
			tree[v][1] = st.nextToken().charAt(0) - 'A';
		}
		
		preOrder(0);
		System.out.println();
		inOrder(0);
		System.out.println();
		postOrder(0);
	}
	public static void preOrder(int v) {
		System.out.print((char)(v + 'A'));
		if (tree[v][0] >= 0)
			preOrder(tree[v][0]);
		if (tree[v][1] >= 0)
			preOrder(tree[v][1]);
	}
	public static void inOrder(int v) {
		if (tree[v][0] >= 0)
			inOrder(tree[v][0]);
		System.out.print((char)(v + 'A'));
		if (tree[v][1] >= 0)
			inOrder(tree[v][1]);
		
	}
	public static void postOrder(int v) {
		if (tree[v][0] >= 0)
			postOrder(tree[v][0]);
		if (tree[v][1] >= 0)
			postOrder(tree[v][1]);
		System.out.print((char)(v + 'A'));
	}
}
// 2020-12-23 03:33
// 자료구조에서 배운 트리의 순회..
// 교수님 감사합니다..
