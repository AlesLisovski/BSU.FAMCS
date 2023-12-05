package com.example.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        stage.setTitle("Application");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}


    /*
    @FXML
    void InputFileArchivedAction(ActionEvent event) {
        InputArchiveNameTextField.setVisible(InputArchivedCheckBox.isSelected());
    }

    @FXML
    void OutputFileArchivedAction(ActionEvent event) {
        OutputArchiveNameTextField.setVisible(OutputArchivedCheckBox.isSelected());
    }

    private FileSettings InputFileSettings;

    private FileSettings OutputFileSettings;

    @FXML
    void ParseFile(ActionEvent event) throws Exception {
        InputFileSettings.check_file_zip = InputArchivedCheckBox.isSelected();
        InputFileSettings.check_file_encrypted = InputEncryptedCheckBox.isSelected();
        InputFileSettings.file_type = InputFileTypeChoiceBox.getValue();
        InputFileSettings.file_name = InputFileNameTextField.getText() + InputFileSettings.file_type;
        InputFileSettings.archive_name = InputArchiveNameTextField.getText();

        OutputFileSettings.check_file_zip = OutputArchivedCheckBox.isSelected();
        OutputFileSettings.check_file_encrypted = OutputEncryptedCheckBox.isSelected();
        OutputFileSettings.file_type = OutputFileTypeChoiceBox.getValue();
        OutputFileSettings.file_name = OutputFileNameTextField.getText() + OutputFileSettings.file_type;
        OutputFileSettings.archive_name = OutputArchiveNameTextField.getText();

        ConsoleUserInterface.ReadInputFile(InputFileSettings);

        String ParseType = "";
        if (ParseByLibRadioButton.isSelected()) {
            ParseType = ParseByLibRadioButton.getText();
        } else if (ParseByStackRadioButton.isSelected()) {
            ParseType = ParseByStackRadioButton.getText();
        } else if (ParseByRegularRadioButton.isSelected()) {
            ParseType = ParseByRegularRadioButton.getText();
        }

        ConsoleUserInterface.ParseMathematicalExpressions(InputFileSettings, OutputFileSettings, ParseType);

        ConsoleUserInterface.WriteOutputFile(OutputFileSettings);
    }

    @FXML
    void initialize() {
        String[] types = {".txt", ".json", ".xml"};
        InputFileTypeChoiceBox.getItems().addAll(types);
        InputFileTypeChoiceBox.setValue(".txt");

        OutputFileTypeChoiceBox.getItems().addAll(types);
        OutputFileTypeChoiceBox.setValue(".txt");

        InputArchiveNameTextField.setVisible(false);
        OutputArchiveNameTextField.setVisible(false);

        InputFileSettings = new FileSettings();
        OutputFileSettings = new FileSettings();
    }

     */