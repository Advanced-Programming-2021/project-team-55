package yugioh.controller.menucontroller;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.w3c.dom.Text;
import yugioh.model.exceptions.MenuException;
import yugioh.model.User;
import yugioh.view.Menus.PopUpWindow;
import yugioh.view.Menus.WelcomeMenu;
import yugioh.view.Menus.MenuType;
import yugioh.view.Responses;

public class ProfileMenuController extends MenuController {
    public static ProfileMenuController profileMenuController;
    public TextField nicknameField;
    public TextField oldPasswordField;
    public TextField newPasswordField;

    public ProfileMenuController() {
    }

    public static ProfileMenuController getInstance() {
        if (profileMenuController == null) profileMenuController = new ProfileMenuController();
        return profileMenuController;
    }

    public void changeNickname(String nickname) throws MenuException {
        if (User.nicknameExists(nickname)) {
            throw new MenuException("Error: user with nickname " + nickname + " already exists");
        } else {
            User.loggedInUser.setNickname(nickname);
        }
    }

    public void changePassword(String currentPassword, String newPassword) throws MenuException {
        if (!User.loggedInUser.getPassword().equals(currentPassword)) {
            throw new MenuException("Error: current password is invalid");
        } else if (newPassword.equals(currentPassword)) {
            throw new MenuException("Error: please enter a new password");
        }
        User.loggedInUser.setPassword(newPassword);
    }


    public void backClicked(MouseEvent mouseEvent) throws Exception{
        User.loggedInUser.setNickname(nicknameField.getText());
        mainMenu.execute();
    }

    public void changePasswordClicked(MouseEvent mouseEvent) throws Exception{
        if(oldPasswordField.getText().equals("")||newPasswordField.getText().equals("")){
            new PopUpWindow(Responses.FILL_ALL_FIELDS.response).start(WelcomeMenu.stage);
        }
        else{
            String response="";
            try {
                changePassword(oldPasswordField.getText(),newPasswordField.getText());
                response=Responses.PASSWORD_CHANGED_SUCCESSFULLY.response;
            }catch (MenuException e){
                response=e.getMessage();
            }
            new PopUpWindow(response).start(WelcomeMenu.stage);
        }
        oldPasswordField.setText("");
        newPasswordField.setText("");
    }
}
