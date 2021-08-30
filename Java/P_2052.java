import java.util.*;
import java.math.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		BigDecimal r = new BigDecimal("0.5");
		System.out.println(r.pow(n).toPlainString());
	}
}
