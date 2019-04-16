package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Reader {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    private int reader_id;

    private String name;
    private String last_name;


    @OneToMany(mappedBy = "reader")
    private Set<Borrow> borrows;

    public int getReader_id() {
        return reader_id;
    }

    public Set<Borrow> getBorrows() {
        return borrows;
    }

    public void setBorrows(Set<Borrow> borrows) {
        this.borrows = borrows;
    }

    public void setReader_id(int reader_id) {
        this.reader_id = reader_id;
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
}
