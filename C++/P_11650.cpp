#include <iostream>
#include <algorithm> // 정렬
#include <vector> // 정렬에 사용될 벡터
using namespace std;

int main()
{	
	int testCase;
	cin >> testCase;

	vector<pair<int, int>> coordinates(testCase);

	for (int i = 0; i < testCase; i++)
	{
		cin >> coordinates[i].first >> coordinates[i].second;
	}

	sort(coordinates.begin(), coordinates.end());

	for (int i = 0; i < testCase; i++)
	{
		cout << coordinates[i].first << " " << coordinates[i].second << "\n";
	}

	return 0;
}
