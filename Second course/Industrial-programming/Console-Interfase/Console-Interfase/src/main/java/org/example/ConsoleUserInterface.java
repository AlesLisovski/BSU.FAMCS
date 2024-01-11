package org.example;

import java.util.Objects;
import java.util.Scanner;

public class ConsoleUserInterface {
    private static final String KEY = "1234password4321";

    public static void SetInputFileSettings(Scanner input, FileSettings InputFileSettings) throws Exception {
        String check;

        System.out.println("Is the input file archived? (Y/n) ");
        check = input.nextLine();
        InputFileSettings.check_file_zip = check.equals("Y");

        System.out.println("Is the input file encrypted? (Y/n) ");
        check = input.nextLine();
        InputFileSettings.check_file_encrypted = check.equals("Y");


        System.out.println("Enter input file type (.txt/.xml/.json) ");
        InputFileSettings.file_type = input.nextLine();

        if (!InputFileSettings.file_type.equals(".txt") && !InputFileSettings.file_type.equals(".json") && !InputFileSettings.file_type.equals(".xml")) {
            throw new Exception("Error file type");
        }

        if (InputFileSettings.check_file_zip) {
            System.out.println("Enter input archive name :");
            InputFileSettings.archive_name = input.nextLine();
        }

        System.out.println("Enter input file(without file type) :");
        InputFileSettings.file_name = input.nextLine() + InputFileSettings.file_type;
    }

    public static void SetOutputFileSettings(Scanner input, FileSettings OutputFileSettings) throws Exception {
        System.out.println("\nIs the output file archived? (Y/n) ");
        String check = input.nextLine();
        OutputFileSettings.check_file_zip = check.equals("Y");

        System.out.println("Is the output file encoded? (Y/n) ");
        check = input.nextLine();
        OutputFileSettings.check_file_encrypted = check.equals("Y");

        System.out.println("Enter output file type (.txt/.xml/.json) ");
        OutputFileSettings.file_type = input.nextLine();

        if (!OutputFileSettings.file_type.equals(".txt") && !OutputFileSettings.file_type.equals(".json") && !OutputFileSettings.file_type.equals(".xml")) {
            throw new Exception("Error file type");
        }

        if (OutputFileSettings.check_file_zip) {
            System.out.println("Enter output archive name :");
            OutputFileSettings.archive_name = input.nextLine();
        }

        System.out.println("Enter output file(without file type) : ");
        OutputFileSettings.file_name = input.nextLine() + OutputFileSettings.file_type;
    }

