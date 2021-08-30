import java.util.*;
public class Main {
	public static HashMap<Long, double[]> map = new HashMap<>();
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		long n = scan.nextLong(); 
		int m = scan.nextInt(), k = scan.nextInt();
		double result = getPercentage(n, m, k);
		System.out.printf("%.16f", result);
	}
	public static double getPercentage(long n, int m, int k) {
		if (n == 0)
			return 0;
		
		if (k == 1) {
			if (n >= 2)
				return 1;
			else if (n == 1)
				return (double)(m - n) / m;
		}
		
		if (map.get(n) != null) {
			if (map.get(n)[k] != 0)
				return map.get(n)[k];
		}

		long[] temp = new long[m];
		long left = n;
		for (int i = 0; i < m; i++) {
			if (n > m) {
				if (i != m - 1) {
					temp[i] = n / m;
					left -= n / m;
				} else
					temp[i] = left;
			}
			else {
				if (left != 0) {
					temp[i] = 1;
					left--;
				} else
					temp[i] = 0;
			}
		}
		double result = 0;
//		for (long i : temp) {
//			result += getPercentage(n - i, m, k - 1) / m;
//		} ==> temp가 균일하게 분포시킨 갯수인데 일단 오차가 생김. 다시 생각해볼 것.
		result += (double)(n % m) / m * getPercentage(n - (n / m + 1), m, k - 1) + 
				(double)(m - n % m) / m * getPercentage(n - (n / m), m, k - 1);
		if (map.get(n) == null) {
			double[] d = new double[51];
			d[k] = result;
			map.put(n, d);
		}
		else {
			double[] d = map.get(n);
			d[k] = result;
			map.put(n, d);
		}
		
		return map.get(n)[k];
	}
}

// 뭐가 틀릴까요??
// 16 4 15 이때 오차가 좀 큼

//dp[0][K] = 0
//dp[1][1] = (M-N)/M
//dp[N][1] = 1 (단, N>=2)
//
//dp[N][K] = ((M-N%M)/M * dp[N-floor(N/M)][K-1]) + ((N%M)/M) * dp[N-floor(N/M)-1][K-1]

// 점화식 유도 해보기
