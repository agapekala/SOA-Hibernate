package entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Borrow {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    private int borrow_id;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @OneToOne
    @JoinColumn(name="book_id")
    private Book book;

    @Temporal(TemporalType.DATE)
    private Date borrow_date;

    @Temporal(TemporalType.DATE)
    private Date return_date;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getBorrow_date() {
        return borrow_date;
    }

    public void setBorrow_date(Date borrow_date) {
        this.borrow_date = borrow_date;
    }

    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }

    public int getBorrow_id() {
        return borrow_id;
    }

    public void setBorrow_id(int borrow_id) {
        this.borrow_id = borrow_id;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}
