import java.io.*;
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		String[] inputs = br.readLine().split(" ");
		int n = Integer.parseInt(inputs[0]), m = Integer.parseInt(inputs[1]);
		String pwd = br.readLine();
		
		inputs = new String[m];
		for (int i = 0; i < m; i++)
			inputs[i] = br.readLine();
		
		for (String s : inputs) {
			int idx = 0;
			boolean check = false;
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) == pwd.charAt(idx)) {
					idx++;
				}
				
				if (idx == pwd.length()) {
					bw.write(true + "\n");
					check = true;
					break;
				}
			}
			
			if (!check)
				bw.write(false + "\n");
		}
		
		bw.flush();
	}
}
// 2020-09-06 01:53 해결
