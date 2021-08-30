import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		ArrayList<Integer> positive = new ArrayList<>(), negative = new ArrayList<>();
		while (n-- > 0) {
			int temp = scan.nextInt();
			if (temp == 0) {
				positive.add(0);
				negative.add(0);
			}
			else if (temp > 0)
				positive.add(temp);
			else
				negative.add(temp);
		}
		Collections.sort(positive);
		Collections.sort(negative);
		int result = 0;
		for (int i = positive.size() - 1; i >= 0; i--) {
			if (i > 0) {
				int m = positive.get(i) * positive.get(i - 1);
				if (m > positive.get(i)) {
					result += m;
					i--;
				} else
					result += positive.get(i);
			}
			else
				result += positive.get(i);
		}
		for (int i = 0; i < negative.size(); i++) {
			if (i < negative.size() - 1) {
				int m = negative.get(i) * negative.get(i + 1);
				if (m > negative.get(i)) {
					result += m;
					i++;
				} else
					result += negative.get(i);
			}
			else
				result += negative.get(i);
		}
		System.out.println(result);
	}
}
// 무슨 반례가 있지?
// 음수와 양수를 따로 받는 방법도 고려해보자.
// 2020-09-18 해결
// 음수와 양수의 구분점을 찾기보단, 처음부터 음수와 양수를 구분해서 받는게
// 더 좋은 방법인 듯 하다.
