import java.util.*;
import java.io.*;
public class E {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int rotate = 0, bCnt = 0, wCnt = 0,
			q = Integer.parseInt(br.readLine());
		Deque<Character> dq = new LinkedList<>();
		
		while (q-- > 0) {
			st = new StringTokenizer(br.readLine());
			String str = st.nextToken();
			if (str.equals("push")) {
				// push
				str = st.nextToken();
				if (str.equals("b")) {
					if (rotate == 90) {
						if (dq.isEmpty()) continue;
						bCnt++;
						dq.add('b');
					}
					else if (rotate == 270) {
						continue;
					}
					else {
						bCnt++;
						dq.add('b');
					}
				}
				else {
					wCnt++;
					dq.add('w');
				}
			}
			else if (str.equals("rotate")) {
				//rotate
				str = st.nextToken();
				if (str.equals("l")) {
					rotate -= 90;
					if (rotate < 0) rotate += 360;
				}
				else {
					rotate += 90;
					if (rotate >= 360) rotate -= 360; 
				}
				
				if (rotate == 90) {
					while (true) {
						if (dq.isEmpty()) break;
						if (dq.peekFirst() == 'w') break;
						dq.removeFirst();
						bCnt--;
					}
				}
				else if (rotate == 270){
					while (true) {
						if (dq.isEmpty()) break;
						if (dq.peekLast() == 'w') break;
						dq.removeLast();
						bCnt--;
					}
				}
			}
			else if (str.equals("count")) {
				//count
				str = st.nextToken();
				if (str.equals("b"))
					sb.append(bCnt).append("\n");
				else
					sb.append(wCnt).append("\n");
			}
			else {
				// pop
				if (!dq.isEmpty()) {
					if (dq.pop() == 'b') bCnt--;
					else {
						wCnt--;
						if (rotate == 90) {
							while (true) {
								if (dq.isEmpty()) break;
								if (dq.peekFirst() == 'w') break;
								dq.removeFirst();
								bCnt--;
							}
						}
					}
				}
			}
		}
		System.out.print(sb);
	}
}

// 앞 뒤에서 모두 push, pop이 발생하기 때문에 Deque 자료구조를 사용해야 한다.

// 1. push
// 공을 집어넣을 경우
//    a. 0, 180도 : 가능
//    b. 90도 (앞이 밑으로 내려옴) : 큐가 비어있으면 공을 넣어도 바로 떨어짐, 반대로 큐가 비어있지 않으면 제일 앞에 가림막이 있다는 뜻 (rotate에 의해 자동으로 충족)
//    c. 270도 (앞이 위로 올라감) : 절대 못넣음
// 가림막을 집어넣을 경우
//    어떠한 상황에서도 가림막은 넣을 수 있다

// 2. rotate
//  회전 결과가 90도 : 앞이 밑으로 갔으므로, 앞에 가림막이 올떄까지 모두 pop
//  회전 결과가 270도 : 앞이 위로 갔으므로, 뒤에 가림막이 올떄까지 뒤에서 모두 pop

// 3. pop
//   공을 pop할 경우에는 상관 없으나, 가림막을 pop하는 경우가 문제가 된다
//   90도에서 pop하는 경우 : 다음 가림막이 앞에 올때까지, 혹은 큐가 모두 빌떄까지 전부 pop시켜야 
