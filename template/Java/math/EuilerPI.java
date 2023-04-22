package D0411;

import java.util.*;
import java.io.*;
public class EuilerPI {
	public static HashSet<Long> primes;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		primes = new HashSet<>();
		long n = Long.parseLong(br.readLine()),
			 k = 2;
		double d = Math.sqrt(n);
		
		long temp = n;
		while (k <= d && temp != 1) {
			if (temp % k == 0) {
				primes.add(k);
				temp /= k;
			}
			else k++;
		}
		
		if (temp > 1) primes.add(temp);
		
		for (long l : primes)
			n = n / l * (l-1);
		
		System.out.println(n);
	}
}
// n = p1^k1 + p2^k2 + p3^k3 + ...
// 꼴로 소인수 분해가 될 떄,
// Pi(n) = n * (1 - 1/p1) * (1 - 1/p2) * ... 꼴로 계산

// 소인수 분해를 일단은 O(sqrt(n)) 시간대로 사용했음