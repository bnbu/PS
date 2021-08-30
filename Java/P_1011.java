import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int x = scan.nextInt();
			int y = scan.nextInt();
			
			boolean repeat = true;
			int moveCount = 0;
			long distance = y - x;
			long limit = 0;
			long j = 1; // 계산 과정중 int형의 범위를 초과하기 떄문. 값들 사이 비교 및 연산을 위해 모두 long형을 이용
			
			while (repeat) {
				
				for (int k = 0; k < 2; k++) {
					if (distance <= limit) {
						repeat = false;
						continue;
					}
					limit += j;
					moveCount++;
				}
				
				j++;
			}
			
			sb.append(moveCount + "\n");
		}
		System.out.println(sb.toString());
	}
}
// 2020-08-30 01:10 해결
// 계산해본 결과 이동 횟수는 
// 거리 1,1,2,2,3,3,4,4,5,5 ... ,i,i.. 마다 1씩 증가함을 관찰할 수 있었다.