    public static void ReadInputFile(FileSettings InputFileSettings, Scanner input) throws Exception {
        boolean json_choice = true;
        boolean xml_choice = true;

        if (InputFileSettings.file_type.equals(".json")) {
            System.out.println("Select a parser for the json file: by json parser(0), or by reading line by line(1) (0/1) ");
            String choice = input.nextLine();

            switch (choice) {
                case "0" -> json_choice = true;
                case "1" -> json_choice = false;
                default -> throw new IllegalStateException("Unexpected value: " + choice);
            }
        }

        if (InputFileSettings.file_type.equals(".xml")) {
            System.out.println("Select a parser for the xml file: by DOM(0), or by reading line by line(1) (0/1) ");
            String choice = input.nextLine();

            switch (choice) {
                case "0" -> xml_choice = true;
                case "1" -> xml_choice = false;
                default -> throw new IllegalStateException("Unexpected value: " + choice);
            }
        }

        if (InputFileSettings.check_file_zip && InputFileSettings.check_file_encrypted) {
            if (Objects.equals(InputFileSettings.file_type, ".txt")) {
                InputFileSettings.byte_info = ArchiveManager.readBytesFromFileInArchive(InputFileSettings.archive_name, InputFileSettings.file_name);
                InputFileSettings.byte_info = FileEncryption.DecryptBytes(KEY, InputFileSettings.byte_info);
                InputFileSettings.txt_info = FileOperations.BytesToString(InputFileSettings.byte_info);
            } else if (Objects.equals(InputFileSettings.file_type, ".json")) {
                InputFileSettings.byte_info = ArchiveManager.readBytesFromFileInArchive(InputFileSettings.archive_name, InputFileSettings.file_name);
                InputFileSettings.byte_info = FileEncryption.DecryptBytes(KEY, InputFileSettings.byte_info);
                InputFileSettings.txt_info = FileOperations.BytesToString(InputFileSettings.byte_info);

                JsonParser parser = new JsonParser();
                if (json_choice) {
                    InputFileSettings.txt_info = parser.ParseStringByParser(InputFileSettings.txt_info);
                } else {
                    InputFileSettings.txt_info = parser.ParseStringByReadingLineByLine(InputFileSettings.txt_info);
                }

                InputFileSettings.byte_info = FileOperations.StringToBytes(InputFileSettings.txt_info);
            } else if (Objects.equals(InputFileSettings.file_type, ".xml")) {
                InputFileSettings.byte_info = ArchiveManager.readBytesFromFileInArchive(InputFileSettings.archive_name, InputFileSettings.file_name);
                InputFileSettings.byte_info = FileEncryption.DecryptBytes(KEY, InputFileSettings.byte_info);
                InputFileSettings.txt_info = FileOperations.BytesToString(InputFileSettings.byte_info);

                XmlParser parser = new XmlParser();
                if (xml_choice) {
                    InputFileSettings.txt_info = parser.ParseStringByDOM(InputFileSettings.txt_info);
                } else {
                    InputFileSettings.txt_info = parser.ParseStringByReadingLineByLine(InputFileSettings.txt_info);
                }

                InputFileSettings.byte_info = FileOperations.StringToBytes(InputFileSettings.txt_info);
            }
        } else if (InputFileSettings.check_file_encrypted) {
            if (Objects.equals(InputFileSettings.file_type, ".txt")) {
                InputFileSettings.txt_info = FileEncryption.DecryptFileToString(KEY, InputFileSettings.file_name);
                InputFileSettings.byte_info = FileEncryption.DecryptFileToBytes(KEY, InputFileSettings.file_name);
            } else if (Objects.equals(InputFileSettings.file_type, ".json")) {
                InputFileSettings.txt_info = FileEncryption.DecryptFileToString(KEY, InputFileSettings.file_name);

                JsonParser parser = new JsonParser();
                if (json_choice) {
                    InputFileSettings.txt_info = parser.ParseStringByParser(InputFileSettings.txt_info);
                } else {
                    InputFileSettings.txt_info = parser.ParseStringByReadingLineByLine(InputFileSettings.txt_info);
                }
                InputFileSettings.byte_info = FileOperations.StringToBytes(InputFileSettings.txt_info);
            } else if (Objects.equals(InputFileSettings.file_type, ".xml")) {
                InputFileSettings.txt_info = FileEncryption.DecryptFileToString(KEY, InputFileSettings.file_name);

                XmlParser parser = new XmlParser();
                if (xml_choice) {
                    InputFileSettings.txt_info = parser.ParseStringByDOM(InputFileSettings.txt_info);
                } else {
                    InputFileSettings.txt_info = parser.ParseStringByReadingLineByLine(InputFileSettings.txt_info);
                }
                InputFileSettings.byte_info = FileOperations.StringToBytes(InputFileSettings.txt_info);
            }
        } else if (InputFileSettings.check_file_zip) {
            if (Objects.equals(InputFileSettings.file_type, ".txt")) {
                InputFileSettings.txt_info = ArchiveManager.readStringFromFileInArchive(InputFileSettings.archive_name, InputFileSettings.file_name);
                InputFileSettings.byte_info = ArchiveManager.readBytesFromFileInArchive(InputFileSettings.archive_name, InputFileSettings.file_name);
            } else if (Objects.equals(InputFileSettings.file_type, ".json")) {
                InputFileSettings.txt_info = ArchiveManager.readStringFromFileInArchive(InputFileSettings.archive_name, InputFileSettings.file_name);

                JsonParser parser = new JsonParser();
                if (json_choice) {
                    InputFileSettings.txt_info = parser.ParseStringByParser(InputFileSettings.txt_info);
                } else {
                    InputFileSettings.txt_info = parser.ParseStringByReadingLineByLine(InputFileSettings.txt_info);
                }
                InputFileSettings.byte_info = FileOperations.StringToBytes(InputFileSettings.txt_info);
            } else if (Objects.equals(InputFileSettings.file_type, ".xml")) {
                InputFileSettings.txt_info = ArchiveManager.readStringFromFileInArchive(InputFileSettings.archive_name, InputFileSettings.file_name);

                XmlParser parser = new XmlParser();
                if (xml_choice) {
                    InputFileSettings.txt_info = parser.ParseStringByDOM(InputFileSettings.txt_info);
                } else {
                    InputFileSettings.txt_info = parser.ParseStringByReadingLineByLine(InputFileSettings.txt_info);
                }
                InputFileSettings.byte_info = FileOperations.StringToBytes(InputFileSettings.txt_info);
            }
        } else {
            if (Objects.equals(InputFileSettings.file_type, ".txt")) {
                InputFileSettings.byte_info = FileOperations.readBytesFromFile(InputFileSettings.file_name);
                InputFileSettings.txt_info = FileOperations.readStringFromFile(InputFileSettings.file_name);
            } else if (Objects.equals(InputFileSettings.file_type, ".json")) {
                JsonParser parser = new JsonParser();

                if (json_choice) {
                    InputFileSettings.txt_info = parser.ParseFileByParser(InputFileSettings.file_name);
                } else {
                    InputFileSettings.txt_info = parser.ParseFileByReadingLineByLine(InputFileSettings.file_name);
                }
                InputFileSettings.byte_info = FileOperations.StringToBytes(InputFileSettings.txt_info);
            } else if (Objects.equals(InputFileSettings.file_type, ".xml")) {
                XmlParser parser = new XmlParser();
                if (xml_choice) {
                    InputFileSettings.txt_info = parser.ParseFileByDOM(InputFileSettings.file_name);
                } else {
                    InputFileSettings.txt_info = parser.ParseFileByReadingLineByLine(InputFileSettings.file_name);
                }
                InputFileSettings.byte_info = FileOperations.StringToBytes(InputFileSettings.txt_info);
            }
        }
    }

