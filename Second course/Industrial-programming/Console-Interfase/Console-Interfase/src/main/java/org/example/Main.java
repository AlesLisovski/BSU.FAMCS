package org.example;

import java.util.*;
import java.lang.String;

public class Main {

    private static final String KEY = "1234password4321";

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        FileSettings InputFileSettings = new FileSettings();
        FileSettings OutputFileSettings = new FileSettings();

        try {
            ConsoleUserInterface.SetInputFileSettings(input, InputFileSettings);
            ConsoleUserInterface.ReadInputFile(InputFileSettings, input);

            ConsoleUserInterface.ParseMathematicalExpressions(InputFileSettings, OutputFileSettings, input);

            ConsoleUserInterface.SetOutputFileSettings(input, OutputFileSettings);
            ConsoleUserInterface.WriteOutputFile(OutputFileSettings, input);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}