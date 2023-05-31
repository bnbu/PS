package D0531;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Pair {
    int n, idx;
    public Pair(int n, int idx) {
        this.n = n;
        this.idx = idx;
    }
}
class Card {
    int a, b;
    public Card(int a, int b) {
        this.a = a;
        this.b = b;
    }
}
public class P_17274 {
    static int[] segTree;
    static Pair[] c;
    static Card[] cards;
    static ArrayList<Integer> compress;
    static ArrayList<Integer>[] mergeSortTree;
    static void updateSegTree(int start, int end, int node, int idx, int k) {
        if (idx < start || idx > end) return;
        if (start == end) {
            segTree[node] = k;
            return;
        }
        int mid = (start + end) / 2;
        updateSegTree(start, mid, node * 2, idx, k);
        updateSegTree(mid + 1, end, node * 2 + 1, idx, k);
        segTree[node] = Math.max(segTree[2*node], segTree[2*node + 1]);
    }
    static int getSegTree(int start, int end, int node, int left, int right) {
        if (right < start || end < left) return -1;
        if (left <= start && end <= right) return segTree[node];
        int mid = (start + end) / 2;
        return Math.max(getSegTree(start, mid, 2*node, left, right), getSegTree(mid + 1, end, 2*node + 1, left, right));
    }

    static void makeMergeSortTree(int start, int end, int node) {
        if (start == end) {
            mergeSortTree[node] = new ArrayList<>();
            mergeSortTree[node].add(c[start].n);
            return;
        }

        int mid = (start + end) / 2;
        makeMergeSortTree(start, mid, 2*node);
        makeMergeSortTree(mid + 1, end, 2*node + 1);
        mergeSortTree[node] = new ArrayList<>();
        ArrayList<Integer> left = mergeSortTree[2*node],
                           right = mergeSortTree[2*node + 1];
        int last = 0, n = 0;
        for (int l : left) {
            n = lowerBound(right, l);
            for (int i = last; i < n; i++) mergeSortTree[node].add(right.get(i));
            mergeSortTree[node].add(l);
            last = n;
        }
        for (int i = last; i < right.size(); i++) mergeSortTree[node].add(right.get(i));
    }
    static int getMergeSortTree(int start, int end, int node, int left, int right, int k) {
        if (right < start || end < left) return 0;
        if (left <= start && end <= right) {
            int ret = lowerBound(mergeSortTree[node], k);
            return mergeSortTree[node].size() - ret;
        }
        int mid = (start + end) / 2;
        return getMergeSortTree(start, mid, 2*node, left, right, k) + getMergeSortTree(mid + 1, end, 2*node + 1, left, right, k);
    }

    static int lowerBound(ArrayList<Integer> arr, int target) {
        int begin = 0;
        int end = arr.size();
        while(begin < end) {
            int mid = (begin + end) / 2;
            if(arr.get(mid) >= target) end = mid;
            else begin = mid + 1;
        }
        return end;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken()),
            m = Integer.parseInt(st.nextToken());

        HashSet<Integer> hs = new HashSet<>();
        cards = new Card[n + 1];
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()),
                b = Integer.parseInt(st.nextToken());
            cards[i] = new Card(a, b);
            hs.add(a);
            hs.add(b);
        }
        c = new Pair[m + 1];
        for (int i = 1; i <= m; i++) {
            c[i] = new Pair(Integer.parseInt(br.readLine()), i);
            hs.add(c[i].n);
        }

        compress = new ArrayList<>(hs);
        compress.sort(null);
        segTree = new int[4*compress.size()];
        Arrays.fill(segTree, -1);
        mergeSortTree = new ArrayList[4*m];
        for (int i = 1; i <= n; i++) {
            cards[i].a = lowerBound(compress, cards[i].a);
            cards[i].b = lowerBound(compress, cards[i].b);
        }
        for (int i = 1; i <= m; i++) {
            c[i].n = lowerBound(compress, c[i].n);
            updateSegTree(0, compress.size() - 1, 1, c[i].n, c[i].idx);;
        }
        makeMergeSortTree(1, m, 1);

        long sum = 0;
        for (int i = 1; i <= n; i++) {
            int idx = getSegTree(0, compress.size() - 1, 1, Math.min(cards[i].a, cards[i].b), Math.max(cards[i].a, cards[i].b) - 1);
            int cnt = getMergeSortTree(1, m, 1, idx == -1 ? 0 : idx, m, Math.max(cards[i].a, cards[i].b));
            if (idx == -1)
                sum += cnt % 2 == 0 ? compress.get(cards[i].a) : compress.get(cards[i].b);
            else {
                if (cards[i].a > cards[i].b)
                    sum += cnt % 2 == 0 ? compress.get(cards[i].a) :compress.get(cards[i].b);
                else
                    sum += cnt % 2 == 0 ? compress.get(cards[i].b) :compress.get(cards[i].a);
            }
        }
        System.out.println(sum);
    }
}

// 일단 일일이 뒤집는다는 동작은 어떻게 해도 O(NM)이 되므로 불가능

// 일단 카드 1장만으로 볼때, 카드 앞, 뒤로 써져있는 숫자가 a, b (a < b)라고 하면

// a보다 작은 값 : 카드는 뒤집어지지 않는다
// a <= b인 값 : 카드는 반드시 값이 b가 된다
// b보다 큰 값 : 카드는 뒤집힌다
// 이 특성을 이용해서 불러진 숫자들 중 2번에 해당하는 숫자가 마지막으로 불러진 이후, 3번에 해당하는 숫자가 몇번이나 불러졌는지를 통해 마지막 상태를 알 수 있다.

// 이거를 다양한 카드로 확장시켜 생각하면

// 각 값의 마지막 등장 순서를 세그먼트 트리로 구축 -> (4 * N)
// 각 순서에서 부른 값을 머지소트트리로 구축 -> (4*MlogM)
// 이렇게 구축한 후 하나의 카드의 마지막 상태를 알아오려면

// 1번 세그먼트 트리에서 구간쿼리 [a, b-1]을 통해 가장 마지막으로 등장한 순서값을 알아옴
// 2번 머지소트 트리에서 1번으로부터 알아온 값 이후로 등장한 값 중 upper bound 등을 통해서 이후 몇개나 등장했는지를 알아옴
// (2번에서 알아온 값 % 2) 을 통해 이게 0이면 짝수번 등장이니까 카드의 상태는 b를 나타내고, 이게 1이면 홀수번 등장이니까, 카드의 상태는 a이다
// 이거를 N번 반복한다
// 1번 과정이 logN, 2번 과정이 (logM)^2 정도로 예상됨

// 그리고 각 카드가 일단은 최대 20만장이지만, 값은 10억까지 가능하므로 좌표압축까지 필요하다.
