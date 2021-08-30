import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int g1 = 0, g2 = 0;
			ArrayList<Integer> primes = getPrime(n);
			
			int index = 0;
			while(true) {
				if (primes.get(index) > n / 2)
					break;
				
				int temp = n - primes.get(index);
				if (primes.contains(temp)) {
					if (g1 == 0 && g2 == 0) {
						g1 = primes.get(index);
						g2 = temp;
					}
					else {
						if ((temp - primes.get(index)) <= (g2 - g1)) {
							g1 = primes.get(index);
							g2 = temp;
						}	
					}
				}
				
				index++;
			}
			System.out.println(g1 + " " + g2);
		}
	}
	
	public static ArrayList<Integer> getPrime(int n) {
		ArrayList<Integer> arr = new ArrayList<>();
		boolean[] check = new boolean[n + 1];
		
		for (int i = 2; i < check.length; i++)
			check[i] = true;
		
		for (int i = 2; i < check.length; i++) {
			if (check[i])
				for (int j = i * 2; j < check.length; j += i) 
					check[j] = false;
		}
		
		for (int i = 2; i < check.length; i++)
			if (check[i])
				arr.add(i);
		
		return arr;
	}
}
// 2020-08-30 해결
// 소수를 구해서 절반까지만 연산을 진행하여 g1과 g2를 찾음
