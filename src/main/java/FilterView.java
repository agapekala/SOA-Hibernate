import dbconnection.Filter;
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
public class FilterView {
    private Filter filter=new Filter();

    private String from_date;
    private String to_date;
    private boolean book;
    private boolean read;
    private boolean read2;
    private int selected_book;
    private int selected_author;
    private int selected_reader;
    private ArrayList<Reader> filter_readers;
    private ArrayList<Author> filter_authors;
    private ArrayList<Book> filter_books;
    private boolean check_popularity;

    public void filterReaders()throws Exception{
        Date date1=new SimpleDateFormat("dd.MM.yyyy").parse(from_date);
        Date date2=new SimpleDateFormat("dd.MM.yyyy").parse(to_date);
        if(book){
            filter_readers=filter.filterReaderByBook(selected_book,date1, date2, read);
        }else{
            filter_readers=filter.filterReaderByAuthor(selected_author, date1, date2, read);
        }
    }

    public void filterAuthors() throws Exception{
        Date date1=new SimpleDateFormat("dd.MM.yyyy").parse(from_date);
        Date date2=new SimpleDateFormat("dd.MM.yyyy").parse(to_date);

        filter_authors=filter.filterAuthorByReader(selected_reader, date1, date2,read2);

    }

    public void filterBooks() {
        if(check_popularity){
            filter_books = filter.filterBookByPopularity(selected_author);
        }else{
            filter_books=filter.filterBookByAuthor(selected_author);
        }

    }

    public ArrayList<Reader> getFilter_readers() {
        return filter_readers;
    }

    public void setFilter_readers(ArrayList<Reader> filter_readers) {
        this.filter_readers = filter_readers;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public boolean isBook() {
        return book;
    }

    public void setBook(boolean book) {
        this.book = book;
    }

    public int getSelected_book() {
        return selected_book;
    }

    public void setSelected_book(int selected_book) {
        this.selected_book = selected_book;
    }

    public int getSelected_author() {
        return selected_author;
    }

    public void setSelected_author(int selected_author) {
        this.selected_author = selected_author;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public ArrayList<Author> getFilter_authors() {
        return filter_authors;
    }

    public void setFilter_authors(ArrayList<Author> filter_authors) {
        this.filter_authors = filter_authors;
    }

    public int getSelected_reader() {
        return selected_reader;
    }

    public void setSelected_reader(int selected_reader) {
        this.selected_reader = selected_reader;
    }

    public ArrayList<Book> getFilter_books() {
        return filter_books;
    }

    public void setFilter_books(ArrayList<Book> filter_books) {
        this.filter_books = filter_books;
    }

    public boolean isRead2() {
        return read2;
    }

    public void setRead2(boolean read2) {
        this.read2 = read2;
    }

    public boolean isCheck_popularity() {
        return check_popularity;
    }

    public void setCheck_popularity(boolean check_popularity) {
        this.check_popularity = check_popularity;
    }
}
