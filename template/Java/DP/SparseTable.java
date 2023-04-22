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

//O(logN)의 LCA를 계산하기 위해 사용할 희소배열의 문제

//문제 자체는 합성함수 f^n(x)의 값을 빠르게 찾는 쿼리문에 대한 결과를 계산하는 것.

//이때, f^n(x)는 트리에서 부모로 거슬러 올라가듯, 이전의 값으로 되찾아가는 방법이 있다.
//하지만, 이때는 하나씩 거슬러가면 O(N)이 된다.

//여기서, 세상의 모든 수는 2의 거듭제곱의 합으로 나타낼 수 있음에 착안
//f^n(x)를 2^0 ~ 2^19까지 미리 계산을 해 둔 다음
//쿼리에 입력받은 n에 대해 2^i씩 거슬러 올라가는 작업을 한다
//이러면 O(logN)만에 계산이 가능하다.