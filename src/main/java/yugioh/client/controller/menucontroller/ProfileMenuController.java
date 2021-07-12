package yugioh.client.controller.menucontroller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import yugioh.client.model.User;
import yugioh.client.model.exceptions.MenuException;
import yugioh.client.view.Responses;
import yugioh.client.view.SoundPlayable;
import yugioh.client.view.ViewInterface;
import yugioh.client.view.menus.PopUpWindow;
import yugioh.client.view.menus.WelcomeMenu;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.UUID;

public class ProfileMenuController extends MenuController implements Initializable {
    public static ProfileMenuController profileMenuController;
    public TextField nicknameField;
    public TextField oldPasswordField;
    public TextField newPasswordField;
    public Button changePasswordButton;
    public ImageView profileImage;
    public MediaView profileMenuBackground;
    boolean isNewPasswordFieldFilled = false;
    boolean isOldPasswordFieldFilled = false;

    public ProfileMenuController() {
    }

    public static ProfileMenuController getInstance() {
        if (profileMenuController == null) profileMenuController = new ProfileMenuController();
        return profileMenuController;
    }

    public void changeNickname(String nickname) throws MenuException {
        ViewInterface.showResult("profile change --nickname " + nickname);
        /*
        if (User.nicknameExists(nickname)) {
            throw new MenuException("Error: user with nickname " + nickname + " already exists");
        } else {
            User.loggedInUser.setNickname(nickname);
        }*/
    }

    public void changePassword(String currentPassword, String newPassword) throws MenuException {
        String response = ViewInterface.showResult("profile change --current "+currentPassword+" --new " +newPassword +" --password");
        System.out.println(response);
        if (!response.equals("password changed successfully!")){
            throw new MenuException(response);
        }
        /*
        if (!User.loggedInUser.getPassword().equals(currentPassword)) {
            SoundPlayable.playButtonSound("enterButton");
            throw new MenuException("Error: current password is invalid");
        } else if (newPassword.equals(currentPassword)) {
            SoundPlayable.playButtonSound("backButton");
            throw new MenuException("Error: please enter a new password");
        }*/
        User.loggedInUser.setPassword(newPassword);
    }


    public void backClicked(MouseEvent mouseEvent) throws Exception {
        SoundPlayable.playButtonSound("backButton");
        User.loggedInUser.setNickname(nicknameField.getText());
        mainMenu.execute();
    }

    public void changePasswordClicked(MouseEvent mouseEvent) throws Exception {
        SoundPlayable.playButtonSound("enterButton");
        if (oldPasswordField.getText().equals("") || newPasswordField.getText().equals("")) {
            new PopUpWindow(Responses.FILL_ALL_FIELDS.response).start(WelcomeMenu.stage);
        } else {
            String response = "";
            try {
                changePassword(oldPasswordField.getText(), newPasswordField.getText());
                response = Responses.PASSWORD_CHANGED_SUCCESSFULLY.response;
            } catch (MenuException e) {
                response = e.getMessage();
            }
            new PopUpWindow(response).start(WelcomeMenu.stage);
        }
        oldPasswordField.setText("");
        newPasswordField.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("src\\resources\\yugioh\\Backgrounds\\main.mp4").toURI().toString()));
        mediaPlayer.play();
        mediaPlayer.setCycleCount(-1);
        profileMenuBackground.setMediaPlayer(mediaPlayer);
//        System.out.println(User.loggedInUser.getProfileImageFile().toURI());
        try {
            Image image = new Image(User.loggedInUser.getProfileImageFile().toURI().toString());
            profileImage.setImage(image);
        } catch (Exception ignored) {
        }

        changePasswordButton.setDisable(true);
        newPasswordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.equals("")) {
                    isNewPasswordFieldFilled = false;
                    changePasswordButton.setDisable(true);
                } else {
                    isNewPasswordFieldFilled = true;
                    if (isOldPasswordFieldFilled) {
                        changePasswordButton.setDisable(false);
                    }
                }
            }
        });
        oldPasswordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.equals("")) {
                    isOldPasswordFieldFilled = false;
                    changePasswordButton.setDisable(true);
                } else {
                    isOldPasswordFieldFilled = true;
                    if (isNewPasswordFieldFilled) {
                        changePasswordButton.setDisable(false);
                    }
                }
            }
        });
    }

    public void changeImageClicked(MouseEvent mouseEvent) throws Exception {
        SoundPlayable.playButtonSound("enterButton");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("image files", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(WelcomeMenu.stage);
        if (file != null) {
            User.loggedInUser.setImageIsChanged();
            User.loggedInUser.deleteProfileImage();
            Path sourceDirectory = Paths.get(file.getPath());
            UUID random = UUID.randomUUID();
            String imageAddress = "/yugioh/PNG/UsersImage/" + random + ".png";
            Path targetDirectory = Paths.get("src\\resources\\yugioh\\PNG\\UsersImage\\" + random + ".png");
            Files.copy(sourceDirectory, targetDirectory);
            profileImage.setImage(new Image(file.toURI().toString()));
            User.loggedInUser.setProfileImage(imageAddress);
            User.loggedInUser.setProfileImageFile(file);
        }
    }
}
