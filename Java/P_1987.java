import java.util.*;

public class Main {
	static int r, c;
	static ArrayList<Integer>[] arr;
	static char[] chars;
	static boolean[] visit;
//	static Stack<Integer> indexStack = new Stack<>();
	static Stack<Character> stack = new Stack<>();
	static int max = 0;
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		r = scan.nextInt();
		c = scan.nextInt();
		scan.nextLine();
		
		arr = new ArrayList[r * c]; 
		visit = new boolean[26];
		chars = new char[r * c];
		
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new ArrayList<>();
		}
		for (int i = 0; i < r; i++) {
			String input = scan.nextLine();
			for (int j = 0; j < c; j++) {
				chars[i * c + j] = input.charAt(j);
			}
		}
		
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				int index = i * c + j;
				if (j > 0) 
					arr[index].add(index - 1);
				if (j < c - 1)
					arr[index].add(index + 1);
				if (i > 0)
					arr[index].add(index - c);
				if (i < r - 1)	
					arr[index].add(index + c);
			}
		}
		
		dfs(0);
		System.out.println(max);
	}
	
	public static void dfs(int num) {
		int index = chars[num] - 65;
		stack.push(chars[num]);
//		indexStack.push(num);
//		System.out.println(stack);
//		System.out.println(indexStack);
		max = max < stack.size() ? stack.size() : max;
		visit[index] = true;
		
		for (int i : arr[num]) {
			int nextIndex = chars[i] - 65;
			
			if (!visit[nextIndex])
				dfs(i);
		}
		
		stack.pop();
//		indexStack.pop();
		visit[index] = false;
	}
}
// 2020 08 25 13:29 해결같지 않은 해결
// 중점은 방문의 기준. 방문을 1회 방문했다고 안가는 게 아닌, 
// 밟은 알파벳을 기준으로 하여, 갈수 있는 곳과 없는 곳을 구분
// 그리고 백트랙을 시작시 (퇴각시) 바로 전 탐색부분은 다시 탐색할 수 있기 때문에 false로 변경해주는게 포인트.

