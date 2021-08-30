import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		String s = scan.nextLine();
		Stack<Character> oper = new Stack<>();
		
		for (int i = 0; i < s.length(); i++) {
			char curr = s.charAt(i);
			if (curr >= 65 && curr <= 90)
				sb.append(curr);
			else if (curr >= 42 && curr <= 47) {
				if (oper.empty() || oper.peek() == '(')
					oper.push(curr);
				else {
					if (!oper.contains('(')) {
						if (oper.peek() == '*' || oper.peek() == '/') {
							if (curr == '*' || curr == '/') {
								sb.append(oper.pop());
								oper.push(curr);
							}
							else {
								while (oper.size() > 0)
									sb.append(oper.pop());
								oper.push(curr);
							}
						}
						else {
							if (curr == '+' || curr == '-') {
								while (oper.size() > 0)
									sb.append(oper.pop());
								oper.push(curr);
							}
							else
								oper.push(curr);
						}
					}
					else {
						if (oper.peek() == '*' || oper.peek() == '/') {
							if (curr == '*' || curr == '/') {
						sb.append(oper.pop());
								oper.push(curr);
							}
							else {
								while (oper.peek() != '(')
									sb.append(oper.pop());
								oper.push(curr);
							}
						}
						else {
							if (curr == '+' || curr == '-') {
								while (oper.peek() != '(')
									sb.append(oper.pop());
								oper.push(curr);
							}
							else
								oper.push(curr);
						}
					}
				}
			}
			else if (curr == '(')
				oper.push('(');
			else if (curr == ')') {
				while (oper.peek() != '(')
					sb.append(oper.pop());
				oper.pop();
			}
		}
		while (oper.size() > 0)
			sb.append(oper.pop());
		System.out.println(sb);
	}
}
// 2020-09-27 00:23 해결
// 뭐가 문제였나?
