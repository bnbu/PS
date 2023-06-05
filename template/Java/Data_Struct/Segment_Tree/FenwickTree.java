import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class FenwickTree {
    static int n;
    static long[] arr, fenwick;
    static void makeFenwickTree() {
        for (int i = 1; i <= n; i++) {
            int j = i + (i & -i);
            if (j <= n) fenwick[j] += fenwick[i];
        }
    }
    static void updateFenwick(int idx, long k) {
        long diff = k - arr[idx];
        arr[idx] = k;

        while (idx <= n) {
            fenwick[idx] += diff;
            idx += (idx & -idx);
        }
    }
    static long getSumFenwick(int idx) {
        long ret = 0;
        while (idx > 0) {
            ret += fenwick[idx];
            idx &= idx - 1;
        }
        return ret;
    }
    static long getSumRangeFenwick(int left, int right) {
        return getSumFenwick(right) - getSumFenwick(left - 1);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());

        arr = new long[n + 1];
        fenwick = new long[n + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            fenwick[i] = arr[i];
        }
        makeFenwickTree();
        System.out.println(Arrays.toString(fenwick));

        int q = Integer.parseInt(br.readLine());
        while (q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int i = Integer.parseInt(st.nextToken()),
                j = Integer.parseInt(st.nextToken()),
                k = Integer.parseInt(st.nextToken());

            if (i == 1)
                updateFenwick(j, k);
            else
                sb.append(getSumRangeFenwick(j, k)).append("\n");
        }
        System.out.println(sb);
    }
}

// Fenwick Tree
// N개의 데이터를 세그먼트 트리로 만들려면 어림잡아 4*N개의 노드가 필요해서
// 메모리 적으로 살짝 별로인 부분이 있는데
// 여기서 다시, 각 구간의 길이를 적절하게 바꿔서 N+1개의 배열만으로도
// 세그먼트 트리와 똑같이 동작할 수 있다.
// 단, 세그먼트 트리에 비해 약간 범용성은 떨어지는 편

// 기존 세그먼트 트리의 구간은 리프노드부터 2개씩 짝을 지어서
// 이웃한 구간을 연속적으로 늘려가며 표현했다면
// 펜윅 트리의 경우에는 다음과 같이 노드를 표현한다

// 1. 홀수 번째에 위치하는 값은 그냥 그 자체의 값이 된다.
// 2. 2^k번째에 위치하면서 2^k+1의 배수가 아닌 원소는
//    직전 2^k개의 합을 의미한다.

// 구간 합을 얻어오는 경우
// 펜윅 트리에서의 구간 합은 반드시 1부터 x까지의 합을 구해온다
// 따라서 어느 특정 구간 [a, b]의 경우는
// [1, b] 부터 [1, a-1]의 구간을 빼는 것으로 진행된다.
// 구간을 구해오는 과정은
// x를 2진수로 고쳤을 때, LSB(비트가 1인 것중 가장 좌측에 위치)를 하나씩 빼면서
// 거기에 해당하는 노드를 더하는 것으로 진행된다.
// ex) 43 -> 101011 이므로
// 1. 101011 : 43번 노드
// 2. 101010 : 42번 노드
// 3. 101000 : 40번 노드
// 4. 100000 : 32번 노드
// => [1, 43] = fenwick[43] + fenwick[42] + fenwick[40] + fenwick[32]

// 어느 위치의 값을 업데이트 하는 경우
// 구간 합과는 반대로, 해당 위치로부터 LSB를 더해가면서 진행된다
// 위치가 3인 곳을 업데이트 하면
// ex) 3 -> 011
// 1. 011(3)
// 2. 011 + 001 = 100(4)
// 3. 100 + 100 = 1000(8)
// 4. 1000 + 1000 = 10000(16) ...
// 위치가 3인 값을 업데이트 하면, [3], [4], [8], [16].. 을 업데이트 하게 된다.

// 어느 구간의 값을 업데이트 하는 경우?
// 이는 곧 fenwick tree에 lazy propagation을 적용하는 문제로 바뀐다
// 이는 펜윅 트리 구성을
// 약간 나눠야 하기 때문에 따로 작성할 예정
// https://plzrun.tistory.com/entry/Fenwick-Tree-Range-Update-Range-Sum-%ED%8E%9C%EC%9C%85%ED%8A%B8%EB%A6%AC-%EA%B5%AC%EA%B0%84%EC%97%85%EB%8D%B0%EC%9D%B4%ED%8A%B8-%EA%B5%AC%EA%B0%84%ED%95%A9-OlgN%EC%97%90-%EA%B5%AC%ED%95%98%EA%B8%B0-feat-Segment-Tree-Lazy-Propagation
// https://everenew.tistory.com/114
// https://rebro.kr/94