import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int treeNum = scan.nextInt();
		int[] trees = new int[treeNum];
		int[] distance = new int[treeNum - 1];
		int gcd;
		
		int answer = 0;
		
		for (int i = 0; i < trees.length; i++) {
			trees[i] = scan.nextInt();
		}
		
		for (int i = 0; i < distance.length; i++) {
			distance[i] = trees[i] - trees[i + 1];
		}
		
		gcd = distance[0];
		for (int i = 1; i < distance.length; i++) {
			gcd = getGCD(gcd, distance[i]);
		}
		
		for (int i = 0; i < distance.length; i++) {
			answer += (distance[i] / gcd) - 1;
		}
		
		System.out.println(answer);
	}
	public static int getGCD(int a, int b) {
		if (a % b == 0) {
			return b;
		}
		else {
			int temp = b;
			b = a % b;
			a = temp;
			
			return getGCD(a, b);
		}
	}
}

// 2020 08 24 02:31 해결
// 가로수의 위치들에서 각 가로수간 거리를 알아내고
// 가로수간 거리들의 최대공약수로 각 거리를 나눈 몫에 1을 빼면 사이에 필요한 나무의 갯수임을 이용.
// 여기서 최대공약수인 이유는, 각 가로수들의 간격이 같아야 하는 점에서 거리들의 공약수가 필요하고
// 그리고 최소로 필요한 점에서 최대공약수가 필요하기 때문.
