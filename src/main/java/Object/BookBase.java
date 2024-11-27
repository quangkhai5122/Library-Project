package Object;

import javafx.beans.property.*;
import java.time.LocalDate;

public abstract class BookBase {
    private final IntegerProperty bookId;
    private final StringProperty title;
    private final StringProperty author;
    private final StringProperty category;
    private final IntegerProperty publishedYear;

    public BookBase(int bookId, String title, String author, String category, int publishedYear) {
        this.bookId = new SimpleIntegerProperty(bookId);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.category = new SimpleStringProperty(category);
        this.publishedYear = new SimpleIntegerProperty(publishedYear);
    }

    public int getBookId() {
        return bookId.get();
    }

    public IntegerProperty bookIdProperty() {
        return bookId;
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getAuthor() {
        return author.get();
    }

    public StringProperty authorProperty() {
        return author;
    }

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public int getPublishedYear() {
        return publishedYear.get();
    }

    public IntegerProperty publishedYearProperty() {
        return publishedYear;
    }
}
