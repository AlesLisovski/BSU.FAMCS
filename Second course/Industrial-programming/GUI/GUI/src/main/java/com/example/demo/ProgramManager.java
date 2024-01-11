package com.example.demo;

import java.util.Objects;

public class ProgramManager {
    private static final String KEY = "1234password4321";

    public static void ReadInputFile(FileSettings InputFileSettings) throws Exception {
        if (InputFileSettings.check_file_zip && InputFileSettings.check_file_encrypted) {
            if (Objects.equals(InputFileSettings.file_type, ".txt")) {
                InputFileSettings.byte_info = ArchiveManager.readBytesFromFileInFromArchive(InputFileSettings.archive_name, InputFileSettings.file_name);
                InputFileSettings.byte_info = FileEncryption.DecryptBytes(InputFileSettings.key, InputFileSettings.byte_info);
                InputFileSettings.txt_info = FileOperations.BytesToString(InputFileSettings.byte_info);
            } else if (Objects.equals(InputFileSettings.file_type, ".json")) {
                InputFileSettings.byte_info = ArchiveManager.readBytesFromFileInFromArchive(InputFileSettings.archive_name, InputFileSettings.file_name);
                InputFileSettings.byte_info = FileEncryption.DecryptBytes(InputFileSettings.key, InputFileSettings.byte_info);
                InputFileSettings.txt_info = FileOperations.BytesToString(InputFileSettings.byte_info);

                JsonParser parser = new JsonParser();

                InputFileSettings.txt_info = parser.ParseStringByParser(InputFileSettings.txt_info);
                InputFileSettings.byte_info = FileOperations.StringToBytes(InputFileSettings.txt_info);
            } else if (Objects.equals(InputFileSettings.file_type, ".xml")) {
                InputFileSettings.byte_info = ArchiveManager.readBytesFromFileInFromArchive(InputFileSettings.archive_name, InputFileSettings.file_name);
                InputFileSettings.byte_info = FileEncryption.DecryptBytes(InputFileSettings.key, InputFileSettings.byte_info);
                InputFileSettings.txt_info = FileOperations.BytesToString(InputFileSettings.byte_info);

                XmlParser parser = new XmlParser();

                InputFileSettings.txt_info = parser.ParseStringByDOM(InputFileSettings.txt_info);
                InputFileSettings.byte_info = FileOperations.StringToBytes(InputFileSettings.txt_info);
            }
        } else if (InputFileSettings.check_file_encrypted) {
            if (Objects.equals(InputFileSettings.file_type, ".txt")) {
                InputFileSettings.txt_info = FileEncryption.DecryptFileToString(InputFileSettings.key, InputFileSettings.file_name);
                InputFileSettings.byte_info = FileEncryption.DecryptFileToBytes(InputFileSettings.key, InputFileSettings.file_name);
            } else if (Objects.equals(InputFileSettings.file_type, ".json")) {
                InputFileSettings.txt_info = FileEncryption.DecryptFileToString(InputFileSettings.key, InputFileSettings.file_name);

                JsonParser parser = new JsonParser();
                InputFileSettings.txt_info = parser.ParseStringByParser(InputFileSettings.txt_info);
                InputFileSettings.byte_info = FileOperations.StringToBytes(InputFileSettings.txt_info);
            } else if (Objects.equals(InputFileSettings.file_type, ".xml")) {
                InputFileSettings.txt_info = FileEncryption.DecryptFileToString(InputFileSettings.key, InputFileSettings.file_name);

                XmlParser parser = new XmlParser();
                InputFileSettings.txt_info = parser.ParseStringByDOM(InputFileSettings.txt_info);
                InputFileSettings.byte_info = FileOperations.StringToBytes(InputFileSettings.txt_info);
            }
        } else if (InputFileSettings.check_file_zip) {
            if (Objects.equals(InputFileSettings.file_type, ".txt")) {
                InputFileSettings.txt_info = ArchiveManager.readStringFromInFileFromArchive(InputFileSettings.archive_name, InputFileSettings.file_name);
                InputFileSettings.byte_info = ArchiveManager.readBytesFromFileInFromArchive(InputFileSettings.archive_name, InputFileSettings.file_name);
            } else if (Objects.equals(InputFileSettings.file_type, ".json")) {
                InputFileSettings.txt_info = ArchiveManager.readStringFromInFileFromArchive(InputFileSettings.archive_name, InputFileSettings.file_name);

                JsonParser parser = new JsonParser();
                InputFileSettings.txt_info = parser.ParseStringByParser(InputFileSettings.txt_info);
                InputFileSettings.byte_info = FileOperations.StringToBytes(InputFileSettings.txt_info);
            } else if (Objects.equals(InputFileSettings.file_type, ".xml")) {
                InputFileSettings.txt_info = ArchiveManager.readStringFromInFileFromArchive(InputFileSettings.archive_name, InputFileSettings.file_name);

                XmlParser parser = new XmlParser();
                InputFileSettings.txt_info = parser.ParseStringByDOM(InputFileSettings.txt_info);
                InputFileSettings.byte_info = FileOperations.StringToBytes(InputFileSettings.txt_info);
            }
        } else {
            if (Objects.equals(InputFileSettings.file_type, ".txt")) {
                InputFileSettings.byte_info = FileOperations.readBytesFromFile(InputFileSettings.file_name);
                InputFileSettings.txt_info = FileOperations.readStringFromFile(InputFileSettings.file_name);
            } else if (Objects.equals(InputFileSettings.file_type, ".json")) {
                JsonParser parser = new JsonParser();
                InputFileSettings.txt_info = parser.ParseFileByParser(InputFileSettings.file_name);
                InputFileSettings.byte_info = FileOperations.StringToBytes(InputFileSettings.txt_info);
            } else if (Objects.equals(InputFileSettings.file_type, ".xml")) {
                XmlParser parser = new XmlParser();
                InputFileSettings.txt_info = parser.ParseFileByDOM(InputFileSettings.file_name);
                InputFileSettings.byte_info = FileOperations.StringToBytes(InputFileSettings.txt_info);
            }
        }
    }

