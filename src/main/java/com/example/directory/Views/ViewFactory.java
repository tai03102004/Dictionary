package com.example.directory.Views;

import com.example.directory.Controllers.Admin.AdminController;
import com.example.directory.Controllers.client.ClientController;
import com.example.directory.Models.Model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    private AccountType loginAccountType;

    // Client View

    private final ObjectProperty<ClientMenuOption> clientSelectedMenuItem;
    private AnchorPane homeView;
    private AnchorPane translateView;

    private AnchorPane profileView;
    private AnchorPane gameView;
    private AnchorPane textTransactionView;

    private AnchorPane historyView;

    private AnchorPane savedView;
    private AnchorPane settingView;

    // Admin Views
    private final ObjectProperty<AdminMenuOption> adminSelectedMenuItem;
    private AnchorPane clientsView;

    private AnchorPane addEntryView;
    private AnchorPane updateEntryView;

    private AnchorPane reportEntryView;


    public ViewFactory(){
        this.loginAccountType = AccountType.CLIENT;
        this.clientSelectedMenuItem = new SimpleObjectProperty<>();
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
    }

    public AccountType getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    public ObjectProperty<ClientMenuOption> getClientSelectedMenuItem() {

        return clientSelectedMenuItem;
    }

    // Client

    // Trang chính
    public AnchorPane getHomeView() {
        System.out.println("getHomeView");
        if (homeView == null ){
            try {
                homeView = new FXMLLoader(getClass().getResource("/Fxml/Client/Dashboard.fxml")).load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return homeView;
    }
    // Trang tra lịch sử
    public AnchorPane getHistoryView () {
        System.out.println("getHistoryView");
        if (historyView == null) {
            try {
                historyView = new FXMLLoader(getClass().getResource("/Fxml/Client/History.fxml")).load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return historyView;
    }

    // Trang lưu từ

    public AnchorPane getSavedView () {
        System.out.println("getHistoryView");
        if (savedView == null) {
            try {
                savedView = new FXMLLoader(getClass().getResource("/Fxml/Client/Saved.fxml")).load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return savedView;
    }

    // Tra từ
    public AnchorPane getTextTransactionView() {
        if (textTransactionView == null ){
            try {
                textTransactionView = new FXMLLoader(getClass().getResource("/Fxml/Client/textTransaction.fxml")).load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return textTransactionView;
    }
    // Dịch văn bản
    public AnchorPane getTranslateView() {
        System.out.println("getTranslateView");
        if (translateView == null ){
            try {
                translateView = new FXMLLoader(getClass().getResource("/Fxml/Client/Translate.fxml")).load();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return  translateView;
    }
    // Game
    public AnchorPane getGameView() {
        System.out.println("Get Game");
        if (gameView == null ) {
            try {
                gameView = new FXMLLoader(getClass().getResource("/Fxml/Client/Topic.fxml")).load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return gameView;
    }

    // Setting

    public AnchorPane getSettingView() {
        if (settingView == null ) {
            try {
                settingView = new FXMLLoader(getClass().getResource("/Fxml/Client/setting.fxml")).load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return settingView;
    }

    // Trang tài khoản
    public AnchorPane getAccountView() {
        System.out.println("profileView");
        if (profileView == null ){
            try {
                profileView = new FXMLLoader(getClass().getResource("/Fxml/Client/Accounts.fxml")).load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return profileView;
    }

    // Phía giao diện Client

    public void showClientWindow() {
        System.out.println("showClientWindow");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader);
    }


    /**
     * Admin View Section
     */
    public ObjectProperty<AdminMenuOption> getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    // Thêm từ

    public AnchorPane getAddEntryView() {
        System.out.println("AddEntryView");
        if (addEntryView == null) {
            try {
                addEntryView = new FXMLLoader(getClass().getResource("/Fxml/Admin/AdminAddEntry.fxml")).load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return addEntryView;
    }

    // Cập nhật từ (Sửa Xoá)

    public AnchorPane getUpdateEntryView() {
        System.out.println("UpdateEntryView");
        if (updateEntryView == null) {
            try {
                updateEntryView = new FXMLLoader(getClass().getResource("/Fxml/Admin/AdminUpdateEntry.fxml")).load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return updateEntryView;
    }
    // Đánh giá bên phía người dùng
    public AnchorPane getReportClient() {
        System.out.println("ReportClientView");
        if (reportEntryView == null) {
            try {
                reportEntryView = new FXMLLoader(getClass().getResource("/Fxml/Admin/ReportClient.fxml")).load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return reportEntryView;
    }
    // Thông tin tài khoản bên CLient (sql)
    public AnchorPane getClientsView() {
        System.out.println("getClientsView");
        if(clientsView == null) {
            try {
                clientsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Clients.fxml")).load();
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return clientsView;
    }

    // Giao diện trang Admin
    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Admin.fxml"));
        AdminController controller = new AdminController();
        loader.setController(controller);
        createStage(loader);
    }

    // Đăng nhập

    public void showLoginWindow() {

            System.out.println("showLoginWindow");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/login.fxml"));
            createStage(loader);

    }

    // Đăng ký

    public void showSignUpWindow() {
        if (Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.CLIENT) {
            System.out.println("showSignUpWindow");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/signup.fxml"));
            createStage(loader);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
            createStage(loader);
        }
    }


    public void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/icon.jpg"))));
        stage.setResizable(false);
        stage.setTitle("Dictionary");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }

}
