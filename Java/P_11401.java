import java.util.*;
public class Main {
	public static int div = 1000000007;
	public static long memo[] = new long[4000001];
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt(), k = scan.nextInt();
		memo[0] = 1;
		for (int i = 1; i <= 4000000; i++) {
			memo[i] = (i * memo[i - 1] % div) % div;
		}
		long result = ((memo[n] % div) * 
				(pow((memo[k] % div * memo[n - k] % div), div - 2) % div)) % div;
		System.out.println(result);
		
	}
	public static long pow(long a, long b) {
		if (b == 1)
			return a % div;
		
		if (b % 2 == 0) {
			long temp = pow(a, b / 2);
			return (temp % div * temp % div) % div;
		}
		else
			return (a % div) * (pow(a, b - 1) % div) % div;
	}
}
// div = 1,000,000,007 이때, 이건 소수임.
// nCk = n! / k!(n-k)! 인데
// 나눗셈은 % 연산이 불가능
// 이때 페르마의 소정리를 생각해보면 a^p-1 은 법 p에 대해 1과 합동인데,
// a^p-2 는 법 p에 대해 a^-1과 합동이게 된다.
// 여기서 a를 k!(n-k)!이라 두면, a^-1은 법 p에 대해 a^p-2와 합동이게 된다.
// 즉 (k!(n-k)!)^1000000005 % 1000000007 로 바꿔 계산이 가능하게 됨.
// 다시, ((n! % div) * ((k!(n-k)!)^(div-2) % div) % div 이게 됨.
