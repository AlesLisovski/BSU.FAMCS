package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchParserDirectorTest {

    @Test
    void getAnswerByStack() throws Exception {
        FileSettings InputFileSettings = new FileSettings();
        FileSettings OutputFileSettings = new FileSettings();

        InputFileSettings.txt_info = """
                4+3
                1-1
                4*5
                20/2*5
                20/(2*5)
                (2/2-1+2)*2
                """;

        MatchParserDirector matchParserDirector = new MatchParserDirector();
        matchParserDirector.SetBuilder(new MatchParserByStack());

        matchParserDirector.GetAnswer(InputFileSettings, OutputFileSettings);

        String result = """
                7.0
                0.0
                20.0
                50.0
                2.0
                4.0
                """;

        assertEquals(result, OutputFileSettings.txt_info);
    }


    @Test
    void getAnswerByLib() throws Exception {
        FileSettings InputFileSettings = new FileSettings();
        FileSettings OutputFileSettings = new FileSettings();

        InputFileSettings.txt_info = """
                4+3
                1-1
                4*5
                20/2*5
                20/(2*5)
                (2/2-1+2)*2
                """;

        MatchParserDirector matchParserDirector = new MatchParserDirector();
        matchParserDirector.SetBuilder(new MatchParserByLib());

        matchParserDirector.GetAnswer(InputFileSettings, OutputFileSettings);

        String result = """
                7.0
                0.0
                20.0
                50.0
                2.0
                4.0
                """;

        assertEquals(result, OutputFileSettings.txt_info);
    }


    @Test
    void getAnswerByRegular() throws Exception {
        FileSettings InputFileSettings = new FileSettings();
        FileSettings OutputFileSettings = new FileSettings();

        InputFileSettings.txt_info = """
                4+3
                1-1
                4*5
                20/2*5
                20/(2*5)
                (2/2-1+2)*2
                """;

        MatchParserDirector matchParserDirector = new MatchParserDirector();
        matchParserDirector.SetBuilder(new MatchParserByRegular());

        matchParserDirector.GetAnswer(InputFileSettings, OutputFileSettings);

        String result = """
                7.0
                0.0
                20.0
                50.0
                2.0
                4.0
                """;

        assertEquals(result, OutputFileSettings.txt_info);
    }
}