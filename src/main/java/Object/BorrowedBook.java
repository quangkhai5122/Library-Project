package Object;

import java.time.LocalDate;

public class BorrowedBook extends BookBase {
    private int memberId;
    private int borrowingId;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    public BorrowedBook(int bookId, int memberId, int borrowingId, LocalDate borrowDate, LocalDate returnDate) {
        super(bookId, "Unknown Title", "Unknown Author", "Unknown Category", 0);
        this.memberId = memberId;
        this.borrowingId = borrowingId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }


    public int getBorrowingId() {
        return borrowingId;
    }

    public int getMemberId() {
        return memberId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    // Có thể thêm các phương thức đặc thù cho BorrowedBook
}
