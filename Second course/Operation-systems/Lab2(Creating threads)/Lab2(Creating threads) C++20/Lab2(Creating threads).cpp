#include <iostream>
#include <thread>
#include <vector>
#include <chrono>
#include <Windows.h>
#include <algorithm>


void min_max(int& min, int& max, std::vector<int> vec)
{
	int size = vec.size();
	min = vec[0];
	max = vec[1];

	for (int i = 0; i < size; i++)
	{
		if (vec[i] > max)
		{
			max = vec[i];

		}
		std::this_thread::sleep_for(std::chrono::milliseconds(7));

		if (vec[i] < min)
		{
			min = vec[i];


		}
		std::this_thread::sleep_for(std::chrono::milliseconds(7));
		std::cout << 1 << "\n";
	}

	std::cout << "\nMin = " << min << "\nMax = " << max;
}

void Average(int& average, std::vector<int> vec)
{
	int size = vec.size();
	for (int i = 0; i < size; i++)
	{
		average += vec[i];
		std::this_thread::sleep_for(std::chrono::milliseconds(12));
		std::cout << "2" << "\n";
	}

	average /= size;
	std::cout << "\nAverage = " << average;
}

int main()
{
	int dimension;
	std::cout << "Enter array dimension: ";
	std::cin >> dimension;

	std::vector<int> vec(dimension);

	std::cout << "Enter an array of integers: ";
	for (int i = 0; i < dimension; i++)
	{
		std::cin >> vec[i];
	}

	int min, max, average = 0;

	std::thread thread_min_max(min_max, std::ref(min), std::ref(max), vec);
	std::thread thread_average(Average, std::ref(average), vec);



	thread_min_max.join();
	thread_average.join();
	//WaitForSingleObject(thread_average.native_handle(), INFINITE);
	//WaitForSingleObject(thread_min_max.native_handle(), INFINITE);

	std::replace(vec.begin(), vec.end(), min, average);
	std::replace(vec.begin(), vec.end(), max, average);

	std::cout << "\n\nResult: \n";

	int size = vec.size();
	for (int i = 0; i < size; i++)
	{
		std::cout << vec[i] << " ";
	}

	return 0;
}
