import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] p;
    static int manachers(String s) {
        int ret = 0;
        p = new int[s.length()];
        // j = Maximum palindrome radius + Maximum palindrome center;
        // k = Maximum palindrome center;
        char[] arr = s.toCharArray();
        int j = -1, k = -1;
        for (int i = 0; i < arr.length; i++) {
            if (i <= j) p[i] = Math.min(j - i, p[2*k - i]);
            while (i - p[i] - 1 >= 0 && i + p[i] + 1 < arr.length && arr[i - p[i] - 1] == arr[i + p[i] + 1])
                p[i]++;

            if (j < i + p[i]) {
                j = i + p[i];
                k = i;
            }
            ret = Math.max(ret, p[i]);
        }
        System.out.println(s);
        System.out.println(Arrays.toString(p));
        return ret;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        char[] arr = br.readLine().toCharArray();
        for (int i = 0; i < arr.length; i++) {
            sb.append('#');
            sb.append(arr[i]);
        }
        sb.append('#');
        System.out.println(manachers(sb.toString()));
    }
}

// Manacher's 알고리즘
// 어느 문자열 내의 모든 팰린드롬(좌우대칭) 문자열을 O(n)으로 찾아낼 수 있다

// i번째 문자를 생각해볼 때
// p[i]는 i번째 문자를 기준으로 하는 팰린드롬 문자열의 반지름이 된다;

// 이제 p[i]를 구해 나갈 것인데
// 지금 문자 위치 i번보다 이전 문자 중 가장 긴 팰린드롬 문자열의 중심인 위치를 k라고 하자
// p[k]에 대해 k + p[k] = j라고 하자 (j는 최장 팰린드롬의 범위의 경계가 된다)

// 그러면 p[i]의 초기값은 다음과 같이 생각할 수 있다
// 1. i > j : 범위 밖이므로 i+1, i-1부터 팰린드롬을 계산해나가야 한다
// 2. i <= j : 팰린드롬의 범위 내부이므로, j - i 혹은 k를 중심으로 하는 i의 대칭 위치 i'= 2*k - i의 반지름인 p[2*k - i]
//             이 둘중 더 작은 값을 초기값으로써 사용하게 된다. 이후부터는 똑같이 팰린드롬을 계산

// 이때 홀수인 팰린드롬 문자열만 판단할 수 있는데
// 각 문자열 사이에 #과 같은 더미 문자를 넣음으로써 짝수 팰린드롬 문자열도 계산할 수 있다.
