package com.example.directory.Models;

import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client {
    private final StringProperty userName;
    private final StringProperty fullName;
    private final StringProperty emailName;
    private final StringProperty phoneName;
    private final StringProperty sexName;

    public Client(String uName, String fName, String eName, String pName, String sName) {

        this.userName = new SimpleStringProperty(this,"userName",uName);
        this.fullName = new SimpleStringProperty(this,"fullName",fName);
        this.emailName = new SimpleStringProperty(this,"emailName",eName);
        this.phoneName = new SimpleStringProperty(this,"phoneName",pName);
        this.sexName = new SimpleStringProperty(this,"sexName",sName);
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

    public StringProperty sexNameProperty() {
        return sexName;
    }

}
