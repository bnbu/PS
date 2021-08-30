import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int length = Integer.parseInt(scan.nextLine());
		
		String currentString = "";
		int currentLength = 0;
		
		String errorString = "";
		boolean error = false;
		
		while (currentLength <= length - 1) {
			String[] strings = new String[3];
			boolean[] isGood = new boolean[3];
			boolean check = true;
			
			if (!error) {
				for (int i = 0; i < 3; i++) {
					strings[i] = currentString + (i + 1);
					isGood[i] = stringTest(strings[i]);
				}

				for (int i = 0; i < strings.length; i++) {
					if (isGood[i]) {
						currentString = strings[i];
						check = false;
						break;
					}
				}
			}
			else {
//				System.out.println("length: " + currentLength);
				for (int i = 0; i < 3; i++) {
					strings[i] = currentString + (i + 1);
					isGood[i] = stringTest(strings[i]);
//					System.out.println(i + ": " + strings[i] + "/" + isGood[i]);
				}

				for (int i = 0; i < strings.length; i++) {
					if (isGood[i] && !(strings[i].equals(errorString))) {
						currentString = strings[i];
						check = false;
						error = false;
						break;
					}
				}
			}
			
			if (!check) {
				currentLength++;
			}
			else {
				errorString = currentString;
				currentString = currentString.substring(0, currentString.length() - 1);
				error = true;
				
//				System.out.println(currentString);
//				System.out.println(errorString);
				currentLength--;
			}
//			System.out.println("currentString: " + currentString);
		}
		System.out.println(currentString);
	}
	
	public static boolean stringTest(String s) {
		int testLength = 1;
		boolean testResult = true;
		
		while (testLength <= s.length() / 2) {
			for (int i = 0; i < s.length() - (testLength * 2) + 1; i++) {
				String front = s.substring(i, i + testLength);
				String back = s.substring(i + testLength, i + testLength + testLength);
				
				if (front.equals(back)) {
					testResult = false;
				}
			}
			testLength++;
			if (!testResult)
				break;
		}
		
		return testResult;
	}
}
// 2020 08 23 03:14 해결
// 길이 0부터 시작해서 끝에 1,2,3 을 추가한 문자열을 좋은수열 판별을 하여 좋은수열인 것부터 채워 나간다.
// 이후 문제가 생기는 부분에서는 문제가 생기는 부분의 문자열을 저장하고
// 그 이전 문자열로 한칸 되돌아간 후 문제가 생기는 문자열 이외의 만족하는 문자열을 찾을 때 까지 반복해나간다.
// 지정한 길이에 도달하면 멈춘다.
