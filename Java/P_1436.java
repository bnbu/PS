import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		
		int currentNumber = 0;
		int current = 0;
		
		while (current < n) {
			String s = Integer.toString(currentNumber);
			
			if (s.contains("666"))
				current++;
			
			currentNumber++;
		}
		
		System.out.println(currentNumber - 1);
	}
}

// 2020 08 24 04:07 해결
// 0부터 1씩 늘려나가면서, 666을 포함하는 문자열일 경우에 몇번째 숌 숫자인지를 체크
// 원하는 숌 숫자까지 탐색했다면 멈추고, 1 넘어간 상태이므로 1 뺴서 출력
