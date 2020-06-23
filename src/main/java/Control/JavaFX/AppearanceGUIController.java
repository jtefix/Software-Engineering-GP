package Control.JavaFX;

import Control.Main;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javax.script.Bindings;
import javax.swing.text.html.CSS;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AppearanceGUIController implements Initializable {

    private static AppearanceGUIController instance;

    @FXML
    private VBox appearanceVBox;

    @FXML
    private Button setAppearanceButton;

    @FXML
    private Label fontSizeLabel;

    @FXML
    private Label fontTypeLabel;

    @FXML
    private Label fontColourLabel;

    @FXML
    private Label borderColourLabel;

    @FXML
    private Label backgroundColourLabel;

    @FXML
    private JFXComboBox fontTypePicker;

    @FXML
    private JFXComboBox fontSizePicker;

    @FXML
    private JFXColorPicker fontColourPicker;

    @FXML
    private JFXColorPicker borderColourPicker;

    @FXML
    private JFXColorPicker backgroundColourPicker;

    private MetricsGraphGUIController metricsGraph;

    //Arraylists
    private ArrayList<VBox> vBoxes;
    private ArrayList<Button> buttons;
    private ArrayList<Label> labels;
    private ArrayList<JFXComboBox> comboBoxes;
    private ArrayList<TextField> textFields;
    private ArrayList<RadioButton> radioButtons;

    public AppearanceGUIController(){
        instance = this;
        MainController.getInstance().setAppearanceGUIController(this);

        vBoxes = MainController.getInstance().getvBoxes();
        buttons = MainController.getInstance().getButtons();
        labels = MainController.getInstance().getLabels();
        comboBoxes = MainController.getInstance().getComboBoxes();
        textFields = MainController.getInstance().getTextFields();
        radioButtons = MainController.getInstance().getRadioButtons();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        MainController.getInstance().getvBoxes().add(appearanceVBox);
        MainController.getInstance().getLabels().add(fontSizeLabel);
        MainController.getInstance().getLabels().add(fontTypeLabel);
        MainController.getInstance().getLabels().add(fontColourLabel);
        MainController.getInstance().getLabels().add(borderColourLabel);
        MainController.getInstance().getLabels().add(backgroundColourLabel);
        MainController.getInstance().getButtons().add(setAppearanceButton);
        MainController.getInstance().getComboBoxes().add(fontTypePicker);
        MainController.getInstance().getComboBoxes().add(fontSizePicker);


        fontSizePicker.getItems().addAll(
                "12",
                "14",
                "16",
                "18"
        );

        fontSizePicker.setValue("12");


        fontTypePicker.getItems().addAll(javafx.scene.text.Font.getFamilies());
        fontTypePicker.setValue("Marlett");

        fontColourPicker.setValue(Color.valueOf("#212121"));
        borderColourPicker.setValue(Color.TOMATO);

    }


    private void changeFonts() {

        for(Label label: labels){
            Double fontSize = Double.valueOf(fontSizePicker.getValue().toString());
            String fontType = (String) fontTypePicker.getValue();
            label.setFont(new Font(fontType,fontSize));
            label.setTextFill(fontColourPicker.getValue());
        }

        for(Button button: buttons){
            Double fontSize = Double.valueOf(fontSizePicker.getValue().toString());
            String fontType = (String) fontTypePicker.getValue();
            button.setFont(new Font(fontType,fontSize));
            button.setTextFill(fontColourPicker.getValue());

        }

        for(ComboBox comboBox: comboBoxes){
            String newBackgroundFill = "#" + Integer.toHexString(backgroundColourPicker.getValue().hashCode());
            String newTextFill = "#" + Integer.toHexString(fontColourPicker.getValue().hashCode());
            String newStyle =
                    "-fx-text-base-color: " + newTextFill + ";"
                            + "-fx-background: " + newBackgroundFill + ";" ;
            comboBox.setStyle(newStyle);
        }

        for(RadioButton radioButton: radioButtons){
            Double fontSize = Double.valueOf(fontSizePicker.getValue().toString());
            String fontType = (String) fontTypePicker.getValue();
            radioButton.setFont(new Font(fontType,fontSize));
            radioButton.setTextFill(fontColourPicker.getValue());
        }

        for(TextField textField: textFields){
            Double newFontSize = Double.valueOf(fontSizePicker.getValue().toString());
            String newFontType = fontTypePicker.getValue().toString();
            String newBackgroundFill = "#" + Integer.toHexString(backgroundColourPicker.getValue().hashCode());
            String newTextFill = "#" + Integer.toHexString(fontColourPicker.getValue().hashCode());
            String newStyle =
                      "-fx-text-fill: " + newTextFill + ";"
                    + "-fx-background-color: " + newBackgroundFill + ";"
                    + "-fx-font-size: " + newFontSize + ";"
                    + "-fx-font-family: " + newFontType + ";" ;
            textField.setStyle(newStyle);
        }

    }


    private void changeVBoxes() {


        for (VBox vBox : vBoxes) {
            String newBackgroundColor = "#" + Integer.toHexString(backgroundColourPicker.getValue().hashCode());
            newBackgroundColor = newBackgroundColor.substring(0, 7);

            String newBorderColor = "#" + Integer.toHexString(borderColourPicker.getValue().hashCode());
            newBorderColor = newBorderColor.substring(0, 7);
            String newStyle = " -fx-border-opacity: 50; -fx-border-width: 25;"
                    + "-fx-border-color: " + newBorderColor + ";"
                    + "-fx-background-color: " + newBackgroundColor +";";
            vBox.setStyle(newStyle);
        }

        String newBackgroundFill = "#" + Integer.toHexString(borderColourPicker.getValue().hashCode());
        newBackgroundFill = newBackgroundFill.substring(0, 7);

        try {
            // Create a new tempfile that will be removed as the application exits
            File tempStyleClass = File.createTempFile("TempCSS", ".css");
            tempStyleClass.deleteOnExit();

            // Write the style-class inside
            try (PrintWriter printWriter = new PrintWriter(tempStyleClass)) {
                printWriter.println(".tab-header-background{ -fx-background-color: "+ newBackgroundFill + ";}"
                        + ".jfx-tab-pane .tab-selected-line{-fx-border-color: #ffff;}");
            }

            // Add the style-sheet and the style-class to the node
            MainController.getInstance().getTabPane().getStylesheets().add(tempStyleClass.toURI().toString());
            MainController.getInstance().getTabPane().getStyleClass().add("temp-style");

        } catch (IOException e1) {
            e1.printStackTrace();
        }



    }

        public void changeAppearance(ActionEvent actionEvent){

        changeFonts();
        changeVBoxes();

    }

}
