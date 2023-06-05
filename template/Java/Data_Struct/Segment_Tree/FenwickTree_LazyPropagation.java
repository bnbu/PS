import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class FenwickTree_LazyPropagation {
    static int n;
    static long[] fenwickFirst, fenwickConst;
    static void makeFenwickTree(long[] tree) {
        for (int i = 1; i <= n; i++) {
            int j = i + (i & -i);
            if (j <= n) tree[j] += tree[i];
        }
    }
    static void update(long[] tree, int idx, int k) {
        while (idx <= n) {
            tree[idx] += k;
            idx += (idx & -idx);
        }
    }
    static long sum(long[] tree, int idx) {
        long ret = 0;
        while (idx > 0) {
            ret += tree[idx];
            idx &= idx - 1;
        }
        return ret;
    }
    static void updateRange(int left, int right, int k) {
        // 1차항
        update(fenwickFirst, left, k);
        update(fenwickFirst, right + 1, -k);
        // 상수항
        update(fenwickConst, left, (-left + 1) * k);
        update(fenwickConst, right + 1, right * k);
    }
    static long getSumRange(int left, int right) {
        long ret = 0;
        ret += sum(fenwickFirst, right) * right + sum(fenwickConst, right); // [1, right]
        ret -= sum(fenwickFirst, left - 1) * (left - 1) + sum(fenwickConst, left - 1); // [1, left - 1];
        return ret; // [left, right];
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());
        fenwickFirst = new long[n + 1];
        fenwickConst = new long[n + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            fenwickConst[i] = Integer.parseInt(st.nextToken());
        }
        makeFenwickTree(fenwickConst);

        int q = Integer.parseInt(br.readLine());
        while (q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int i = Integer.parseInt(st.nextToken());

            if (i == 1) {
                int a = Integer.parseInt(st.nextToken()),
                    b = Integer.parseInt(st.nextToken()),
                    c = Integer.parseInt(st.nextToken());
                updateRange(a, b, c);
            }
            else {
                int a = Integer.parseInt(st.nextToken()),
                    b = Integer.parseInt(st.nextToken());
                sb.append(getSumRange(a, b)).append("\n");
            }
        }
        System.out.println(sb);
    }
}
// fenwick tree에서의 lazy propagation
// segment tree와 약간 다르게 lazy 배열 같은게 따로 있는게 아니라
// 아래의 아이디어를 차용해서 일차항 계수 및 상수항으로 트리를 구성해서 생각한다

// 어느 구간 [a, b]에 값 k를 더한다고 생각해보자
// 이 구간 [a, b] 내에 있는 임의의 원소를 i번째라 할때
// 이 i번째 까지의 원소의 합은 (i - a + 1)k 만큼 증가하게 된다.
// (k가 a부터 i까지의 길이만큼 중첩되는 셈)

// 이를 f(i) = i*k + (-a + 1)k 로 보면
// i에 대한 함수로 생각해볼 수 있고, 이를 1차항과 상수항으로 나누어 생각할 수 있다
// 1차항 : i*k
// 상수항 : (-a + 1)k

// 이 함수의 값을 k번째 원소로 사용하는 펜윅 트리라 생각할 수 있게된다.
// 초기에 특정 배열로 초기화를 하려면, 상수항 트리에 추가해서
// 펜윅 트리를 구성시키면 된다.