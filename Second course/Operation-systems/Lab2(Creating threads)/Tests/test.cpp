#include "pch.h"
#include "C:\Users\alesl\OneDrive\Рабочий стол\Новая папка\Lab2-Test\TestSuite\funcToTest.cpp" 

TEST(MinMaxTest, BasicTest) {
	std::vector<int> vec = { 3, 1, 4, 1, 5, 9 };
	int min, max;
	min_max(min, max, vec);
	ASSERT_EQ(min, 1);
	ASSERT_EQ(max, 9);
}

TEST(MinMaxTest, EmptyVectorTest) {
	std::vector<int> vec;
	int min, max;
	min_max(min, max, vec);
	ASSERT_EQ(min, 0); 
	ASSERT_EQ(max, 0);
}

TEST(MinMaxTest, MinMaxSameValueTest) {
	std::vector<int> vec = { 3, 3, 3, 3 };
	int min, max;
	min_max(min, max, vec);
	ASSERT_EQ(min, 3);
	ASSERT_EQ(max, 3);
}

TEST(AverageTest, BasicTest) {
	std::vector<int> vec = { 3, 1, 4, 1, 5, 9 };
	int average = 0;
	Average(average, vec);
	ASSERT_EQ(average, 3);
}

TEST(AverageTest, EmptyVectorTest) {
	std::vector<int> vec;
	int average = 0;
	Average(average, vec);
	ASSERT_EQ(average, 0);
}

TEST(AverageTest, LargeVectorTest) {
	std::vector<int> vec(10000, 5); 
	int average = 0;
	Average(average, vec);
	ASSERT_EQ(average, 5); 
}
