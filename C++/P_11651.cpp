#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

int main()
{
	int testCase;
	cin >> testCase;

	vector<pair<int, int>> coordinates(testCase);

	for (int i = 0; i < testCase; i++)
	{
		cin >> coordinates[i].second >> coordinates[i].first;
	}

	sort(coordinates.begin(), coordinates.end());

	for (int i = 0; i < testCase; i++)
	{
		cout << coordinates[i].second << " " << coordinates[i].first << "\n";
	}

	return 0;
}