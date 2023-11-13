package com.example.directory.Models;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;

public class Client implements Initializable {
    private final StringProperty userName;
    private final StringProperty fullName;
    private final StringProperty emailName;
    private final StringProperty phoneName;

    private final StringProperty question;

    private final StringProperty answer;

    private final StringProperty dateTime;
    public Client(String uName, String fName, String eName, String pName,String qt,String as,String date) {

        this.userName = new SimpleStringProperty(this,"userName",uName);
        this.fullName = new SimpleStringProperty(this,"fullName",fName);
        this.emailName = new SimpleStringProperty(this,"emailName",eName);
        this.phoneName = new SimpleStringProperty(this,"phoneName",pName);
        this.question = new SimpleStringProperty(this,"question",qt);
        this.answer = new SimpleStringProperty(this,"answer",as);
        this.dateTime = new SimpleStringProperty(this,"dateTime",date);
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }

    public StringProperty emailNameProperty() {
        return emailName;
    }

    public StringProperty phoneNameProperty() {
        return phoneName;
    }

    public StringProperty questionProperty() {
        return question;
    }

    public StringProperty answerProperty() {
        return answer;
    }

    public StringProperty dateTimeProperty() {
        return dateTime;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
