import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		int x = scan.nextInt(), y = scan.nextInt(), m = scan.nextInt();
		int[] enemy = new int[x], heal = new int[y];
		
		for (int i = 0; i < x; i++)
			enemy[i] = scan.nextInt();
		for (int i = 0; i < y; i++)
			heal[i] = scan.nextInt();
		
		int enemyIndex= 0;
		int healIndex = 0;
		while (true) {
			if (enemyIndex < x) {
				if (m - enemy[enemyIndex] > 0) {
					m -= enemy[enemyIndex++];
					sb.append("-" + enemyIndex + "\n");
				} 
				else {
					if (healIndex < y) {
						m += heal[healIndex++];
						sb.append(healIndex + "\n");
					} 
					else {
						sb = new StringBuilder();
						sb.append(0 + "\n");
						break;
					}
				}
			}
			else {
				if (healIndex < y) {
					m = m + heal[healIndex++] > 10 ? 10 : m + heal[healIndex++];
					sb.append(healIndex + "\n");
				}
				else
					break;
			}
		}
		System.out.println(sb);
	}
}
// 2020-09-07 18:47 해결
// 생각보다는 간단했다. 내가 이정도만 고민하고 한걸 보니 이건 실버티어다.
