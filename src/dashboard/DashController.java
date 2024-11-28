package dashboard;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import gametetris.Tetris;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class DashController implements Initializable {

    @FXML
    private Button addbook_btn;

    @FXML
    private Button bars_btn;

    @FXML
    private Button chevron_btn;

    @FXML
    private Button close_btn;

    @FXML
    private Button mini_btn;

    @FXML
    private Button addmem_btn;

    @FXML
    private Circle circle_img;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button game_btn;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Button edit_button;

    @FXML
    private FontAwesomeIcon edit_icon;

    @FXML
    private Button in4book_btn;

    @FXML
    private Button in4mem_btn;

    @FXML
    private Button lendbook_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Label nameadmin;

    @FXML
    private Button returnbook_btn;

    @FXML
    private AnchorPane nav_form;

    @FXML
    private AnchorPane CenterMain_form;

    @FXML
    private Circle semiCircle_img;

    @FXML
    private Button semiGame_btn;

    @FXML
    private Button semiIn4book_btn;

    @FXML
    private Button semiIn4mem_btn;

    @FXML
    private Button semiLogout_btn;

    @FXML
    private Button semidash_btn;

    @FXML
    private AnchorPane semiNav_form;

    private double x;
    private double y;

    private Image image;
    private Stage gameStage; // Lưu trữ cửa sổ game để quản lý trạng thái

    @FXML
    public void logout(ActionEvent event) {
        try {
            if (event.getSource() == logout_btn || event.getSource() == semiLogout_btn) {
                // Transform DASHBOARD to LOGIN
                Parent root = FXMLLoader.load(getClass().getResource("/login/LoginManager.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent e) -> {
                    x = e.getSceneX();
                    y = e.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent e) -> {

                    stage.setX(e.getScreenX() - x);
                    stage.setY(e.getScreenY() - y);

                });

                stage.initStyle(StageStyle.DECORATED);

                stage.setScene(scene);
                stage.show();

                logout_btn.getScene().getWindow().hide();

            }
        } catch (Exception e) {e.printStackTrace();}
    }

    public void sliderArrow() {

        TranslateTransition slide = new TranslateTransition();

        slide.setDuration(Duration.seconds(.5));
        slide.setNode(nav_form);
        slide.setToX(-208);

        TranslateTransition slide1 = new TranslateTransition();

        slide1.setDuration(Duration.seconds(.5));
        slide1.setNode(CenterMain_form);
        slide1.setToX(-208 + 90);

        TranslateTransition slide2 = new TranslateTransition();
        slide2.setDuration(Duration.seconds(.5));
        slide2.setNode(semiNav_form);
        slide2.setToX(0);

        slide.setOnFinished((ActionEvent event) -> {

            chevron_btn.setVisible(false);
            bars_btn.setVisible(true);

        });

        slide2.play();
        slide1.play();
        slide.play();

    }

    public void setAvatar() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Avatar");
        chooser.getExtensionFilters().add(new ExtensionFilter("Image file", "*png", "*jpg"));
        Stage stage = (Stage)nav_form.getScene().getWindow();
        File file = chooser.showOpenDialog(stage);

        if(file != null) {
            image = new Image(file.toURI().toString(), 100, 96, false, true);
            circle_img.setFill(new ImagePattern(image));
            semiCircle_img.setFill(new ImagePattern(image));

            // getData.path = file.getAbsolutePath();
        }
    }

    public void designSetAvatar() {

        edit_button.setVisible(false);

        circle_img.setOnMouseEntered((MouseEvent event) -> {
            edit_button.setVisible(true);
        });

        circle_img.setOnMouseExited((MouseEvent event) -> {
            edit_button.setVisible(false);
        });

        edit_button.setOnMouseEntered((MouseEvent event) -> {
            edit_button.setVisible(true);
            edit_icon.setFill(Color.valueOf("ffff"));
        });

        edit_button.setOnMousePressed((MouseEvent event) -> {
            edit_button.setVisible(true);
            edit_icon.setFill(Color.RED);
        });

        edit_button.setOnMouseExited((MouseEvent event) -> {
            edit_button.setVisible(false);
        });
    }

    public void sliderBars() {

        TranslateTransition slide = new TranslateTransition();

        slide.setDuration(Duration.seconds(.5));
        slide.setNode(nav_form);
        slide.setToX(0);

        TranslateTransition slide1 = new TranslateTransition();

        slide1.setDuration(Duration.seconds(.5));
        slide1.setNode(CenterMain_form);
        slide1.setToX(0);

        TranslateTransition slide2 = new TranslateTransition();
        slide2.setDuration(Duration.seconds(.5));
        slide2.setNode(semiNav_form);
        slide2.setToX(-78);

        slide.setOnFinished((ActionEvent event) -> {

            chevron_btn.setVisible(true);
            bars_btn.setVisible(false);

        });

        slide2.play();
        slide1.play();
        slide.play();
    }

    private void openTetrisGame() {
        // Đóng cửa sổ game cũ (nếu tồn tại)
        if (gameStage != null) {
            gameStage.close();
        }
        // Khởi chạy game Tetris
        Tetris tetris = new Tetris();
        gameStage = new Stage();
        try {
            tetris.start(gameStage); // Gọi start() của TetrisGame
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exit() {

        System.exit(0);

    }

    public void minimize() {

        Stage stage = (Stage)mini_btn.getScene().getWindow();
        stage.setIconified(true);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        designSetAvatar();
        // Gắn sự kiện cho nút game_btn
        game_btn.setOnAction(event -> {
            openTetrisGame(); // Mở game Tetris
        });

        semiGame_btn.setOnAction(event -> {
            openTetrisGame(); // Mở game Tetris
        });
    }
}
