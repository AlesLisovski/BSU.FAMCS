#include <iostream>
#include <fstream>
#include <Windows.h>
#include <conio.h>
#include <string>

struct employee
{
	int num = 0;
	char name[10] = "";
	double hours = 0;
};

std::istream& operator >>(std::istream& in, employee& person)
{
	in >> person.num >> person.name >> person.hours;
	return in;
}

std::ostream& operator << (std::ostream& os, employee& person)
{
	return os << person.num << " " << person.name << " " << person.hours;
}

int main()
{
	std::string binary_file;
	int number_of_records;

	std::cout << "Enter the name of the binary file: ";
	std::cin >> binary_file;
	std::cout << "Enter the number of records in this file: ";
	std::cin >> number_of_records;

	STARTUPINFO si = { 0 };
	PROCESS_INFORMATION piApp = { 0 };

	std::wstring CommandLine = (L"CREATOR.exe " + std::wstring(binary_file.begin(),
		binary_file.end()) + L" " + std::to_wstring(number_of_records));
	LPWSTR lpszCommandLine = &CommandLine[0];

	if (!CreateProcess(NULL, lpszCommandLine, NULL, NULL, FALSE,
		CREATE_NEW_CONSOLE, NULL, NULL, &si, &piApp))
	{
		std::cout << "The new process 'CREATOR' is not created.\n";
		std::cout << "Check a name of the process.\n";
		std::cout << "Press any key to finish.\n";
		_getch();
		return 0;
	}

	std::cout << "The CREATOR is created.\n";

	WaitForSingleObject(piApp.hProcess, INFINITE);
	CloseHandle(piApp.hThread);
	CloseHandle(piApp.hProcess);

	std::cout << "\nContents of the binary file: \n";
	std::ifstream in(binary_file, std::ios::binary);
	employee worker;

	while (!in.eof())
	{
		in >> worker;
		std::cout << worker<<"\n";
	}
	in.close();

	std::string report_file;
	double payment;
	std::cout << "\nEnter the name of the report file: ";
	std::cin >> report_file;
	std::cout << "Enter payment per hour of work: ";
	std::cin >> payment;

	si = { 0 };
	piApp = { 0 };

	CommandLine = (L"REPORTER.exe " + std::wstring(binary_file.begin(), binary_file.end()) + L" " +
		std::wstring(report_file.begin(), report_file.end()) + L" " + std::to_wstring(payment));
	lpszCommandLine = &CommandLine[0];


	if (!CreateProcess(NULL, lpszCommandLine, NULL, NULL, FALSE,
		CREATE_NEW_CONSOLE, NULL, NULL, &si, &piApp))
	{
		std::cout << "The new process 'REPORTER' is not created.\n";
		std::cout << "Check a name of the process.\n";
		std::cout << "Press any key to finish.\n";
		_getch();
		return 0;
	}

	std::cout << "The REPORTER is created.\n";

	WaitForSingleObject(piApp.hProcess, INFINITE);
	CloseHandle(piApp.hThread);
	CloseHandle(piApp.hProcess);

	std::cout << "\nContents of the report file: \n";

	double salary;
	std::string line;
	std::ifstream fin(report_file);
	std::getline(fin, line);
	std::cout << line << "\n";

	for (int i = 0; i < number_of_records; i++)
	{
		fin >> worker >> salary;
		std::cout << worker << " " << salary << "\n";
	}
	fin.close();

	return 0;

}