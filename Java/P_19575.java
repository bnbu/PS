import java.io.*;
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		String[] inputs = br.readLine().split(" ");
		final long num = 1000000007;
		int n = Integer.parseInt(inputs[0]);
		int x = Integer.parseInt(inputs[1]);
		int[] a = new int[n + 1];
		for (int i = a.length - 1; i >= 0; i--) {
			inputs = br.readLine().split(" ");
			a[i] = Integer.parseInt(inputs[0]);
		}
		long sum = 0;
		for (int i = a.length - 1; i >= 0; i--) {
			sum *= x;
			sum += a[i];
			sum %= num;
		}
		
		bw.write(sum + "\n");
		bw.flush();
	}
}
// 2020-09-02 12:50 해결
// 매 연산마다 mod 연산을 해줘야 long 범위를 안넘어감, 사실 int 범위도 안넘음
