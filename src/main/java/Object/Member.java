package Object;


import javafx.beans.property.*;

import java.time.LocalDate;

public class Member {
    private final IntegerProperty memberCode;
    private final StringProperty name;
    private final StringProperty email;
    private final ObjectProperty<LocalDate> dateOfRegist;

    public Member(int memberCode, String name, String email, LocalDate dateOfRegist) {
        this.memberCode = new SimpleIntegerProperty(memberCode);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.dateOfRegist = new SimpleObjectProperty<>(dateOfRegist);
    }

    public LocalDate getDateOfRegist() {
        return dateOfRegist.get();
    }

    public ObjectProperty<LocalDate> dateOfRegistProperty() {
        return dateOfRegist;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public int getMemberCode() {
        return memberCode.get();
    }

    public IntegerProperty memberCodeProperty() {
        return memberCode;
    }
}
