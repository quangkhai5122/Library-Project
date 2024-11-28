package main.java.login;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import main.java.alert.AlertMess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {

    @FXML
    private Button change_backBtn;

    @FXML
    private PasswordField change_comfirmPassword;

    @FXML
    private AnchorPane change_form;

    @FXML
    private PasswordField change_password;

    @FXML
    private Button change_proceedBtn;

    @FXML
    private TextField forget_Answer;

    @FXML
    private Button forget_backBtn;

    @FXML
    private AnchorPane forget_form;

    @FXML
    private Button forget_proceedBtn;

    @FXML
    private ComboBox<?> forget_selectQuestion;

    @FXML
    private TextField forget_username;

    @FXML
    private Button login_btn;

    @FXML
    private Button login_createAccount;

    @FXML
    private Hyperlink login_forgotPassword;

    @FXML
    private AnchorPane login_form;

    @FXML
    private PasswordField login_password;

    @FXML
    private CheckBox login_selectShowPassword;

    @FXML
    private TextField login_showPassword;

    @FXML
    private TextField login_username;

    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField signup_answer;

    @FXML
    private Button signup_btn;

    @FXML
    private PasswordField signup_confirmPassword;

    @FXML
    private TextField signup_email;

    @FXML
    private AnchorPane signup_form;

    @FXML
    private Button signup_loginAccount;

    @FXML
    private PasswordField signup_password;

    @FXML
    private ComboBox<?> signup_selectQuestion;

    @FXML
    private TextField signup_username;

    private Connection connect;
    private PreparedStatement prep;
    private ResultSet result;
    private Statement statement;

    // Connect to database library
    public Connection connectDatabase() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/library",
                    "root", "");
            return connect;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Handle login function
    public void login() {
        AlertMess alert = new AlertMess();

        if (login_username.getText().isEmpty() || login_password.getText().isEmpty()) {
            alert.errorMessage("Error, All fields must be filled");
        } else {
            String dataUser = "SELECT username, password FROM user WHERE username = ? and password = ?";
            connect = connectDatabase();
            try {
                prep = connect.prepareStatement(dataUser);
                prep.setString(1, login_username.getText());
                prep.setString(2, login_password.getText());
                result = prep.executeQuery();
                if (result.next()) {
                    alert.successMessage("Successfully Login");

                    // Hide login form
                    login_btn.getScene().getWindow().hide();

                    // Show dashboard form
                    Parent root = FXMLLoader.load(getClass().getResource("/dashboard/DashboardManager.fxml"));
                    Stage stage = new Stage();

                    stage.initStyle(StageStyle.UNDECORATED); 

                    Scene scene = new Scene(root);
                    stage.setScene(scene);

                    stage.show();

                } else {
                    alert.errorMessage("Incorect Username/Password");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    // Show password when user press "Show Password"
    public void showPassword() {
        if (login_selectShowPassword.isSelected()) {
            login_showPassword.setText(login_password.getText());
            login_showPassword.setVisible(true);
            login_password.setVisible(false);
        } else {
            login_password.setText(login_showPassword.getText());
            login_showPassword.setVisible(false);
            login_password.setVisible(true);
        }
    }

    // Handle forgetPassword
    public void forgetPassword() {
        AlertMess alert = new AlertMess();
        if (forget_username.getText().isEmpty() || forget_Answer.getText().isEmpty()
                || forget_selectQuestion.getSelectionModel().getSelectedItem() == null) {
            alert.errorMessage("Error, All fields must be filled");
        } else {
            String dataForgot = "SELECT username, question, answer FROM user WHERE username = ? AND question = ? AND answer = ?";
            connect = connectDatabase();
            try {
                prep = connect.prepareStatement(dataForgot);
                prep.setString(1, forget_username.getText());
                prep.setString(2, (String) forget_selectQuestion.getSelectionModel().getSelectedItem());
                prep.setString(3, forget_Answer.getText());
                result = prep.executeQuery();
                if (result.next()) {
                    signup_form.setVisible(false);
                    login_form.setVisible(false);
                    forget_form.setVisible(false);
                    change_form.setVisible(true);
                } else {
                    alert.errorMessage("Incorrect answer/question");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Question forgetpassword
    public void listForgetQuestion() {
        List<String> questions = new ArrayList<String>();
        for (String question : questionList) {
            questions.add(question);
        }
        ObservableList listForgotQ = FXCollections.observableArrayList(questions);
        forget_selectQuestion.setItems(listForgotQ);
    }

    // Handle cases when fill fields of register window
    // Add to database library, table user.
    public void register() {
        AlertMess alert = new AlertMess();

        if (signup_username.getText().isEmpty() || signup_password.getText().isEmpty()
                || signup_confirmPassword.getText().isEmpty()
                || signup_email.getText().isEmpty() || signup_answer.getText().isEmpty()) {
            alert.errorMessage("Error, All fields must be filled");
        } else if (!signup_password.getText().equals(signup_confirmPassword.getText())) {
            alert.errorMessage("Error, Password does not match");
        } else if (signup_password.getText().length() < 6) {
            alert.errorMessage("Error, Password is not strong enough. Password must be at least 6 characters.");
        } else {
            String DataNewUser = "SELECT * FROM user WHERE username = '"
                    + signup_username.getText() + "'";
            connect = connectDatabase();
            try {
                statement = connect.createStatement();
                result = statement.executeQuery(DataNewUser);

                if (result.next()) {
                    alert.errorMessage("Error, Username already exists");
                } else {
                    String insertData = "INSERT INTO user " + "(email, username, password, question, answer, date)"
                            + "VALUES(?,?,?,?,?,?)";

                    prep = connect.prepareStatement(insertData);
                    prep.setString(1, signup_email.getText());
                    prep.setString(2, signup_username.getText());
                    prep.setString(3, signup_password.getText());
                    prep.setString(4, (String) signup_selectQuestion.getSelectionModel().getSelectedItem());
                    prep.setString(5, signup_answer.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prep.setString(6, String.valueOf(sqlDate));

                    prep.executeUpdate();

                    alert.successMessage("Registered successfully");

                    clearRegister();

                    signup_form.setVisible(false);
                    login_form.setVisible(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // To delete all fields of register windows when register successful
    public void clearRegister() {
        signup_username.clear();
        signup_password.clear();
        signup_confirmPassword.clear();
        signup_email.clear();
        signup_answer.clear();
        signup_selectQuestion.setValue(null);
    }

    // Handle cases when user press "Change Password"
    public void changePassword() {
        AlertMess alert = new AlertMess();
        if (change_password.getText().isEmpty() || change_comfirmPassword.getText().isEmpty()) {
            alert.errorMessage("Error, All fields must be filled");
        } else if (!change_password.getText().equals(change_comfirmPassword.getText())) {
            alert.errorMessage("Error, Password does not match");
        } else if (change_password.getText().length() < 6) {
            alert.errorMessage("Error, Password is not strong enough. Password must be at least 6 characters.");
        } else {
            String dataChangePass = "UPDATE user SET password = ?, update_date = ?" + "WHERE username = '"
                    + forget_username.getText() + "'";
            connect = connectDatabase();
            try {
                prep = connect.prepareStatement(dataChangePass);
                prep.setString(1, change_password.getText());
                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                prep.setString(2, String.valueOf(sqlDate));
                prep.executeUpdate();
                alert.successMessage("Password changed successfully");

                signup_form.setVisible(false);
                login_form.setVisible(true);
                forget_form.setVisible(false);
                change_form.setVisible(false);

                change_password.setText("");
                change_comfirmPassword.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Transform
    public void transform(ActionEvent event) {
        if (event.getSource() == signup_loginAccount
                || event.getSource() == forget_backBtn) {
            signup_form.setVisible(false);
            login_form.setVisible(true);
            forget_form.setVisible(false);
            change_form.setVisible(false);
        } else if (event.getSource() == login_createAccount) {
            signup_form.setVisible(true);
            login_form.setVisible(false);
            forget_form.setVisible(false);
            change_form.setVisible(false);
        } else if (event.getSource() == login_forgotPassword) {
            signup_form.setVisible(false);
            login_form.setVisible(false);
            forget_form.setVisible(true);
            change_form.setVisible(false);

            // Show question to combo box
            listForgetQuestion();
        } else if (event.getSource() == change_backBtn) {
            signup_form.setVisible(false);
            login_form.setVisible(true);
            forget_form.setVisible(false);
            change_form.setVisible(false);
        }
    }

    // Setting question.
    private String[] questionList = { "What is your grandmother's name?", "What is your favourite singer?",
            "550 / 2 = ?",
            "Glory Glory ...?", "Do you like J97 aka Meomeo?" };

    public void question() {
        List<String> list = new ArrayList<String>();
        for (String x : questionList) {
            list.add(x);
        }
        ObservableList listQ = FXCollections.observableArrayList(list);
        signup_selectQuestion.setItems(listQ);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        question();
        listForgetQuestion();
    }

}
