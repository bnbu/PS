import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String[] temp = scan.nextLine().split("-");
		int result = 0, sum = 0;
		for (int i = 0; i < temp.length; i++) {
			String[] num = temp[i].split("\\+");
			sum = 0;
			for (int j = 0; j < num.length; j++) 
				sum += Integer.parseInt(num[j]);
			if (i == 0)
				result = sum;
			else
				result -= sum;
		}
		
		System.out.println(result);
	}
}
// 2020-09-24 01:36 해결
// 어.. 그리디일까 이게?
// -인 부분으로 모두 쪼개서 이를 더한 뒤 빼는 경우로 생각 한 것.
