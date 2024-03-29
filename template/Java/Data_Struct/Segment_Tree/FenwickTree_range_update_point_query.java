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

// 이는 펜윅트리가 배열 본연의 값을 저장하는게 아닌
// 배열값은 따로 있고, imos 법에서의 imos 배열처럼
// 변화값만을 저장해서, [1,K] 까지의 합은 결국 1부터 k까지의 변화값이 되어서
// 이 구간합을 얻어오면 결국은 K의 최종 값이 되기 때문

// 이 때문에 구간합 쿼리는 사용할 수 없게 된다.

// https://nahwasa.com/entry/%ED%8E%9C%EC%9C%85-%ED%8A%B8%EB%A6%ACFenwick-tree-BIT-%EA%B8%B0%EB%B3%B8-2D-lazy-propagationrange-update-point-query-range-update-range-query#%EC%9D%91%EC%9A%A9_2_:_%EA%B5%AC%EA%B0%84_%EC%97%85%EB%8D%B0%EC%9D%B4%ED%8A%B8,_%EB%8B%A8%EC%9D%BC_%EA%B0%92_%ED%9A%8D%EB%93%9D
// 저기 참고할 것
