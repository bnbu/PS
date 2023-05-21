import java.util.*;
import java.io.*;
public class I {
	static int n, k;
	static int[] memo;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		
		memo = new int [n + 1];
		for (int i = 0; i <= n; i++) memo[i] = i;
		for (int i = 4; i <= n; i++) {
			memo[i] = Math.min(memo[i], memo[i - 1] + 1);
			if ((i + i/2) > n) break;
			memo[i + i/2] = memo[i] + 1;
		}
				
		for (int i = 1; i <= n; i++) System.out.println(i + " " + memo[i]);
		System.out.println(Math.min(memo[n], memo[n - 1] + 1) <= k && k <= n ? "minigimbob" : "water");
	}
}

// 마지막의 마지막까지 시도하다가 못낸 코드
// 이거 맞는거 같긴 한데

// 1 2 3 4 5까지는 일단 고정적으로 1 2 3 4 5번을 올라가야 하지만

// 6번부터는 얘기가 다르다.

// 6은 5에서도 올 수 있고, 4에서도 올 수 있기 때문에
// 6 : 5, 6

// 7은 6에서도 올 수 있고, 5에서도 올 수 있으므로
// 7 : 6, 7

// 8은 7에서밖에 못오므로
// 8 : 7, 8

// 9는 8이서도 올 수 있고, 6에서도 올 수 있으므로
// 9 : 6, 7, 8, 9 -> 6 ~ 9

// 10은 9에서도 올 수 있고, 7에서도 올 수 있으므로
// 10 : 7, 8, 9, 10 -> 7 ~ 10

// 여기서 발견한 규칙은
// 바로 이전 값의 최저값 혹은 지금 이 값에 2번동작을 통해 도달할 수 있는 지점의 최저값
// 이 둘중 더 낮은 값이 최저값으로써 사용되고, 최대값은 자기 자신이 되어
// 이 구간 내에 k가 존재하면 된다고 생각중

// 1분차이로 제출을 못해봐서 검증은 모름