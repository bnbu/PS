#include <iostream>
#include <vector>
#include <queue>
#include <limits>
using namespace std;

bool* selected;
vector<pair<int, int>>* adj;
// pair<int, int> 순서대로 w, d

int prim(int v) {
    priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> pq;
    pq.push(make_pair(0, 1)); // 시작점
    int cost = 0;
    for (int i = 1; i <= v; i++) {
        pair<int, int> curr;
        int min = INT32_MAX;

        while (!pq.empty()) {
            curr = pq.top(); pq.pop();
            if (!selected[curr.second]) {
                min = curr.first;
                break;
            }
        }

        if (min == INT32_MAX)
            return INT32_MAX;
        
        cost += min;
        selected[curr.second] = true;
        for (pair<int, int> next : adj[curr.second]) {
            pq.push(next);
        }
    }
    return cost;
}

int main() {
    int v, e;
    cin >> v >> e;

    selected = new bool[v + 1];
    adj = new vector<pair<int, int>>[v + 1];
    for (int i = 1; i <= v; i++)
        selected[i] = false;

    while (e--) {
        int s, d, w;
        cin >> s >> d >> w;
        adj[s].push_back(make_pair(w, d));
        adj[d].push_back(make_pair(w, s));
    }

    cout << prim(v);

    return 0;
}