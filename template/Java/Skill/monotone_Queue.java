import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class monotone_Queue {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken()), l = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = Integer.parseInt(st.nextToken());

        Deque<Integer> dq = new LinkedList<>();
        dq.add(arr[0]);
        sb.append(dq.peek()).append(" ");
        for (int i = 1; i < l; i++) {
            while (!dq.isEmpty() && dq.peekLast() > arr[i]) dq.pollLast();
            dq.add(arr[i]);
            sb.append(dq.peek()).append(" ");
        }
        for (int i = l; i < n; i++) {
            if (arr[i - l] == dq.peek()) dq.poll();
            while (!dq.isEmpty() && dq.peekLast() > arr[i]) dq.pollLast();
            dq.add(arr[i]);
            sb.append(dq.peek()).append(" ");
        }
        System.out.println(sb);
    }
}

// 큐 내의 값들이 단조적인 조건을 만족하도록 저장하는 모노톤 큐 기법
