import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int x1 = scan.nextInt(), y1 = scan.nextInt(), r1 = scan.nextInt(),
				x2 = scan.nextInt(), y2 = scan.nextInt(), r2 = scan.nextInt();
			
			int bigRadius = r1 > r2 ? r1 : r2;
			int smallRadius = r1 < r2 ? r1 : r2;
			double distance = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
			
			if (distance == 0) {
				if (r1 == r2)
					sb.append(-1 + "\n");
				else 
					sb.append(0 + "\n");
			}
			else {
				if (bigRadius < distance) {
					if ((r1 + r2) < distance)
						sb.append(0 + "\n");
					else if ((r1 + r2) > distance)
						sb.append(2 + "\n");
					else
						sb.append(1 + "\n");
				}
				else {
					if (bigRadius > distance + smallRadius)
						sb.append(0 + "\n");
					else if (bigRadius < distance + smallRadius)
						sb.append(2 + "\n");
					else
						sb.append(1 + "\n");
				}
			}
		}
		System.out.println(sb.toString());
	}
}
// 2020-08-30 21:00
// 반지름이 r1 이고 중심이 (x1, y1)원 과 반지름이 r2 이고 중심이 (x2, y2)인 원들의 교점 수를 찾는 문제.
// 이때 원이 일치. 즉 중심이 같고 반지름도 동일하다면 -1을 출력하는게 포인트다.
// 교점의 경우를 나눠 생각해야한다
// 우선적으로 중심간 거리가 0인지(중심이 같음) 아닌지(다름)
// 만약 중심간 거리가 0이라면, 반지름의 길이로 판단이 가능하며
// 거리가 0이 아니라면 다시 두 반지름 중 큰 반지름에 해당하는 값이 거리보다 큰지 작은지로 구분할 수 있다.
// 각 경우에 따라 3가지 경우가 존재하며
// 각각 가장 큰 값과 나머지 두 값의 합의 비교로 이루어진다.
// 가장 큰 값이 크다면 0개, 작다면 2개, 두 값이 서로 같다면 1개의 교점을 같는다.