    public static void WriteOutputFile(FileSettings OutputFileSettings) throws Exception {
        if (OutputFileSettings.check_file_zip && OutputFileSettings.check_file_encrypted) {
            ArchiveManager.writeBytesToArchive(FileEncryption.EncryptBytes(OutputFileSettings.key, OutputFileSettings.byte_info), OutputFileSettings.archive_name, OutputFileSettings.file_name);
            if (Objects.equals(OutputFileSettings.file_type, ".txt")) {
                ArchiveManager.writeBytesToArchive(FileEncryption.EncryptBytes(OutputFileSettings.key, OutputFileSettings.byte_info), OutputFileSettings.archive_name, OutputFileSettings.file_name);
            } else if (Objects.equals(OutputFileSettings.file_type, ".json")) {
                JsonParser parser = new JsonParser();

                OutputFileSettings.byte_info = FileOperations.StringToBytes(parser.makeJson(OutputFileSettings.txt_info));
                ArchiveManager.writeBytesToArchive(FileEncryption.EncryptBytes(OutputFileSettings.key, OutputFileSettings.byte_info), OutputFileSettings.archive_name, OutputFileSettings.file_name);
            } else if (Objects.equals(OutputFileSettings.file_type, ".xml")) {
                XmlParser parser = new XmlParser();

                OutputFileSettings.byte_info = FileOperations.StringToBytes(parser.makeXml(OutputFileSettings.txt_info));
                ArchiveManager.writeBytesToArchive(FileEncryption.EncryptBytes(OutputFileSettings.key, OutputFileSettings.byte_info), OutputFileSettings.archive_name, OutputFileSettings.file_name);
            }
        } else if (OutputFileSettings.check_file_encrypted) {
            if (Objects.equals(OutputFileSettings.file_type, ".txt")) {
                FileOperations.WriteBytesToFile(FileEncryption.EncryptBytes(OutputFileSettings.key, OutputFileSettings.byte_info), OutputFileSettings.file_name);
            } else if (Objects.equals(OutputFileSettings.file_type, ".json")) {
                JsonParser parser = new JsonParser();

                OutputFileSettings.byte_info = FileOperations.StringToBytes(parser.makeJson(OutputFileSettings.txt_info));
                FileOperations.WriteBytesToFile(FileEncryption.EncryptBytes(OutputFileSettings.key, OutputFileSettings.byte_info), OutputFileSettings.file_name);
            } else if (Objects.equals(OutputFileSettings.file_type, ".xml")) {
                XmlParser parser = new XmlParser();

                OutputFileSettings.byte_info = FileOperations.StringToBytes(parser.makeXml(OutputFileSettings.txt_info));
                FileOperations.WriteBytesToFile(FileEncryption.EncryptBytes(OutputFileSettings.key, OutputFileSettings.byte_info), OutputFileSettings.file_name);
            }
        } else if (OutputFileSettings.check_file_zip) {
            if (Objects.equals(OutputFileSettings.file_type, ".txt")) {
                ArchiveManager.writeStringToArchive(OutputFileSettings.txt_info, OutputFileSettings.archive_name, OutputFileSettings.file_name);
            } else if (Objects.equals(OutputFileSettings.file_type, ".json")) {
                JsonParser parser = new JsonParser();
                ArchiveManager.writeStringToArchive(parser.makeJson(OutputFileSettings.txt_info), OutputFileSettings.archive_name, OutputFileSettings.file_name);
            } else if (Objects.equals(OutputFileSettings.file_type, ".xml")) {
                XmlParser parser = new XmlParser();
                ArchiveManager.writeStringToArchive(parser.makeXml(OutputFileSettings.txt_info), OutputFileSettings.archive_name, OutputFileSettings.file_name);
            }
        } else {
            if (Objects.equals(OutputFileSettings.file_type, ".txt")) {
                FileOperations.WriteBytesToFile(OutputFileSettings.byte_info, OutputFileSettings.file_name);
            } else if (Objects.equals(OutputFileSettings.file_type, ".json")) {
                JsonParser parser = new JsonParser();
                FileOperations.WriteStringToFile(parser.makeJson(OutputFileSettings.txt_info), OutputFileSettings.file_name);
            } else if (Objects.equals(OutputFileSettings.file_type, ".xml")) {
                XmlParser parser = new XmlParser();
                FileOperations.WriteStringToFile(parser.makeXml(OutputFileSettings.txt_info), OutputFileSettings.file_name);
            }
        }
    }

    public static void ParseMathematicalExpressions(FileSettings InputFileSettings, FileSettings OutputFileSettings, String ParseType) throws Exception {
        MatchParserDirector matchParserDirector = new MatchParserDirector();

        switch (ParseType) {
            case "Parse by stack" -> matchParserDirector.SetBuilder(new MatchParserByStack());
            case "Parse by lib" -> matchParserDirector.SetBuilder(new MatchParserByLib());
            case "Parse by regular" -> matchParserDirector.SetBuilder(new MatchParserByRegular());
            default -> throw new IllegalStateException("Unexpected value: " + ParseType);
        }

        matchParserDirector.GetAnswer(InputFileSettings, OutputFileSettings);
    }

}
