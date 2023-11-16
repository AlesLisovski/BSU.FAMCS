#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <Windows.h>
#include <conio.h>
#include<thread>

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

std::string shiftLinesInFile(const std::string& file_name) {
	std::vector<std::string> lines;
	std::string line;

	std::ifstream in(file_name, std::ios::binary);
	while (std::getline(in, line)) {
		size_t found = line.find('\r');
		while (found != std::string::npos) {
			line.replace(found, 1, "");
			found = line.find('\r', found + 1);
		}

		lines.push_back(line);
	}
	in.close();

	if (lines.size() < 1) {
		return "";
	}

	std::ofstream out(file_name, std::ios::binary);
	for (size_t i = 1; i < lines.size(); i++) {
		out << lines[i] << "\n";
	}
	out.close();

	return lines[0];
}

int main()
{
	std::string file_name;
	int number_of_notes, number_of_senders;

	std::cout << "Input binary file name:\n";
	std::cin >> file_name;

	std::ofstream out(file_name, std::ios::binary);
	out.close();

	std::cout << "Input number of notes:\n";
	std::cin >> number_of_notes;

	std::cout << "Input number of Sender Processes:\n";
	std::cin >> number_of_senders;

	HANDLE hMutex = CreateMutex(NULL, 0, L"mut ex");
	HANDLE* hEventStarted = new HANDLE[number_of_senders];

	for (int i = 0; i < number_of_senders; i++)
	{
		STARTUPINFO si = { 0 };
		PROCESS_INFORMATION piApp = { 0 };
		std::wstring CommandLine = (L"Sender.exe " + std::wstring(file_name.begin(),
			file_name.end()) + L" " + std::to_wstring(number_of_notes) + L" ") + std::to_wstring(i + 1);
		LPWSTR lpszCommandLine = &CommandLine[0];

		if (!CreateProcess(NULL, lpszCommandLine, NULL, NULL, TRUE,
			CREATE_NEW_CONSOLE, NULL, NULL, &si, &piApp))
		{
			std::cout << "The new process 'CREATOR' is not created.\n";
			std::cout << "Check a name of the process.\n";
			std::cout << "Press any key to finish.\n";
			_getch();
			return GetLastError();
		}
		hEventStarted[i] = CreateEvent(NULL, FALSE, FALSE, L"Process Started");

		if (hEventStarted[i] == NULL)
		{
			return GetLastError();
		}

		CloseHandle(piApp.hThread);
	}

	WaitForMultipleObjects(number_of_senders, hEventStarted, TRUE, INFINITE);
	ReleaseMutex(hMutex);

	std::cout << "\nInput 1 to read message;\nInput 0 to exit process\n";
	int key;
	int counter = 0;
	std::cin >> key;

	std::ifstream in(file_name, std::ios::in);

	while (true)
	{
		if (key == 1)
		{
			std::cout << "\nWe are waiting for a message, please do not press anything\n";
			while (isFileEmpty(file_name))
			{
				std::this_thread::sleep_for(std::chrono::milliseconds(100));
			}
			std::cout << "message from " << shiftLinesInFile(file_name) << "\n";

			std::cout << "\nInput 1 to read message;\nInput 0 to exit process\n";
			std::cin >> key;
		}
		else if (key == 0)
		{
			std::cout << "\nProcess ended.";
			break;
		}
		else
		{
			std::cout << "\nIncorrect value!\nInput 1 to read message;\nInput 0 to exit process\n";
			std::cin >> key;
		}
	}

	for (int i = 0; i < number_of_senders; i++)
	{
		CloseHandle(hEventStarted[i]);
	}

	return 0;
}