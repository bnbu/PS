import java.util.*;
import java.io.*;
public class KMP {
	public static ArrayList<Integer> idxList;
	public static int[] fail;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String t = br.readLine();
		String p = br.readLine();
		
		idxList = new ArrayList<>();
		fail = new int[p.length()];
		
		int j = 0;
		for (int i = 1; i < p.length(); i++) {
			while (j > 0 && p.charAt(i) != p.charAt(j))
				j = fail[j - 1];
			if (p.charAt(i) == p.charAt(j))
				fail[i] = ++j;
		}
		// 실패함수를 구하는 과정
		// 실패함수는 P에서 P를 찾는 느낌으로 진행한다.
		// 
		// ex) ABCDABD
		// 이때, fail(0)은 항상 0이므로 건너뛴다 -> fail(0) = 0
		// i는 1, j는 0이고, 1번(B)과 0번(A)은 서로 같지 않으므로 통과 -> fail(1) = 0
		// i는 2, j는 0이고, 2번(C)과 0번(A)은 서로 같지 않으므로 통과 -> fail(2) = 0
		// i는 3, j는 0이고, 3번(D)과 0번(A)은 서로 갖지 않으므로 통과 -> fail(3) = 0
		// i는 4, j는 0이고, 4번(A)과 0번(A)은 서로 같다 -> fail(4) = 1
		// i는 5, j는 1이므로, while문에서의 탐색을 진행
		// => j는 1, 5번(B)과 1번(B)은 서로 같다, while문 종료 -> fail(5) = 2; 
		// i는 6, j는 2이므로, while문에서의 탐색을 진행
		// => j는 2, 6번(D)와 2번(C)는 다르다. j = fail[1] = 0이 된다.
		// => j는 0, 6번(D)와 0번(A)는 다르다, j가 0이므로 while문 종료 -> fail(6) = 0
		
		// 요점 : 원본의 1번 인덱스부터 시작
		// j가 0이라면, 현재 탐색위치가 같은지 찾기
		// j가 0보다 크다면, j부터 다시 뒤로 줄여가면서 몇개나 일치하는지 탐색
		// 이때, j는 하나씩 줄이는게 아닌, 이전 탐색위치의 실패함수값을 가져온다.
		// 원본을 i번째에서 비교 -> 접미사로 볼 수 있고
		// 비교하는 값을 앞에서부터 비교 -> 접두사로 볼 수 있다
		// 어디까지나 실패함수 fail(i)는 i번째 인덱스까지의 문자열 중
		// 접두사와 접미사의 최장 공통문자열 길이이기 때문.
		
		j = 0;
		for (int i = 0; i < t.length(); i++) {
			while(j > 0 && t.charAt(i) != p.charAt(j))
				j = fail[j - 1];
			if (t.charAt(i) == p.charAt(j)) {
				if (j == p.length() - 1) {
					idxList.add(i - p.length() + 2);
					// => j가 패턴의 마지막idx
					// 즉 모두 일치한단 뜻이므로, 시작 인덱스는 i - p.length + 1
					// 이때, 인덱스가 아닌 서수 번째로 해야하므로 + 1을 더 해준다.
					j = fail[j];
					// 다음에 마저 다시 탐색해야하므로 j를 초기화
					// 이때 0으로 초기화 하지 않고
					// fail(j)를 쓰는 이유는
					// 지금 탐색중인 위치에서 조차 겹치는 문자열이 존재하여
					// 건너뛰지 않도록 하기 위함이다.
				}
				else
					j++;
				// j가 패턴의 마지막 idx가 아니라면, 패턴의 확인 인덱스인 j도 1을 늘려서
				// 다음 i와 같이 j도 비교해 나간다.
			}
		}
		
		System.out.println(sb);
	}
	public static ArrayList<Integer> kmp(String orign, String pattern) {
		ArrayList<Integer> idxList = new ArrayList<>();
		int[] failFunction = new int[pattern.length()];
		
		int j = 0;
		for (int i = 1; i < pattern.length(); i++) {
			while(j > 0 && pattern.charAt(i) != pattern.charAt(j))
				j = failFunction[j - 1];
			if (pattern.charAt(i) == pattern.charAt(j))
				failFunction[i] = ++j;
		}
		// 실패함수

		for (int i : failFunction)
			System.out.print(i + " ");
		System.out.println();
		
		j = 0;
		for (int i = 0; i < orign.length(); i++) {
			while (j > 0 && orign.charAt(i) != pattern.charAt(j))
				j = failFunction[j - 1]; // 일치하지 않으면 j는 이전 idx의 실패 함수 값으로 돌려보낸다.
			if (orign.charAt(i) == pattern.charAt(j)) {
				if (j == pattern.length() - 1) {
					idxList.add(i - pattern.length() + 1);
					j = failFunction[j];
				} // 마지막까지 일치시, 시작 인덱스를 반환리스트에 추가
				else
					j++; // 마지막이 아니면, i와 같이 j도 1 늘림
			}
		}
		
		for (int i : idxList)
			System.out.print(i + " ");
		System.out.println();
		
		return idxList;
	}
}
