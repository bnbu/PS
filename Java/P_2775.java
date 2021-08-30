import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		int t = scan.nextInt();
		
		for (int i = 0; i < t; i++) {
			int k = scan.nextInt();
			int n = scan.nextInt();
			
			int[][] population = new int[k + 1][n];
			for (int j = 0; j < population[0].length; j++)
				population[0][j] = j + 1;
			for (int j = 1; j < population.length; j++) {
				for (int l = 0; l < population[j].length; l++) {
					for (int m = 0; m <= l; m++) {
						population[j][l] += population[j - 1][m];
					}
				}
			}
			sb.append(population[k][n - 1] + "\n");
		}
		System.out.println(sb.toString());
	}
}
// 2020-08-30 01:41 해결
// 어.. 메모리나 시간중 하나 초과가 날거라 생각했지만 예상을 깨고 해결됨.
// 근본있게 그냥 0층 배열부터 시작해서 n층까지 계산하여 구한 후 해당 값을 출력시킴.
