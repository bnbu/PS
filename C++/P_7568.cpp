#include <iostream>
#include <vector>
using namespace std;

class Person
{
public:
	int weight, height, rank;

	Person()
	{
		weight = 0;
		height = 0;
		rank = 0;
	}
};
int main()
{
	int num;
	cin >> num;

	vector<Person> people = vector<Person>(num);

	for (int i = 0; i < num; i++)
	{
		cin >> people[i].weight >> people[i].height;
	}

	for (int i = 0; i < num; i++)
	{
		int rank = 1;
		for (int j = 0; j < num; j++)
		{
			if (i == j)
				continue;

			if (people[i].weight < people[j].weight && people[i].height < people[j].height)
				rank++;
		}
		people[i].rank = rank;
	}

	for (int i = 0; i < num; i++)
		cout << people[i].rank << " ";

	return 0;
}