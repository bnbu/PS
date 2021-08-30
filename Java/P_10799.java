import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		Stack<Character> s = new Stack<>();
		
		int sum = 0;
		char last = 0;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '(')
				s.push('(');
			else if (input.charAt(i) == ')' && last == '(') {
				s.pop();
				sum += s.size();
			}
			else if (input.charAt(i) == ')' && last == ')') {
				s.pop();
				sum += 1;
			}
			last = input.charAt(i);
		}
		
		System.out.println(sum);
	}
}
