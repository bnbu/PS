import java.io.*;
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		String[] input = br.readLine().split(" ");
		int n = Integer.parseInt(input[0]), q = Integer.parseInt(input[1]);
		long[] arr = new long[n + 1];
		while (q-- > 0) {
			input = br.readLine().split(" ");
			int num = Integer.parseInt(input[0]), value1 = Integer.parseInt(input[1])
					, value2 = Integer.parseInt(input[2]);
			
			if (num == 1) 
				arr[value1] += value2;
			else {
				long result = 0;
				for (int i = value1; i <= value2; i++)
					result += arr[i];
				bw.write(result + "\n");
			}
		}
		bw.flush();
	}
}
// 2020-09-18 해결
// 아나 ㅋㅋ long
