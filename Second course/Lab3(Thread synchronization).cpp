#include <random>
#include <iostream>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <vector>
#include <algorithm>

std::mutex mtx;
std::condition_variable cv;
std::vector<bool> threadExited;
std::vector<bool> threadSleep;

void marker(int threadIndex, std::vector<int>& vec)
{
	std::random_device rd;
	std::mt19937 gen(rd());
	std::uniform_int_distribution<> dis(0, vec.size() - 1);

	int size = vec.size();
	int count = 0;

	while (true)
	{
		std::unique_lock<std::mutex> ul(mtx);

		cv.wait(ul, [&] {
			if (threadSleep[threadIndex] == 0 || threadExited[threadIndex] == 1)
			{
				return 1;
			}
			else
			{
				return 0;
			}
			}
		);

		if (threadExited[threadIndex] == 1)
		{
			ul.unlock();
			cv.notify_all();
			break;
		}

		int r = dis(gen);

		if (vec[r] == 0)
		{
			std::this_thread::sleep_for(std::chrono::milliseconds(5));
			vec[r] = threadIndex + 1;
			count++;
			std::this_thread::sleep_for(std::chrono::milliseconds(5));
		}
		else
		{
			std::cout << "\nThread number = " << threadIndex + 1 <<
				"\nNumber of marked elements = " << count <<
				"\nIndex of not implemented element = " << r << "\n\n";
			threadSleep[threadIndex] = 1;
			count = 0;
		}

		ul.unlock();
		cv.notify_all();
	}

	cv.notify_all();
}

int main() {
	std::cout << "Input array size: ";
	int n, amount_of_terminated_threads = 0;
	std::cin >> n;

	std::cout << "Input amount of threads: ";
	int amount_of_threads;
	std::cin >> amount_of_threads;

	std::vector<int> vec(n, 0);
	std::vector<std::thread> tvec;

	threadExited.resize(amount_of_threads, 0);
	threadSleep.resize(amount_of_threads, 0);

	for (int i = 0; i < amount_of_threads; i++)
	{
		tvec.emplace_back(marker, i, std::ref(vec));
	}

	cv.notify_all();

	while (amount_of_terminated_threads < amount_of_threads)
	{
		std::unique_lock<std::mutex> ul(mtx);

		cv.wait(ul, [&] { return std::find(threadSleep.begin(), threadSleep.end(), 0) == threadSleep.end(); });
		if (amount_of_terminated_threads == amount_of_threads - 1)
		{
			ul.unlock();
			break;
		}

		for (size_t i = 0; i < n; i++)
		{
			std::cout << vec[i] << " ";
		}

		std::cout << "\nInput marker thread number to stop:\n";
		int stop_marker_id;
		std::cin >> stop_marker_id;

		for (size_t i = 0; i < n; i++)
		{
			if (vec[i] == stop_marker_id)
			{
				vec[i] = 0;
			}
		}

		threadExited[stop_marker_id - 1] = true;
		threadSleep = threadExited;

		amount_of_terminated_threads++;
		ul.unlock();
		cv.notify_all();
	}

	for (size_t i = 0; i < amount_of_threads; i++)
	{
		tvec[i].detach();
	}


	std::cout << "\n\nFinal array:\n";
	for (size_t i = 0; i < n; i++)
	{
		std::cout << vec[i] << " ";
	}

	return 0;
}