import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int coinNum = scan.nextInt();
		int cost = scan.nextInt();
		int coinCount = 0;
		int[] coin = new int[coinNum];
		
		for (int i = 0; i < coin.length; i++) {
			coin[i] = scan.nextInt();
		}
		
		for (int i = coin.length - 1; i >= 0; i--) {
			if (cost < coin[i]) {
				continue;
			}
			else {
				coinCount += cost / coin[i];
				cost %= coin[i];
			}
		}
		
		System.out.println(coinCount);
	}
}

// 2020 08 24 02:52 해결
// 큰 값부터 제외시키며, 제외시킨 수를 합산.
