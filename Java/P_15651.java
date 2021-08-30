import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.*;

public class Main {
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static ArrayList<Integer>[] arr;
	static boolean[] visit;
	static int[] numbers;
	static String s = "";
	static int n;
	static int m;
	
	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		int totalNodeNum = 0;
		
		for (int i = 0; i <= m; i++) {
			totalNodeNum += Math.pow(n, i);
		}
		
		arr = new ArrayList[totalNodeNum];
		visit = new boolean[totalNodeNum];
		numbers = new int[totalNodeNum];
		
		for (int i = 1; i < totalNodeNum; i++) {
			numbers[i] = i % n == 0 ? n : i % n;
		}
		for (int i = 0; i < totalNodeNum; i++) {
			arr[i] = new ArrayList<Integer>();
		}
		
		for (int i = 0; i < n; i++) {
			arr[0].add(i + 1);
		}
		
		int currentNum = 0;
		while (currentNum < totalNodeNum) {
			if (currentNum <= n) {
				for (int i = 1; i < totalNodeNum; i++) {
					arr[i].add(currentNum);
					if (i % n == 0) {
						currentNum++;
					}
				}
				currentNum = n + 1;
			}
			else {
				for (int i = 1; i < totalNodeNum; i++) {
					for (int j = 0; j < n; j++) {
						arr[i].add(currentNum);
						currentNum++;
					}
					if (currentNum == totalNodeNum)
						break;
				}
			}
		}

		dfs(0);
		bw.flush();
	}
	
	public static void dfs(int node) throws IOException {
		visit[node] = true;
		int j = 0;
		int k = 0;
		for (int i = 0; i <= m; i++) {
			j += Math.pow(n, i);
			if (node <= j) {
				k = i;
				break;
			}
		}
		
		if (s.length() == 2 * m) {
			if (k == 1) {
				s = "";
			}
			else if (k == m) {
				s = s.substring(0, 2 * (m - 1));
			}
			else {
				s = s.substring(0, 2 * (k - 1));
			}
			
		}
		if (node != 0) {
			s += numbers[node] + " ";
		}
		
		if (s.length() == 2 * m) {
			bw.write(s + "\n");
		}
		
		for (int i : arr[node]) {
			if (!visit[i]) {
				dfs(i);
			}
		}
	}
}
// => 노드를 이어서 정통 dfs 탐색 방법 적용
