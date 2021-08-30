import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		int[][] checkChar = new int[2][26];
		String first = scan.nextLine();
		for (int i = 0; i < first.length(); i++)
			checkChar[0][first.charAt(i) - 'a']++;

		String second = scan.nextLine();
		for (int i = 0; i < second.length(); i++) {
			char c = second.charAt(i);
			if (c == '*')
				continue;
			else
				checkChar[1][c - 'a']++;
		}
		
		boolean check = true;
		for (int i = 0; i < 26; i++) {
			if (checkChar[0][i] < checkChar[1][i]) {
				check = false;
				break;
			}
		}
		
		if (check && first.length() == second.length()) 
			sb.append("A\n");
		else
			sb.append("N\n");
		
		System.out.println(sb);
	}
}
