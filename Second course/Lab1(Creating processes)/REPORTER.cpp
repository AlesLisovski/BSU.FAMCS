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
	std::cout << "REPORTER started\n";

	double payment = std::stof(argv[3]);
	std::string report_file = argv[2];
	std::string binary_file = argv[1];

	std::ifstream in(binary_file, std::ios::binary);
	std::ofstream out(report_file);
	employee worker;

	out << "Report on " << report_file << " file\n";

	while (!in.eof())
	{
		in >> worker;
		out << worker<<" " << worker.hours * payment<<"\n";
	}

	out.close();
	in.close();

	std::cout << "Enter something to close REPORTER";
	_getch();
}