#include <iostream>
#include <string>
#include <Windows.h>
#include <conio.h>
#include <fstream>

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


int main(int argc, char* argv[])
{
	std::cout << "CREATOR started\n";

	int number_of_records = std::stoi(argv[2]);
	std::string binary_file = argv[1];
	std::ofstream out(binary_file,std::ios::binary);
	employee worker;

	std::cout << "\nEnter information about " << number_of_records << " employees";

	for (int i = 0; i < number_of_records - 1; i++)
	{
		std::cout << "\nEnter data(remaining " << number_of_records - i << "):";
		std::cin >> worker;
		out << worker << "\n";
	}

	std::cout << "\nEnter data(remaining 1):";
	std::cin >> worker;
	out << worker;

	out.close();

	std::cout << "Enter something to close CREATOR";
	_getch();
}