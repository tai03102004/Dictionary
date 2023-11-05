package Views;

import Controllers.Admin.AdminController;
import Controllers.client.ClientController;
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

    // Admin Views
    private final ObjectProperty<AdminMenuOption> adminSelectedMenuItem;
    private AnchorPane createClientView;

    private AnchorPane clientsView;

    private AnchorPane addEntryView;
    private AnchorPane settingView;

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

    public AnchorPane getGameView() {
        System.out.println("Get Game");
        if (gameView == null ) {
            try {
                gameView = new FXMLLoader(getClass().getResource("/Fxml/Client/Game.fxml")).load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return gameView;
    }

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

    public AnchorPane getCreateClientView() {
        System.out.println("CreateClientView");
        if (createClientView == null) {
            try {
                createClientView = new FXMLLoader(getClass().getResource("/Fxml/Admin/CreateClient.fxml")).load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return createClientView;
    }

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
    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Admin.fxml"));
        AdminController controller = new AdminController();
        loader.setController(controller);
        createStage(loader);
    }

    public void showLoginWindow() {
        System.out.println("showLoginWindow");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/login.fxml"));
        createStage(loader);
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
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/icon.png"))));
        stage.setResizable(false);
        stage.setTitle("Dictionary");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }

}
