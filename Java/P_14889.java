import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int num = scan.nextInt();
		
		int[] link = new int[num / 2];            
		int[][] status = new int[num][num];
		int min = Integer.MAX_VALUE;
		
		for (int i = 0; i < link.length; i++) {
			link[i] = num / 2 + i; // index 기준으로 0부터 시작함.
		}
		
		for (int i = 0; i < status.length; i++) {
			for (int j = 0; j < status[i].length; j++) {
				status[i][j] = scan.nextInt();
			}
		}
		
		while (true) {
			int[] duo = new int[2];
			int[] start = new int[num / 2];
			for (int i = 0; i < duo.length; i++) {
				duo[i] = (link.length - duo.length) + i;
			}
			int indexNum = 0;
			for (int i = 0; i < num; i++) {

				boolean overlapped = false;
				for (int j = 0; j < link.length; j++) {
					if (i == link[j]) {
						overlapped = true;
						break;
					}
				}
				
				if (overlapped) {
					continue;
				}
				start[indexNum++] = i;
			}
			int linkSum = 0;
			int startSum = 0;
			while (true) {
				for (int i = 0; i < duo.length; i++) {
					linkSum += status[link[duo[0]]][link[duo[1]]] + status[link[duo[1]]][link[duo[0]]];
					startSum += status[start[duo[0]]][start[duo[1]]] + status[start[duo[1]]][start[duo[0]]];
				}
//				System.out.print(status[link[duo[0]]][link[duo[1]]] + " + " + status[link[duo[1]]][link[duo[0]]] + " = " + linkSum + "\n");
//				System.out.print(status[start[duo[0]]][start[duo[1]]] + " + " + status[start[duo[1]]][start[duo[0]]] + " = " + startSum + "\n");
//				
//				for (int i = 0; i < duo.length; i++) {
//					System.out.print(duo[i] + " ");
//				}
//				System.out.println();
				
				if ((duo[0] + 1 == duo[1]) && duo[0] == 0) {
					break;
				}
				else if ((duo[0] + 1 == duo[1]) && duo[0] != 0) {
					duo[0] -= 1;
					duo[1] = link.length - duo.length + 1;
				}
				else {
					duo[1] -= 1;
				}
			}
//			System.out.println(linkSum + " " + startSum);
			min = min <= (Math.abs(linkSum - startSum)) ? min : (Math.abs(linkSum - startSum));
			
			// 대충 해당 팀으로 능력치 합산 구해서 최솟값 구하는 구간
//			for (int i : link) {
//				System.out.print(i + " ");
//			}
//			System.out.println();
//			for (int i : start) {
//				System.out.print(i + " ");
//			}
//			System.out.println();
//			System.out.println("==============");
			
			boolean check = true;
			for (int i = 0; i < link.length - 1; i++) {
				if (link[i] + 1 != link[i + 1]) {
					check = false;
					break;
				}
			}
			
			if (check && link[0] == 0) {
				break;
			}
			else if (check && link[0] != 0) {
				link[0] -= 1;
				for (int i = 1; i < link.length; i++) {
					link[i] = num / 2 + i;
				}
			}
			else {
				int index = link.length - 1;
				for (int i = link.length - 1; i > 0; i--) {
					if (link[i] - 1 != link[i - 1]) {
						index = i;
						break;
					}
				}
				
				link[index] -= 1;
				for (int i = index + 1; i < link.length; i++) {
					link[i] = num / 2 + i;
				}
			}
		}
		System.out.println(min / 2);
		// 합산시, 2번씩 중복계산하기 때문에 2로 나눠줌
	}
}
// 2020 08 10 16:40 해결


