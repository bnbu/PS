import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Stack<Character> stack = new Stack<>();
		String input = scan.nextLine();
		char last = 0;
		boolean ppap = true;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == 'A' && i != input.length() - 1 && last != 'A' && stack.size() >= 2) {
				stack.pop();
				stack.pop();
			}
			else {
				if (input.charAt(i) == 'A') {
					ppap = false;
					break;
				}
				stack.push(input.charAt(i));
			};
			last = input.charAt(i);
		}
		if (stack.size() != 1)
			ppap = false;
		System.out.println(ppap ? "PPAP" : "NP");
	}
}
// 2020-09-04 01:47 해결
// A가 나오면, PP를 2개 삭제
// 결과적으로 PPAP -> P로 압축이 된다.
// 여기서 주의점이 A가 나올때, A로 끝나지 않아야 하며, 동시에 A는 연속되지 않아야 하고, 무엇보다 A가 나올 시점에 스택의 크기가 2 이상이어야 함.
// 연산의 결과 마지막으로 남은게 P. 즉 PPAP 문자열의 시작인 P로 되돌아가진다 (여기서 A는 절대 스택에 들어가지 않음)
// 그러면 PPAP 문자열이고 아니면, PPAP 문자열이 아니다.
