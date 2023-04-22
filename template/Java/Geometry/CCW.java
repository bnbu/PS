import java.util.*;
import java.io.*;	
public class CCW {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int[] x, y;
		x = new int[3];
		y = new int[3];
		
		for (int i = 0; i < 3; i++) {
			st = new StringTokenizer(br.readLine());
			x[i] = Integer.parseInt(st.nextToken());
			y[i] = Integer.parseInt(st.nextToken());
		}
		
		System.out.println(ccw(x, y));
	}
	public static int ccw(int[] x, int[] y) {
		return (x[0]*y[1] + x[1]*y[2] + x[2]*y[0])
				- (y[0]*x[1] + y[1]*x[2] + y[2]*x[0]);
	}
}
// 두 백터의 내적으로 계산
// 2차원 평면 상 3점의 x,y좌표로부터 ccw는 다음과 같이 구해진다
// s = (p1.x * p2.y + p2.x * p3.y + p3.x * p1.y) -
//		(p1.y * p2.x + p2.y * p3.x + p3.y * p1.x)
// s < 0 이면, p1->p2 벡터에 대해 p3은 오른쪽에 존재. (즉 시계방향)
// s == 0 이면 일직선.
// s > 0 이면, p1->p2 벡터에 대해 p3은 왼쪽에 존재. (즉 반시계방향)
//

// s는 두 벡터의 외적으로
// CCW의 결과인 s는 사실상 p1->p2 와 p2->p3 벡터의 외적의 크기에 해당하며
// 벡터 a와 b의 외적의 크기값은
// a x b = |a||b|*sin(t) **(a : p1->p2 / b : p2->p3 )
// => (0, 0, axby - aybx) 가 성립
// axby => (p2x - p1x)(p3y - p2y) - (p2y - p1y)(p3x - p2x)가 된다.
// 따라서, 이를 전개하여 게산하면
// s = p2x*p3y + p1x*p2y - p1x*p3y - p2x*p2y
//		- (p2y*p3x + p1y*p2x - p2y*p2x - p1y*p3x)  => p2x*p2y 끼리 소거됨
// 정리하면
// s = (p1.x * p2.y + p2.x * p3.y + p3.x * p1.y) -
//			(p1.y * p2.x + p2.y * p3.x + p3.y * p1.x)

// 이 s값이 0보다 작으면 시계방향
// s값이 0보다 크면 반시계 방향
// 0이라면, 일직선이게 된다.