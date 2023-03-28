#include <iostream>
#include <limits>
#include <vector>
using namespace std;
typedef pair<int, int> pii;
class PriorityQueue_pii {
    private:
    pii arr[1000001];
    int last_idx;

    public:
    PriorityQueue_pii() {
        last_idx = 0;
    }
    void swap(int idx1, int idx2) {
        pii temp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = temp;
    }
    void push(pii p) {
        arr[++last_idx] = p;

        for (int i = last_idx; i > 1; i /= 2) {
            if (arr[i/2] > arr[i]) swap(i/2, i);
            else break;
        }
    }
    pii top() {
        if (last_idx == 0) return {-1, -1};
        else return arr[1];
    }
    void pop() {
        if (last_idx == 0) return;
        else {
            arr[1] = arr[last_idx];
            arr[last_idx] = {INT32_MAX, INT32_MAX};
            last_idx--;

            int idx = 1;
            while (2 * idx <= last_idx) {
                if (arr[2*idx] > arr[idx] && arr[2*idx + 1] > arr[idx]) break;
                else if (arr[2*idx] < arr[2*idx + 1]) {
                    swap(2*idx, idx);
                    idx *= 2;
                }
                else {
                    swap(2*idx + 1, idx);
                    idx *= 2;
                    idx += 1;
                }
            }
        }
    }
    bool empty() {
        return last_idx == 0;
    }
};

int main() {
    return 0;
}