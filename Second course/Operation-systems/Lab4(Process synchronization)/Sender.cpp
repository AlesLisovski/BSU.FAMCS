#include <iostream>
#include <string>
#include <fstream>
#include <Windows.h>
#include <conio.h>
#include <thread>

bool isFileEmpty(const std::string& file_name) {
	std::ifstream file(file_name, std::ios::binary);
	bool result = (file.peek() == std::ifstream::traits_type::eof());
	file.close();
	return result;
}

int countLinesInFile(const std::string& file_name) {
	std::ifstream in(file_name, std::ios::binary);

	int lineCount = 0;
	std::string line;
	while (std::getline(in, line)) {
		lineCount++;
	}
	in.close();

	return lineCount;
}

int main(int argc, char* argv[])
{
	std::string binary_file = argv[1];
	int number_of_notes = std::stoi(argv[2]);
	std::string index = argv[3];

	HANDLE hStartEvent = OpenEvent(EVENT_MODIFY_STATE, FALSE, L"Process Started");
	if (hStartEvent == NULL)
	{
		std::cout << "Open event failed. \nInput any char to exit.\n";
		std::cin.get();
		return GetLastError();
	}

	HANDLE hMutex = OpenMutex(SYNCHRONIZE, FALSE, L"mut ex");
	SetEvent(hStartEvent);

	std::cout << "Sendler" + index + " was started\n";
	std::cout << "Input 1 to write message;\nInput 0 to exit process\n";
	std::string key;
	std::cin >> key;

	while (true)
	{
		if (key == "1")
		{
			std::string msg;
			std::cout << "\nInput message to add\n";
			std::cin >> msg;

			if (msg.size() > 20)
			{
				std::cout << "\nIncorrect size! Message size is greater than 20! Try again.\nInput 1 to write message;\nInput 0 to exit process\n";
				std::cin >> key;
				continue;
			}

			std::cout << "\nWe are waiting for the message to be sent, please do not click anything\n";
			WaitForSingleObject(hMutex, INFINITE);
			int size;
			while ((size = countLinesInFile(binary_file)) >= number_of_notes) 
			{
				std::this_thread::sleep_for(std::chrono::milliseconds(100));
			}
			std::cout << "Message sent\n";

			std::ofstream out(binary_file, std::ios::out | std::ios::app);
			out << "Sendler" + index +" : " << msg << "\n";
			out.close();
			ReleaseMutex(hMutex);

			std::cout << "\nInput 1 to write message;\nInput 0 to exit process\n";
			std::cin >> key;
		}
		else if (key == "0")
		{
			std::cout << "Process ended.";
			break;
		}
		else
		{
			std::cout << "\nIncorrect value! Try again.\nInput 1 to write message;\nInput 0 to exit process\n";
			std::cin >> key;
		}
	}

	return 0;
}