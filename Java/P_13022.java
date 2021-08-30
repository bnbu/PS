import java.util.*;
public class Main {
	public static char[] wolf = {'w', 'o', 'l', 'f'};
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		ArrayList<Integer> wIndex = new ArrayList<>();
		ArrayList<Integer> wNum = new ArrayList<>();
		ArrayList<String> str = new ArrayList<>();
		for (int i = 0; i < input.length(); i++) 
			if (input.charAt(i) == wolf[0])
				wIndex.add(i);
		
		for (int i = 0; i < wIndex.size(); i++) {
			int cnt = 0;
			if (i != 0 && (wIndex.get(i - 1) + 1 == wIndex.get(i)))
				continue;
			else {
				for (int j = wIndex.get(i); j < input.length() - 1; j++) {
					if (input.charAt(j) == wolf[0])
						cnt++;
					else {
						wNum.add(cnt);
						break;
					}
				}
			}
		}
		
		String[] splited = input.split("w");
		for (String s : splited)
			if (s.length() > 0)
				str.add(s);
		
		int[] partLength = new int[wNum.size()];
		int idx = 0;
		for (int i = 0; i < partLength.length - 1; i++) {
			partLength[i] = wIndex.get(idx + wNum.get(i)) - wIndex.get(idx);
			idx += wNum.get(i);
		}
		if (partLength.length != 0)
			partLength[partLength.length - 1] = input.length() - wIndex.get(idx);
		
		boolean pass = false;
		for (int i = 0; i < partLength.length; i++) 
			if (partLength[i] != 4 * wNum.get(i)) {
				pass = true;
				break;
			}
		
		boolean check = true;
		if (partLength.length > 0 && partLength.length == str.size() && !pass) {
			idx = 0;
			for (int i = 0; i < partLength.length; i++) {
				int num = 0;
				for (int j = idx; j < idx + partLength[i]; j++) {
					if (input.charAt(j) != wolf[num++ / wNum.get(i)]) {
						check = false;
						break;
					}
				}
				idx += partLength[i];
			}
		}
		else
			check = false;
		System.out.println(check ? 1 : 0);
	}
}
// 2020-09-03 01:55 해결
// 아 쉬발 존나 어렵네;
// w의 인덱스들을 먼저 알아낸 후 인덱스의 연속으로부터 각 인덱스로부터 몇글자의 w가 있는지 체크
// 그리고 w로부터 다음 w까지 그 사이에 몇글자가 있는지도 따로 체크
// 그 다음 입력받은 한줄을 w로 스플릿 시킨 다음 길이가 0보다 큰 것들을 str 배열에 추가
// 체크한 값의 배열의 길이와 str의 배열 길이가 서로 맞지 않으면 w부터 시작한 문자열이 아니므로 바로 0 처리
// 그리고 w의 연속된 수 * 4 와 각 체크한 값이 서로 갖지가 않다면, 검사할 가치가 없는 문자열이므로 0 처리
// -> 적어도 같아야, 4글자 혹은 8글자가 wolf인지 wwoollff인지 검사를 해볼만 하지, 7글자, 11글자 이러면 검사할 필요가 없다.
// 마지막으로 길이가 같다면, w, o, l, f 하나씩 검사해서 다른 부분이 있다면 바로 0처리.

