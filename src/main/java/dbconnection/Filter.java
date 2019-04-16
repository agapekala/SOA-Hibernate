package dbconnection;

import com.sun.org.apache.regexp.internal.RE;
import entities.Author;
import entities.Book;
import entities.Borrow;
import entities.Reader;
import jdk.nashorn.internal.scripts.JO;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Filter {
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
    private EntityManager em = factory.createEntityManager();

    public ArrayList<Reader> filterReaderByBook(int id, Date from, Date to, boolean read){
        String end="return_date";
        if(!read){
            end="borrow_date";
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Reader> query=cb.createQuery(Reader.class);
        Root<Reader> r = query.from(Reader.class);
        Join<Reader, Borrow> borrowJoin= r.join("borrows", JoinType.INNER);
        query.select(r).where(cb.equal(borrowJoin.get("book"),id),cb.greaterThanOrEqualTo(borrowJoin.get("borrow_date")
        .as(java.sql.Date.class),from),cb.lessThanOrEqualTo(borrowJoin.get(end)
                .as(java.sql.Date.class),to));

        TypedQuery<Reader> tq = em.createQuery(query);
        return (ArrayList<Reader>) tq.getResultList();
    }

    public ArrayList<Reader> filterReaderByAuthor(int id, Date from, Date to, boolean read){
        String end="return_date";
        if(!read){
            end="borrow_date";
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Reader> query=cb.createQuery(Reader.class);
        Root<Reader> r = query.from(Reader.class);
        Join<Reader, Borrow> borrowJoin= r.join("borrows", JoinType.INNER);
        Join<Borrow, Book> bookJoin =borrowJoin.join("book", JoinType.INNER);
        Join<Book, Author> authorJoin=bookJoin.join("author",JoinType.INNER);
        query.select(r).where(cb.equal(authorJoin.get("author_id"),id),cb.greaterThanOrEqualTo(borrowJoin.get("borrow_date")
                .as(java.sql.Date.class),from),cb.lessThanOrEqualTo(borrowJoin.get(end)
                .as(java.sql.Date.class),to));

        TypedQuery<Reader> tq = em.createQuery(query);
        return (ArrayList<Reader>) tq.getResultList();
    }

    public ArrayList<Author> filterAuthorByReader(int id, Date from, Date to, boolean read){
        String hql;
        if(!read){
            hql = "SELECT a FROM Borrow b inner join" +
                    " b.book bb inner join bb.author a where b.reader="+id + " AND " +
                    "b.borrow_date >= :from AND b.borrow_date <= :to";
        }else{
            hql = "SELECT a FROM Borrow b inner join" +
                    " b.book bb inner join bb.author a where b.reader="+id + " AND " +
                    "b.borrow_date >= :from AND b.return_date <= :to";
        }

        Query query = em.createQuery(hql);
        query.setParameter("from",from);
        query.setParameter("to",to);
        List<Author> results =query.getResultList();

        return (ArrayList<Author>) results;
    }

    public ArrayList<Book> filterBookByAuthor(int id){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> query=cb.createQuery(Book.class);
        Root<Book> r = query.from(Book.class);
        Join<Book, Author> authorJoin= r.join("author", JoinType.INNER);

        query.select(r).where(cb.equal(authorJoin.get("author_id"),id));

        TypedQuery<Book> tq = em.createQuery(query);
        return (ArrayList<Book>) tq.getResultList();

    }

    public ArrayList<Book> filterBookByPopularity(int id){
        String hql;
        if(id!=0) {
            hql = "select book from Borrow b inner join b.book book inner join book.author a " +
                    "WHERE a.id="+id+" group by book.id order by count(book) desc ";
        }else{
            hql = "select book from Borrow b inner join b.book book " +
                    "group by book.id order by count(book) desc ";
        }
        Query query = em.createQuery(hql);
        query.setMaxResults(1);
        List<Book> results =query.getResultList();
        return (ArrayList<Book>)results;

    }
}
