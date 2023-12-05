package com.example.demo;

public class MatchParserDirector {
    MatchParserBuilder matchParserBuilder;

    public void SetBuilder(MatchParserBuilder matchParserBuilder) {
        this.matchParserBuilder = matchParserBuilder;
    }

    void GetAnswer(FileSettings InputFileSettings, FileSettings OutputFileSettings) throws Exception {
        matchParserBuilder.ParseTxt(InputFileSettings, OutputFileSettings);
    }
}
