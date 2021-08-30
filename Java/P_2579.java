import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int[] stair = new int[n];
		int[] memoization = new int[n];
		for (int i = 0; i < n; i++)
			stair[i] = scan.nextInt();
		
		switch (n) {
		default:
			memoization[2] = stair[0] + stair[2] < stair[1] + stair[2] ? stair[1] + stair[2] : stair[0] + stair[2];
		case 2:
			memoization[1] = stair[0] + stair[1];
		case 1:
			memoization[0] = stair[0];
		}
		
		for (int i = 3; i < n; i++)
			memoization[i] += memoization[i - 3] + stair[i - 1] + stair[i] < memoization[i - 2] + stair[i] ?
					memoization[i - 2] + stair[i] : memoization[i - 3] + stair[i - 1] + stair[i];
		
		System.out.println(memoization[n - 1]);
	}
}
// 2020-09-09 21:00 해결
// 1층 : 1층밖에 없음
// 2층 : 1, 2층 밟는게 제일 큼
// 3층 : (1,3) / (2,3) 층 중 큰거
// 4층 : (2,4) / (1,3,4) 층 중 큰거, 이때 2->4층은 어차피 2칸차이라 아무 상관없이 젤 큰거 오면 됨. 마찬가지로 1->3도 같은 논리
// 5층 : (3,5) / (2,4,5) 층 중 큰거, 마찬가지로 3->5층은 2칸 차이므로 3층중 젤 큰거 오면 됨, 2->4도 마찬가지
// 이를 n층까지 생각해본다면?
// n층 : (n-2, n) / (n-3, n-1, n) 층 중 큰거
// (n-2)->n 도 2칸차이니까 n-2층까지 오는 방법중 제일 큰거로 한다
// 마찬가지로 (n-3)->(n-1)층도 2칸차이라 n-3층까지 오는 방법중 제일 큰거로 한다. 이를 반복하여 마지막층까지 계산을 반복한다.
