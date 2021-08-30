import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		char[] vowels = new char[] {'a', 'e', 'i', 'o', 'u'};
		char[] limit = new char[] {'d', 'h', 'm', 's'};
		String result = "";
		String s = scan.nextLine(); 
		for (int i = 0; i < s.length(); i++) {
			char current = s.charAt(i);
			if (Arrays.binarySearch(vowels, current) >= 0)
				result += current;
			else {
				int idx = 4;
				for (int j = 0; j < limit.length; j++) {
					if (current < limit[j]) {
						idx = j;
						break;
					}
				}
				result += current;
				result += vowels[idx];
				char c = 0;
				if (Arrays.binarySearch(vowels, ++current) >= 0)
					c = ++current;
				else
					c = current >= 'z' ? 'z' : current;
				
				result += c;
			}
		}
		System.out.println(result);
	}
}
// 2020-09-11 02:00 해결
// 걍 써진거대로 만듦. 이건 영어 해석이 더 힘들었음
