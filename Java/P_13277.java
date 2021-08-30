import java.util.*;
import java.math.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String[] s = scan.nextLine().split(" ");
		BigInteger n1 = new BigInteger(s[0]), n2 = new BigInteger(s[1]);
		System.out.println(n1.multiply(n2).toString());
	}
}
