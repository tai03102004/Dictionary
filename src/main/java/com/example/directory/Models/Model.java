package com.example.directory.Models;

import com.example.directory.Controllers.LoginController;
import com.example.directory.Views.ViewFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {

    private static Model model;
    private final ViewFactory viewFactory;


    // Client Data Selection
    private final Client client;
    private boolean clientLoginSuccessFlag;

    // Admin Data Selection

    private boolean adminLoginSuccessFlag;
    private final ObservableList<Client> clients;

    private final DatabaseConnection databaseConnection;
    private Model() {

        this.viewFactory = new ViewFactory();
        this.databaseConnection = new DatabaseConnection();
        // Client Data Section
        this.clientLoginSuccessFlag =false;
        this.adminLoginSuccessFlag = false;
        this.client = new Client("","","","","","","");
        this.clients =  FXCollections.observableArrayList();
        // Admin Data Section
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }

    /**
     * Client Method Section .
     */

    public boolean getClientLoginSuccessFlag() {
        return this.clientLoginSuccessFlag;
    }

    public void setClientLoginSuccessFlag(boolean clientLoginSuccessFlag) {
        this.clientLoginSuccessFlag = clientLoginSuccessFlag;
    }

    public Client getClient() {
        return this.client;
    }

    public void evaluateClientCred(String userName, String password) {

        ResultSet resultSet = databaseConnection.getClientData(userName,password);

        try {
            if (resultSet.next()) {
                this.client.userNameProperty().set(resultSet.getString("userName"));
                this.client.fullNameProperty().set(resultSet.getString("fullName"));
                this.clientLoginSuccessFlag = true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Admin Method Section .
     */

    public boolean getAdminLoginSuccessFlag() {
        return this.adminLoginSuccessFlag;
    }

    public void setAdminLoginSuccessFlag(boolean adminLoginSuccessFlag) {
        this.adminLoginSuccessFlag = adminLoginSuccessFlag;
    }

    public void evaluateAdminCred (String userName, String password) {
        ResultSet resultSet = databaseConnection.getAdminData(userName,password);
        try {
            if (resultSet.next()) {
                this.adminLoginSuccessFlag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Client> getClients() {
        return clients;
    }

    public void setClients() {
        ResultSet resultSet = databaseConnection.getAllClientsData();
        try {
            while (resultSet.next()) {
                String FullName = resultSet.getString("FullName");
                String UserName = resultSet.getString("UserName");
                String Email = resultSet.getString("Email");
                String Phone = resultSet.getString("Phone");
                String Question = resultSet.getString("Question");
                String Answer = resultSet.getString("Answer");
                String DateTime = resultSet.getString("DateTime");

                clients.add(new Client(FullName,UserName,Email,Phone,Question,Answer,DateTime));
                this.clientLoginSuccessFlag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
