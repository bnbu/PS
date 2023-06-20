import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] h;

    static int findMax(int start, int end) {
        if (start == end) {
            return h[start];
        }
        int ret = 0;
        int mid = (start + end) / 2;

        ret = Math.max(ret, findMax(start, mid));
        ret = Math.max(ret, findMax(mid + 1, end));
        // 각각 왼쪽, 오른쪽을 분할하여 진행

        // 아래부터는 중앙 분할점에 걸쳐 나타나는 경우를 계산
        int left, right;
        int curr = h[mid];
        left = right = mid;

        ret = Math.max(ret, curr);
        while (start < left || right < end) {
            if ((left > start && right < end && h[left - 1] > h[right + 1]) || (left > start && right == end)) {
                left--;
                curr = Math.min(curr, h[left]);
            }
            else if ((left > start && right < end && h[left - 1] <= h[right + 1]) || (left == start && right < end)){
                right++;
                curr = Math.min(curr, h[right]);
            }

            ret = Math.max(ret, curr * (right - left + 1));
        }
        return ret;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        h = new int[n + 2];
        for (int i = 1; i <= n; i++) h[i] = Integer.parseInt(br.readLine());
        System.out.println(findMax(0, n + 1));
    }
}