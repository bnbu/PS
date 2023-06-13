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
    static void updateFenwickRange(int left, int right, long k) {
        updateFenwick(left, k);
        updateFenwick(right + 1, -k);
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
                // sb.append(getSumRangeFenwick(j, k)).append("\n");
        }
        System.out.println(sb);
    }
}

// Fenwick Tree range update - point query
// 업데이트를 위와같이 해주는 대신
// 구간 합 쿼리는 사용 불가
// getSumFenwick(i)를 통해 i번째 위치의 값을 얻어올 수 있다.
