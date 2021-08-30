import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		char[] arr = scan.nextLine().toCharArray();
		Arrays.sort(arr);
		TreeMap<Character, Integer> map = new TreeMap<>();
		
		for (char c : arr) {
			if (map.containsKey(c))
				map.put(c, map.get(c) + 1);
			else
				map.put(c, 1);
		}
		
		ArrayList<Character> keys = new ArrayList<>(map.keySet());
		ArrayList<Integer> values = new ArrayList<>(map.values());
		ArrayList<Integer> oddIndex = new ArrayList<>();
		
		int oddCount = 0;
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i) % 2 == 0)
				continue;
			else {
				oddCount++;
				oddIndex.add(i);
			}
		}
		
		if (oddCount < 2) {
			for (int i = 0; i < keys.size(); i++) {
				for (int j = 0; j < values.get(i) / 2; j++)
					sb.append(keys.get(i));
			}
			for (int i = 0; i < oddCount; i++) {
				for (int j = 0; j < values.get(oddIndex.get(i)) % 2; j++)
					sb.append(keys.get(oddIndex.get(i)));
			}

			for (int i = keys.size() - 1; i >= 0; i--) {
				for (int j = 0; j < values.get(i) / 2; j++)
					sb.append(keys.get(i));
			}

			System.out.println(sb);
		}
		else {
			System.out.println("I'm Sorry Hansoo");
		}
	}
}
// 2020-09-07 01:49 해결
// 좀 추잡하긴 한데, 정렬 한 다음  Map 구조로, 어느 글자가 몇개 있는지 탐색
// 만약 홀수 인 수가 1개 초과시 팰린드롬 문자열은 못만듦
// 1개 이하면 우선 value값 처음부분부터 2로 나눈 수만큼 해당 index의 문자를 출력해 나간다
// 끝난다면 oddIndex의 문자를 1개 출력한다. 사실 저기처럼 반복문 쓸 필요 없음
// 그리고 다시 이번엔 뒤부터 출력해 나간다.
