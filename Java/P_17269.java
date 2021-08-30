import java.util.*;
public class Main {
	public static final int[] alphaNum = {3, 2, 1, 2, 4, 3, 1, 3, 1, 1, 3, 1, 3, 2, 1, 2, 2, 2, 1, 2, 1, 1, 1, 2, 2, 1};
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt(), m = scan.nextInt();
		scan.nextLine();
		String[] names = scan.nextLine().split(" ");
		int idx1 = 0, idx2 = 0;
		String s = "";
		while (idx1 + idx2 <= n + m - 1) {
			if (idx1 <= names[0].length() - 1)
				s += names[0].charAt(idx1++);
			if (idx2 <= names[1].length() - 1)
				s += names[1].charAt(idx2++);
		}
		int[] before = new int[n + m], after = new int[n + m - 1];
		for (int i = 0; i < before.length; i++)
			before[i] = alphaNum[s.charAt(i) - 'A'];
		while (true) {
			for (int i = 0; i < before.length - 1; i++) 
				after[i] = (before[i] + before[i + 1]) % 10;
			
			if (after.length == 2)
				break;
			
			before = after;
			after = new int[before.length - 1];
		}
		
		System.out.println((after[0] * 10 + after[1]) + "%");
	}
}
// 2020-09-16 해결
// 간단한 구현문제.