    public static void WriteOutputFile(FileSettings OutputFileSettings, Scanner input) throws Exception {
        if (OutputFileSettings.check_file_zip && OutputFileSettings.check_file_encrypted) {
            ArchiveManager.writeBytesToArchive(FileEncryption.EncryptBytes(KEY, OutputFileSettings.byte_info), OutputFileSettings.archive_name, OutputFileSettings.file_name);
            if (Objects.equals(OutputFileSettings.file_type, ".txt")) {
                ArchiveManager.writeBytesToArchive(FileEncryption.EncryptBytes(KEY, OutputFileSettings.byte_info), OutputFileSettings.archive_name, OutputFileSettings.file_name);
            } else if (Objects.equals(OutputFileSettings.file_type, ".json")) {
                JsonParser parser = new JsonParser();

                OutputFileSettings.byte_info = FileOperations.StringToBytes(parser.makeJson(OutputFileSettings.txt_info));
                ArchiveManager.writeBytesToArchive(FileEncryption.EncryptBytes(KEY, OutputFileSettings.byte_info), OutputFileSettings.archive_name, OutputFileSettings.file_name);
            } else if (Objects.equals(OutputFileSettings.file_type, ".xml")) {
                XmlParser parser = new XmlParser();

                OutputFileSettings.byte_info = FileOperations.StringToBytes(parser.makeXml(OutputFileSettings.txt_info));
                ArchiveManager.writeBytesToArchive(FileEncryption.EncryptBytes(KEY, OutputFileSettings.byte_info), OutputFileSettings.archive_name, OutputFileSettings.file_name);
            }
        } else if (OutputFileSettings.check_file_encrypted) {
            if (Objects.equals(OutputFileSettings.file_type, ".txt")) {
                FileOperations.WriteBytesToFile(FileEncryption.EncryptBytes(KEY, OutputFileSettings.byte_info), OutputFileSettings.file_name);
            } else if (Objects.equals(OutputFileSettings.file_type, ".json")) {
                JsonParser parser = new JsonParser();

                OutputFileSettings.byte_info = FileOperations.StringToBytes(parser.makeJson(OutputFileSettings.txt_info));
                FileOperations.WriteBytesToFile(FileEncryption.EncryptBytes(KEY, OutputFileSettings.byte_info), OutputFileSettings.file_name);
            } else if (Objects.equals(OutputFileSettings.file_type, ".xml")) {
                XmlParser parser = new XmlParser();

                OutputFileSettings.byte_info = FileOperations.StringToBytes(parser.makeXml(OutputFileSettings.txt_info));
                FileOperations.WriteBytesToFile(FileEncryption.EncryptBytes(KEY, OutputFileSettings.byte_info), OutputFileSettings.file_name);
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

    public static void ParseMathematicalExpressions(FileSettings InputFileSettings, FileSettings OutputFileSettings, Scanner input) throws Exception {
        MatchParserDirector matchParserDirector = new MatchParserDirector();

        System.out.println("Select a match parser : MatchParserByStack(0), MatchParserByLib(1), or MatchParserByRegular(2) (0/1/2) ");
        String choice = input.nextLine();

        switch (choice) {
            case "0" -> matchParserDirector.SetBuilder(new MatchParserByStack());
            case "1" -> matchParserDirector.SetBuilder(new MatchParserByLib());
            case "2" -> matchParserDirector.SetBuilder(new MatchParserByRegular());
            default -> throw new IllegalStateException("Unexpected value: " + choice);
        }

        matchParserDirector.GetAnswer(InputFileSettings, OutputFileSettings);
    }

}
