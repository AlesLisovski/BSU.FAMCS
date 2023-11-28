package org.example;

public abstract class MatchParserBuilder {

    abstract void ParseTxt(FileSettings InputFileSettings, FileSettings OutputFileSettings) throws Exception;
}
