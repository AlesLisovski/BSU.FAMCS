#include "pch.h"
#include "CppUnitTest.h"
#include "C:\Users\alesl\OneDrive\Рабочий стол\Новая папка\UnitTest1\TestSuite\TestSuite.cpp"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace UnitTest1
{
	TEST_CLASS(CountMarkedElementsTest)
	{
	public:

		TEST_METHOD(BasicTest)
		{
			std::vector<int> arr = { 1, 2, 3, 4, 5, 4, 4, 6 };
			int value = 4;
			int result = count_marked_elements(arr, value);
			Assert::AreEqual(3, result);
		}

		TEST_METHOD(EmptyArrayTest)
		{
			std::vector<int> arr;
			int value = 4;
			int result = count_marked_elements(arr, value);
			Assert::AreEqual(0, result);
		}

		TEST_METHOD(NoMarkedElementsTest)
		{
			std::vector<int> arr = { 1, 2, 3, 5, 6 };
			int value = 4;
			int result = count_marked_elements(arr, value);
			Assert::AreEqual(0, result);
		}

		TEST_METHOD(AllMarkedElementsTest)
		{
			std::vector<int> arr = { 4, 4, 4, 4 };
			int value = 4;
			int result = count_marked_elements(arr, value);
			Assert::AreEqual(4, result);
		}
	};

	TEST_CLASS(ThreadDeleteResultTest)
	{
	public:

		TEST_METHOD(BasicTest)
		{
			std::vector<int> arr = { 1, 2, 3, 4, 5, 4, 4, 6 };
			int value = 4;
			std::vector<int> result = thread_delete_result(arr, value);
			std::vector<int> expected = { 1, 2, 3, 0, 5, 0, 0, 6 };
			Assert::IsTrue(expected ==  result);
		}

		TEST_METHOD(EmptyArrayTest)
		{
			std::vector<int> arr;
			int value = 4;
			std::vector<int> result = thread_delete_result(arr, value);
			std::vector<int> expected;
			Assert::IsTrue(expected == result);
		}

		TEST_METHOD(NoMarkedElementsTest)
		{
			std::vector<int> arr = { 1, 2, 3, 5, 6 };
			int value = 4;
			std::vector<int> result = thread_delete_result(arr, value);
			std::vector<int> expected = { 1, 2, 3, 5, 6 };
			Assert::IsTrue(expected == result);
		}

		TEST_METHOD(AllMarkedElementsTest)
		{
			std::vector<int> arr = { 4, 4, 4 };
			int value = 4;
			std::vector<int> result = thread_delete_result(arr, value);
			std::vector<int> expected = { 0, 0, 0 };
			Assert::IsTrue(expected == result);
		}
	};
}
