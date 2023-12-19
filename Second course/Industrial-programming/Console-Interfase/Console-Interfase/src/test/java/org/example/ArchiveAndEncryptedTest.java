package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArchiveAndEncryptedTest {

    private static final String KEY = "1234password4321";

    @Test
    void readAndDecryptTxtFileFromArchive() throws Exception {
        byte[] input_bytes = ArchiveManager.readBytesFromFileInArchive("test_encrypted_txt.zip", "test_encrypted.txt");
        input_bytes = FileEncryption.DecryptBytes(KEY, input_bytes);
        String result = FileOperations.BytesToString(input_bytes);

        String str = """
                4+3
                1-1
                4*5
                20/2*5
                20/(2*5)
                (2/2-1+2)*2""";

        assertEquals(str, result);
    }

    @Test
    void readAndDecryptJsonFileFromArchive() throws Exception {
        byte[] input_bytes = ArchiveManager.readBytesFromFileInArchive("test_encrypted_json.zip", "test_encrypted.json");
        input_bytes = FileEncryption.DecryptBytes(KEY, input_bytes);
        String result = FileOperations.BytesToString(input_bytes);

        String str = """
                {
                  "MathematicalEquations": [
                    {
                      "equation": "4+3"
                    },
                    {
                      "equation": "1-1"
                    },
                    {
                      "equation": "4*5"
                    },
                    {
                      "equation": "20/2*5"
                    },
                    {
                      "equation": "20/(2*5)"
                    },
                    {
                      "equation": "(2/2-1+2)*2"
                    }
                  ]
                }""";

        assertEquals(str, result);
    }

    @Test
    void readAndDecryptXmlFileFromArchive() throws Exception {
        byte[] input_bytes = ArchiveManager.readBytesFromFileInArchive("test_encrypted_xml.zip", "test_encrypted.xml");
        input_bytes = FileEncryption.DecryptBytes(KEY, input_bytes);
        String result = FileOperations.BytesToString(input_bytes);

        String str = """
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <MathematicalEquations>
                    <equation>4+3</equation>
                    <equation>1-1</equation>
                    <equation>4*5</equation>
                    <equation>20/2*5</equation>
                    <equation>20/(2*5)</equation>
                    <equation>(2/2-1+2)*2</equation>
                </MathematicalEquations>""";

        assertEquals(str, result);
    }
}