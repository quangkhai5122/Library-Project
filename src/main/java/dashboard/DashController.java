package dashboard;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import API.GoogleBooksAPI;
import Object.Book;
import Object.Member;
import Object.BorrowedBook;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class DashController implements Initializable {
    private ObservableList<Book> List0fBooks = FXCollections.observableArrayList();
    private ObservableList<Member> List0fMembers = FXCollections.observableArrayList();
    private final Map<String, Image> imageCache = new HashMap<>();

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
    private Button dashboard_btn;

    @FXML
    private Button edit_button;

    @FXML
    private Button in4book_btn;

    @FXML
    private Button in4mem_btn;

    @FXML
    private Button lendbook_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Button returnbook_btn;

    @FXML
    private Button semiIn4book_btn;

    @FXML
    private Button semiIn4mem_btn;

    @FXML
    private Button semiLogout_btn;

    @FXML
    private Button semidash_btn;

    @FXML
    private Circle semiCircle_img;

    @FXML
    private Circle circle_img;

    @FXML
    private Label nameadmin;

    @FXML
    private Label bookcnt;

    @FXML
    private Label memcnt;

    @FXML
    private Label authorcnt;

    @FXML
    private Label codeLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label yearLabel;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private AnchorPane nav_form;

    @FXML
    private AnchorPane CenterMain_form;

    @FXML
    private AnchorPane semiNav_form;

    @FXML
    private AnchorPane bookshow;

    @FXML
    private AnchorPane dashshow;

    @FXML
    private AnchorPane memshow;

    @FXML
    private AnchorPane addbookshow;

    @FXML
    private AnchorPane addmemshow;

    @FXML
    private AnchorPane memIn4;

    @FXML
    private AnchorPane memLoan;

    @FXML
    private ImageView bookCoverImageView;

    @FXML
    private ImageView loadingGif;

    @FXML
    private PieChart categoryPieChart;

    @FXML
    private TextField searchBookField;

    @FXML
    private TextField searchMemField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField yearField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField searchBorrow;

    @FXML
    private DatePicker dateField;

    @FXML
    private TableView<Book> bookTable;

    @FXML
    private TableView<Member> memTable;

    @FXML
    private TableView<BorrowedBook> borrowedBookTable;

    @FXML
    private TableColumn<Book, Integer> bookIdColumn;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, String> categoryColumn;

    @FXML
    private TableColumn<Book, Integer> publishedYearColumn;

    @FXML
    private TableColumn<Member, Integer> codeColumn;

    @FXML
    private TableColumn<Member, String> nameColumn;

    @FXML
    private TableColumn<Member, String> emailColumn;

    @FXML
    private TableColumn<Member, LocalDate> dateColumn;

    @FXML
    private TableColumn<BorrowedBook, Integer> borrowIdColumn;

    @FXML
    private TableColumn<BorrowedBook, Integer> memberIdColumn;

    @FXML
    private TableColumn<BorrowedBook, Integer> bookIdColumn2;

    @FXML
    private TableColumn<BorrowedBook, LocalDate> borrowDateColumn;

    @FXML
    private TableColumn<BorrowedBook, LocalDate> returnDateColumn;

    @FXML
    private double x;
    private double y;

    @FXML
    public void logout(ActionEvent event) {
        try {
            if (event.getSource() == logout_btn || event.getSource() == semiLogout_btn) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void exit() {

        System.exit(0);

    }

    public void minimize() {

        Stage stage = (Stage) mini_btn.getScene().getWindow();
        stage.setIconified(true);

    }


    @FXML
    //Ham tim sach.
    protected void searchBooks() {
        String keyword = searchBookField.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            bookTable.setItems(List0fBooks);
            return;
        }
        ObservableList<Book> filteredList = FXCollections.observableArrayList();
        for (Book book : List0fBooks) {
            if (book.getTitle().toLowerCase().contains(keyword)) {
                filteredList.add(book);
            } else if (book.getAuthor().toLowerCase().contains(keyword)) {
                filteredList.add(book);
            } else if (book.getCategory().toLowerCase().contains(keyword)) {
                filteredList.add(book);
            }
        }
        bookTable.setItems(filteredList);
    }

    @FXML
    //Ham tim hoi vien.
    protected void searchMembers() {
        String keyword = searchMemField.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            memTable.setItems(List0fMembers);
            return;
        }
        ObservableList<Member> filteredList = FXCollections.observableArrayList();
        for (Member mem : List0fMembers) {
            if (mem.getName().toLowerCase().contains(keyword)) {
                filteredList.add(mem);
            }
        }
        memTable.setItems(filteredList);
    }

    @FXML
    //Ham hien thi thong tin sach.
    protected void showBookInfo() {
        addmemshow.setVisible(false);
        dashshow.setVisible(false);
        addbookshow.setVisible(false);
        memshow.setVisible(false);
        bookshow.setVisible(true);
    }

    @FXML
    //Ham hien thi dashboard.
    protected void showDashboard() {
        addmemshow.setVisible(false);
        addbookshow.setVisible(false);
        bookshow.setVisible(false);
        memshow.setVisible(false);
        dashshow.setVisible(true);
        refreshDashBoard();
    }

    @FXML
    //Ham them sach.
    protected void showAddBook() {
        addmemshow.setVisible(false);
        dashshow.setVisible(false);
        bookshow.setVisible(false);
        memshow.setVisible(false);
        addbookshow.setVisible(true);
    }

    @FXML
    //Ham hien thi hoi vien.
    protected void showMember() {
        addmemshow.setVisible(false);
        dashshow.setVisible(false);
        addbookshow.setVisible(false);
        bookshow.setVisible(false);
        memLoan.setVisible(false);
        memIn4.setVisible(true);
        memshow.setVisible(true);
    }

    @FXML
    //Ham hien thi cua so them hoi vien.
    protected void showAddMem() {
        dashshow.setVisible(false);
        bookshow.setVisible(false);
        memshow.setVisible(false);
        addbookshow.setVisible(false);
        addmemshow.setVisible(true);
    }

    @FXML
    //Ham hien thi sach dang duoc muon.
    protected void showBorrowedBook() {
        dashshow.setVisible(false);
        bookshow.setVisible(false);
        addbookshow.setVisible(false);
        addmemshow.setVisible(false);
        memIn4.setVisible(false);
        memshow.setVisible(true);
        memLoan.setVisible(true);
        showBorrowedBooksForMember();
    }

    @FXML
    //Ham tai du lieu sach.
    protected void loadBookData() {
        List0fBooks.clear();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryDB", "root", "huy2206");
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM Book");

            while (rs.next()) {
                List0fBooks.add(new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getInt("published_year")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        bookTable.setItems(List0fBooks);
    }

    @FXML
    //Ham tai du lieu hoi vien.
    protected void loadMemberData() {
        List0fMembers.clear();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryDB", "root", "huy2206");
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM member");

            while (rs.next()) {
                List0fMembers.add(new Member(
                        rs.getInt("member_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getDate("registration_date").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        memTable.setItems(List0fMembers);
    }

    @FXML
    //Ham tai du lieu bieu do tron.
    protected void loadPieChartData() {
        categoryPieChart.getData().clear();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryDB", "root", "huy2206")) {
            String sql = "SELECT category, COUNT(*) AS categoryCount FROM Book GROUP BY category ORDER BY categoryCount DESC";
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {


                List<PieChart.Data> tempData = new ArrayList<>();
                int totalBooks = 0;

                while (rs.next()) {
                    String category = rs.getString("category");
                    int count = rs.getInt("categoryCount");
                    tempData.add(new PieChart.Data(category, count));
                    totalBooks += count;
                }

                if (tempData.size() > 2) {
                    pieChartData.add(tempData.get(0));
                    pieChartData.add(tempData.get(1));


                    int otherBooks = tempData.stream()
                            .skip(2)
                            .mapToInt(data -> (int) data.getPieValue())
                            .sum();


                    pieChartData.add(new PieChart.Data("Other", otherBooks));
                } else {
                    pieChartData.addAll(tempData);
                }

                categoryPieChart.setData(pieChartData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    //Ham them sach.
    protected void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String category = categoryField.getText();
        int year = 0;
        if (!yearField.getText().isEmpty()) {
            year = Integer.parseInt(yearField.getText());
        }

        if (author.isEmpty() || category.isEmpty() || year == 0) {
            try {

                String jsonResponse = GoogleBooksAPI.searchBooks(title);
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
                JsonArray items = jsonObject.getAsJsonArray("items");

                if (items != null && items.size() > 0) {
                    JsonObject book = items.get(0).getAsJsonObject();
                    JsonObject volumeInfo = book.getAsJsonObject("volumeInfo");


                    title = volumeInfo.has("title") ? volumeInfo.get("title").getAsString() : title;
                    author = volumeInfo.has("authors") ? volumeInfo.getAsJsonArray("authors").toString() : "Unknown";
                    category = volumeInfo.has("categories") ? volumeInfo.getAsJsonArray("categories").get(0).getAsString() : "Unknown";
                    year = volumeInfo.has("publishedDate") ? Integer.parseInt(volumeInfo.get("publishedDate").getAsString().substring(0, 4)) : year;
                } else {
                    showAlert("No book found in Google Books API!", Alert.AlertType.ERROR);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error while fetching data from API.", Alert.AlertType.ERROR);
                return;
            }
        }


        String sql = "INSERT INTO Book (title, author, category, published_year) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryDB", "root", "huy2206");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, category);
            pstmt.setInt(4, year);

            pstmt.executeUpdate();
            loadPieChartData();

            showAlert("Add book successfully!", Alert.AlertType.INFORMATION);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadBookData();
    }

    @FXML
    //Ham them thanh vien.
    protected void AddMember() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        LocalDate registDate = dateField.getValue();
        String date = registDate.toString();

        if (!name.matches("[a-zA-Z\\s]+")) {
            showAlert("Invalid name! Name should only contain letters.", Alert.AlertType.ERROR);
            return;
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
            showAlert("Invalid email! Email should be in the format: example@gmail.com.", Alert.AlertType.ERROR);
            return;
        }

        if (!phone.matches("\\d{10}")) {
            showAlert("Invalid phone number! Phone number must be exactly 10 digits.", Alert.AlertType.ERROR);
            return;
        }

        if (registDate == null) {
            showAlert("Please select a registration date.", Alert.AlertType.ERROR);
            return;
        }

        String sql = "INSERT INTO member (name, email, registration_date, phone) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryDB", "root", "huy2206");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, date);
            pstmt.setString(4, phone);

            pstmt.executeUpdate();

            showAlert("Add member successfully!", Alert.AlertType.INFORMATION);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadMemberData();
    }

    @FXML
    //Ham tai du lieu dashboard.
    private void loadDashboardData() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryDB", "root", "huy2206");
             Statement stmt = conn.createStatement()) {

            ResultSet rsBooks = stmt.executeQuery("SELECT COUNT(*) AS book_count FROM Book");
            if (rsBooks.next()) {
                bookcnt.setText(String.valueOf(rsBooks.getInt("book_count")));
            }

            ResultSet rsMembers = stmt.executeQuery("SELECT COUNT(*) AS member_count FROM Member");
            if (rsMembers.next()) {
                memcnt.setText(String.valueOf(rsMembers.getInt("member_count")));
            }


            ResultSet rsAuthors = stmt.executeQuery("SELECT COUNT(DISTINCT author) AS author_count FROM Book");
            if (rsAuthors.next()) {
                authorcnt.setText(String.valueOf(rsAuthors.getInt("author_count")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    //Ham lam moi dashboard.
    protected void refreshDashBoard() {
        bookcnt.setText("");
        memcnt.setText("");
        authorcnt.setText("");
        loadDashboardData();
    }
    @FXML
    //Ham hien thi sdt hoi vien.
    protected void showMemberPhoneNumber(int memberCode) {
        String phoneNumber = "";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryDB", "root", "huy2206");
             PreparedStatement stmt = conn.prepareStatement("SELECT phone FROM Member WHERE member_id = ?")) {

            stmt.setInt(1, memberCode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                phoneNumber = rs.getString("phone");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        phoneLabel.setText(phoneNumber.isEmpty() ? "No phone number found" : phoneNumber);
    }

    @FXML
    //Ham hien thi thong tin hoi vien chi tiet.
    protected void showMemberDetailIn4(Member member) {
        codeLabel.setText(Integer.toString(member.getMemberCode()));
        emailLabel.setText(member.getEmail());
        showMemberPhoneNumber(member.getMemberCode());
    }

    @FXML
    //Ham hien thi thong tin sach chi tiet.
    protected void showBookDetailIn4(Book book) {
        titleLabel.setText(book.getTitle());
        authorLabel.setText(book.getAuthor());
        genreLabel.setText(book.getCategory());
        yearLabel.setText(Integer.toString(book.getPublishedYear()));

        String title = book.getTitle();
        if (imageCache.containsKey(title)) {
            bookCoverImageView.setImage(imageCache.get(title));
            return;
        }


        loadingGif.setVisible(true);
        bookCoverImageView.setImage(null);
        new Thread(() -> {
            try {
                String coverUrl = GoogleBooksAPI.getBookCover(title);
                if (coverUrl != null) {
                    Image bookCover = new Image(coverUrl, true);
                    Platform.runLater(() -> {
                        loadingGif.setVisible(false);
                        bookCoverImageView.setImage(bookCover);
                        imageCache.put(title, bookCover);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    //Ham lay Id hoi vien.
    protected int getMemberId() {
        Member selectedMem = memTable.getSelectionModel().getSelectedItem();
        if (selectedMem != null) {
            return selectedMem.getMemberCode();
        }
        return 0;
    }

    @FXML
    //Ham lay Id sach.
    protected int getBookId() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            return selectedBook.getBookId();
        }
        return 0;
    }

    @FXML
    //Ham cho muon sach.
    protected void lendBook() throws SQLException {
        int memId = getMemberId();
        int bookId = getBookId();
        LocalDate borrowDate = LocalDate.now();
        LocalDate returnDate = borrowDate.plusWeeks(3);

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryDB", "root", "huy2206")) {
            String sql = "INSERT INTO borrowing (member_id, book_id, borrow_date, return_date) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, memId);
                pstmt.setInt(2, bookId);
                pstmt.setString(3, borrowDate.toString());
                pstmt.setString(4, returnDate.toString());

                pstmt.executeUpdate();

                showAlert("Lend book successfully!", Alert.AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    //Ham tai du lieu sach dang duoc muon.
    protected ObservableList<BorrowedBook> loadBorrowedBook(int memberId) {
        ObservableList<BorrowedBook> borrowedBooks = FXCollections.observableArrayList();
        String query = "SELECT borrowing_id, member_id, book_id, borrow_date, return_date " +
                "FROM borrowing " +
                "WHERE member_id = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryDB", "root", "huy2206");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                borrowedBooks.add(new BorrowedBook(
                        rs.getInt("book_id"),
                        rs.getInt("member_id"),
                        rs.getInt("borrowing_id"),
                        rs.getDate("borrow_date").toLocalDate(),
                        rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return borrowedBooks;
    }

    @FXML
    //Ham hien thi sach dang muon cua hoi vien.
    protected void showBorrowedBooksForMember() {
        int memberId = getMemberId();
        borrowedBookTable.setItems(loadBorrowedBook(memberId));
    }

    @FXML
    //Ham tra sach.
    protected void returnBook() throws SQLException {
        BorrowedBook selectedBook = borrowedBookTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            int borrowingId = selectedBook.getBorrowingId();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryDB", "root", "huy2206")) {
                String query = "DELETE FROM borrowing WHERE borrowing_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setInt(1, borrowingId);
                    pstmt.executeUpdate();
                    borrowedBookTable.getItems().remove(selectedBook);

                    showAlert("Return book successfully!", Alert.AlertType.INFORMATION);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please select a book first!");
        }
    }

    @FXML
    //Ham thong bao loi.
    protected void showAlert(String mess, Alert.AlertType type) {
        Alert pop = new Alert(type);
        pop.setTitle("Admin message");
        pop.setHeaderText(null);
        pop.setContentText(mess);
        pop.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadDashboardData();
        loadPieChartData();

        loadBookData();
        bookIdColumn.setCellValueFactory(cellData -> cellData.getValue().bookIdProperty().asObject());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        publishedYearColumn.setCellValueFactory(cellData -> cellData.getValue().publishedYearProperty().asObject());

        loadMemberData();
        codeColumn.setCellValueFactory(cellData -> cellData.getValue().memberCodeProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateOfRegistProperty());

        borrowIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBorrowingId()));
        memberIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMemberId()));
        bookIdColumn2.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBookId()));
        borrowDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBorrowDate()));
        returnDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getReturnDate()));

        memTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showMemberDetailIn4(newValue);
            }
        });

        bookTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showBookDetailIn4(newValue);
            }
        });
    }

}
