package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    private int author_id;

    private String name;
    private String last_name;

    @OneToMany(mappedBy = "author")
    private Set<Book> books;

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
