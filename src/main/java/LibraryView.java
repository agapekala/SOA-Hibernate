import dbconnection.Update;
import dbconnection.Filter;
import dbconnection.Select;
import entities.Author;
import entities.Book;
import entities.Reader;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@ManagedBean
@ViewScoped
public class LibraryView {
    private Update update =new Update();
    private Select select=new Select();


    private String name;
    private String last_name;
    private Reader reader=new Reader();
    private ArrayList<Reader> all_readers=new ArrayList<Reader>();
    private ArrayList<Book> all_books=new ArrayList<Book>();
    private ArrayList<Book> user_books=new ArrayList<Book>();
    private ArrayList<Author> all_authors=new ArrayList<Author>();
    private int id;
    private String title;
    private String author_name;
    private String author_last_name;

    //filtry



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        reader=select.selectById(id);

    }

    public void addReader(){
        update.addReader(name,last_name);
    }

    public ArrayList<Book> getAll_books() {
        all_books=(ArrayList<Book>) select.selectBooks();
        return all_books;
    }

    public void setAll_books(ArrayList<Book> all_books) {
        this.all_books = all_books;
    }

    public ArrayList<Author> getAll_authors() {
        all_authors=(ArrayList<Author>) select.selectAuthors();
        return all_authors;
    }

    public void setAll_authors(ArrayList<Author> all_authors) {
        this.all_authors = all_authors;
    }

    public ArrayList<Reader> getAll_readers() {
        all_readers = (ArrayList<Reader>) select.selectReaders();
        return all_readers;
    }

    public void setAll_readers(ArrayList<Reader> all_readers) {
        this.all_readers = (ArrayList<Reader>) select.selectReaders();
    }

    public void addBook(){
        Author a=new Author();
        a.setLast_name(author_last_name);
        a.setName(author_name);
        update.addBook(title, a);
    }


    public void borrowBook(){
        ArrayList<Book> to_borrow=new ArrayList<Book>();
        for(Book b: all_books){
            if(b.isChecked()){
                to_borrow.add(b);
            }
        }
        update.addBorrow(reader,to_borrow);
    }

    public ArrayList<Book> getUser_books() {
        user_books=(ArrayList<Book>)select.selectUserBooks(reader);
        return user_books;
    }

    public void setUser_books(ArrayList<Book> user_books) {
        this.user_books = user_books;
    }

    public void returnBook(){
        ArrayList<Book> to_return=new ArrayList<Book>();
        for (Book b:user_books){
            if(b.isChecked()){
                to_return.add(b);
            }
        }
        update.returnBorrowedBooks(to_return);
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

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_last_name() {
        return author_last_name;
    }

    public void setAuthor_last_name(String author_last_name) {
        this.author_last_name = author_last_name;
    }


}
