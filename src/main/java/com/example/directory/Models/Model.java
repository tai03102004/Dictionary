package com.example.directory.Models;

import com.example.directory.Views.ViewFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {

    private static Model model;
    private final ViewFactory viewFactory;


    // Client Data Selection
    private final Client client;
    private final WordListReportClient reportClient;
    private boolean clientLoginSuccessFlag;

    // Admin Data Selection

    private boolean adminLoginSuccessFlag;
    private final ObservableList<Client> clients;
    private final ObservableList<WordListReportClient> reportClients;

    private final DatabaseConnection databaseConnection;
    public interface DatabaseChangeListener {
        void onDataChange();
    }

    private List<DatabaseChangeListener> databaseChangeListeners = new ArrayList<>();

    public void addDatabaseChangeListener(DatabaseChangeListener listener) {
        databaseChangeListeners.add(listener);
    }

    public void notifyDatabaseChange() {
        for (DatabaseChangeListener listener : databaseChangeListeners) {
            listener.onDataChange();
        }
    }
    private Model() {

        this.viewFactory = new ViewFactory();
        this.databaseConnection = new DatabaseConnection();
        // Client Data Section
        this.clientLoginSuccessFlag =false;
        this.adminLoginSuccessFlag = false;
        this.client = new Client("","","","","","");
        this.clients =  FXCollections.observableArrayList();
        this.reportClient = new WordListReportClient("","");
        this.reportClients = FXCollections.observableArrayList();
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

    public ObservableList<WordListReportClient> getWordListReportClient() {
        return this.reportClients;
    }
    public void setWordListReportClient() {
        ResultSet resultSet = databaseConnection.getAllClientsDataReport();
        try {
            while (resultSet.next()) {
                String Text = resultSet.getString("Text");
                String userName = resultSet.getString("UserNameClient");

                reportClients.add(new WordListReportClient(Text,userName));
                this.clientLoginSuccessFlag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
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

                clients.add(new Client(FullName,UserName,Email,Phone,Question,Answer));
                this.clientLoginSuccessFlag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
