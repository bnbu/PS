import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class B {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int log;
		if (n > 0)
			log = (int) (Math.log(n) / Math.log(2)) + 1;
		else if (n < 0)
			log = 32;
		else
			log = 1;
		
		System.out.println(log);
	}
}
// 필요한 비트 수 = 밑이 2인 로그를 취한 값을 통해 알아올 수 있다
// 아니면 단적으로 그냥 2진수 문자열로 변환했었더라면
