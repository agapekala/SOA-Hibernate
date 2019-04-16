package entities;


import javax.persistence.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    private int book_id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToOne
    @JoinColumn(name="borrow_id")
    private Borrow borrow;

    @Transient
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Borrow getBorrow() {
        return borrow;
    }

    public void setBorrow(Borrow borrow) {
        this.borrow = borrow;
    }


}
