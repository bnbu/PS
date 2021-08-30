import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		String[] inputs = scan.nextLine().split(" ");
		int n = Integer.parseInt(inputs[0]);
		int m = Integer.parseInt(inputs[1]);
		
		ArrayList<Building> chicken = new ArrayList<>();
		ArrayList<Building> home= new ArrayList<>(); // 치킨집, 집 좌표
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int input = scan.nextInt();
				
				if (input == 2)
					chicken.add(new Building(i, j)); 
				else if (input == 1) 
					home.add(new Building(i, j));
			}
		}
		
		int[][] distance = new int[home.size()][chicken.size()];
		
		for (int i = 0; i < distance.length; i++) {
			for (int j = 0; j < distance[i].length; j++) {
				distance[i][j] = (Math.abs(home.get(i).x - chicken.get(j).x)) + 
								(Math.abs(home.get(i).y - chicken.get(j).y));
			}
		} // 각 집으로부터 각 치킨집까지의 거리를 계산
		
		int[] picked = new int[m]; // 폐업시키지 않을 치킨집의 index를 저장할 곳
		for (int i = 0; i < picked.length; i++) {
			picked[i] = (chicken.size() - picked.length) + i;
		}
		
//		for (int[] i : distance) {
//			for (int j : i) {
//				System.out.print(j + " ");
//			}
//			System.out.println();
//		}
		
		int min = Integer.MAX_VALUE;
		while (true) {
			int[] leastDistance = new int[home.size()]; // 한 집에서 폐업시키지 않은 치킨잡까지의 계산된 거리중 가장 짧은 거리들
			
			for (int i = 0; i < leastDistance.length; i++) {
				leastDistance[i] = distance[i][picked[0]];
			}
			
			for (int i = 0; i < leastDistance.length; i++) {
				for (int j = 0; j < picked.length; j++) {
					leastDistance[i] = leastDistance[i] < distance[i][picked[j]] ? 
							leastDistance[i] : distance[i][picked[j]];
				}
			}
			
			int distanceSum = 0;
			for (int i : leastDistance) {
				distanceSum += i;
			}
			min = min > distanceSum ? distanceSum : min; // 계산된 거리 합산하여 가장 작은 '치킨거리' 를 구함
					
			boolean check = true;
			for (int i = 0; i < picked.length - 1; i++) {
				if (picked[i] + 1 != picked[i + 1]) {
					check = false;
					break;
				}
			}
			
			if (check && picked[0] == 0) {
				break;
			}
			else if (check && picked[0] != 0) {
				picked[0] -= 1;
				for (int i = 1; i < picked.length; i++) {
					picked[i] = (chicken.size() - picked.length) + i;
				}
			}
			else {
				int index = picked.length - 1;
				for (int i = picked.length - 1; i > 0; i--) {
					if (picked[i] - 1 != picked[i - 1]) {
						index = i;
						break;
					}
				}
				picked[index] -= 1;
				for (int i = index + 1; i < picked.length; i++) {
					picked[i] = (chicken.size() - picked.length) + i;
				}
			}
		}
		System.out.println(min);
	}
}

class Building {
	public int x;
	public int y;
	public Building (int x, int y) {
		this.x = x;
		this.y = y;
	}
}
// 2020 08 10 19:52 해결
