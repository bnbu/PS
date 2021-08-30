import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		
		char last = 0;
		int maxScore = 0;
		int currentScore = 0;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '(' || input.charAt(i) == '{' || input.charAt(i) == '[') {
				last = input.charAt(i);
				
				if (last == '(')
					currentScore += 1;
				else if (last == '{')
					currentScore += 2;
				else
					currentScore += 3;
			}
			else if (input.charAt(i) == ')') {
				if (last == '(') {
					if (input.charAt(i - 1) != last)
						maxScore = maxScore < currentScore ? currentScore : maxScore;
				}
				currentScore -= 1;

			}
			else if (input.charAt(i) == '}') {
				if (last == '{') {
					if (input.charAt(i - 1) != last)
						maxScore = maxScore < currentScore ? currentScore : maxScore;
				}
				currentScore -= 2;
			}
			else if (input.charAt(i) == ']') {
				if (last == '[') {
					if (input.charAt(i - 1) != last)
						maxScore = maxScore < currentScore ? currentScore : maxScore;
				}
				currentScore -= 3;
			}
//			System.out.println(maxScore + " " + currentScore + " " + input.charAt(i) + " " + last);
		}
		System.out.println(maxScore);
	}
}
// 2020-09-04 00:46 해결
// 최대값 비교 조건을 바로 직전에 여는 괄호가 있는지로 판단.
