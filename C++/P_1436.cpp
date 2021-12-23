#include <iostream>
#include <string>
using namespace std;
int main()
{
	int n;
	int current = 0, cnt = 0;
	cin >> n;
	while (cnt != n) {
		current++;
		string s = to_string(current);
		if (s.find("666") != string::npos)
			cnt++;
	}
	cout << current << "\n";
}