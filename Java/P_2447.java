import java.util.*;
public class Main {
	public static int[] range = new int[2];
	public static char[][] stars;
	public static StringBuilder sb = new StringBuilder();
	public static void main(String[] args)  {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		stars = new char[n + 1][n + 1];
		//setStar(n, 1, 1);
		set(n, 1, 1);
		for (int i = 1; i < stars.length; i++) {
			for (int j = 1; j < stars[i].length; j++)
				sb.append(stars[i][j]);
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}
	public static void setStar(int n, int x, int y) {
		int temp = n;
		while (temp >= 1) {
			range[0] = temp / 3 + 1;
			range[1] = 2 * temp / 3;
			
			if (temp == 1) {
				stars[y][x] = '*';
				break;
			}
			else {
				if ((x % temp >= range[0] && x % temp <= range[1]) && (y % temp >= range[0] && y % temp <= range[1])) {
					stars[y][x] = ' ';
					break;
				}
			}
			temp /= 3;
		}
		
		if (x == n) {
			if (y == n)
				return;
			else 
				setStar(n, 1, y + 1);
		}
		else 
			setStar(n, x + 1, y);
	}
	
	public static void set(int n, int x, int y) {
		if (n == 1) {
			stars[y][x] = '*';
			return;
		}
		else {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (i == 1 && j == 1)
						continue;
					set(n / 3, x + (n / 3 * i), y + (n / 3 * j));
				}
			}
		}
	} // => 이게 바로 분할정복 방식. 한번 알아볼 것.
}

// 2020-08-31 02:04 해결
// 하지만.. 메모리가 장난이 아니고 무엇보다 실제 컴파일시 n=243부터 오류가 남...
// 어째서 정답처리가 된것일까
// 분할 정복으로 풀라는데?
// 가운데를 제외한 나머지 8군데에 같은 패턴이 나타나니까, 각각을 담당하도록 하는 재귀 호출들을 하게끔 함수를 작성하는 것이 핵심
