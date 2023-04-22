import java.util.*;
import java.io.*;
public class Sieve_of_Eratosthenes {
	public static void main(String[] args) {
		int[] primes = new int[21001];
		for (int i = 2; i <= 21000; i++)
			primes[i] = i;
		
		for (int i = 2; i <= 21000; i++) {
			if (primes[i] == 0)
				continue;
			for (int j = i + i; j <= 21000; j += i)
				primes[j] = 0;
		}
		
		for (int i = 0; i < primes.length; i++)
			if (primes[i] != 0)
				System.out.print(primes[i] + " ");
	}
}
