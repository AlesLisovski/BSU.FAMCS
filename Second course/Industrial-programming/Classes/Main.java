package org.example;

import java.util.*;
import java.lang.String;

public class Main {

    private static final String KEY = "1234password4321";

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        FileSettings InputFileSettings = new FileSettings();
        FileSettings OutputFileSettings = new FileSettings();

        ConsoleUserInterface.SetInputFileSettings(input, InputFileSettings);
        ConsoleUserInterface.ReadInputFile(InputFileSettings, input);

        MatchParser.ParseTxt(InputFileSettings,OutputFileSettings);

        ConsoleUserInterface.SetOutputFileSettings(input, OutputFileSettings);
        ConsoleUserInterface.WriteOutputFile(OutputFileSettings,input);
    }
}