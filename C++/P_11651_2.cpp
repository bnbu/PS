#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;

bool compare(pair<int, int> p1, pair<int, int> p2)
{
	if (p1.second == p2.second)
	{
		return p1.first < p2.first;
	}
	return p1.second < p2.second;
}

int main()
{
	int testCase;
	cin >> testCase;

	vector<pair<int, int>> coordinates(testCase);

	for (int i = 0; i < testCase; i++)
	{
		cin >> coordinates[i].first >> coordinates[i].second;
	}

	sort(coordinates.begin(), coordinates.end(), compare);

	for (int i = 0; i < testCase; i++)
	{
		cout << coordinates[i].first << " " << coordinates[i].second << "\n";
	}

	return 0;
}
