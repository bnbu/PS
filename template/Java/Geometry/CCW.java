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
// �� ������ �������� ���
// 2���� ��� �� 3���� x,y��ǥ�κ��� ccw�� ������ ���� ��������
// s = (p1.x * p2.y + p2.x * p3.y + p3.x * p1.y) -
//		(p1.y * p2.x + p2.y * p3.x + p3.y * p1.x)
// s < 0 �̸�, p1->p2 ���Ϳ� ���� p3�� �����ʿ� ����. (�� �ð����)
// s == 0 �̸� ������.
// s > 0 �̸�, p1->p2 ���Ϳ� ���� p3�� ���ʿ� ����. (�� �ݽð����)
//

// s�� �� ������ ��������
// CCW�� ����� s�� ��ǻ� p1->p2 �� p2->p3 ������ ������ ũ�⿡ �ش��ϸ�
// ���� a�� b�� ������ ũ�Ⱚ��
// a x b = |a||b|*sin(t) **(a : p1->p2 / b : p2->p3 )
// => (0, 0, axby - aybx) �� ����
// axby => (p2x - p1x)(p3y - p2y) - (p2y - p1y)(p3x - p2x)�� �ȴ�.
// ����, �̸� �����Ͽ� �Ի��ϸ�
// s = p2x*p3y + p1x*p2y - p1x*p3y - p2x*p2y
//		- (p2y*p3x + p1y*p2x - p2y*p2x - p1y*p3x)  => p2x*p2y ���� �Ұŵ�
// �����ϸ�
// s = (p1.x * p2.y + p2.x * p3.y + p3.x * p1.y) -
//			(p1.y * p2.x + p2.y * p3.x + p3.y * p1.x)

// �� s���� 0���� ������ �ð����
// s���� 0���� ũ�� �ݽð� ����
// 0�̶��, �������̰� �ȴ�.