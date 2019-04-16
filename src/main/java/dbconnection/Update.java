package dbconnection;

import entities.Author;
import entities.Book;
import entities.Borrow;
import entities.Reader;
import helpers.Helper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.*;

public class Update {
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
    private EntityManager em = factory.createEntityManager();
    private Helper helper=new Helper();

    public void addReader(String name, String last_name){
        try {
            Reader reader=new Reader();
            reader.setName(name);
            reader.setLast_name(last_name);
            em.getTransaction().begin();
            em.persist(reader);

            em.getTransaction().commit();
        }
        catch(Exception e) {
            System.err.println("Blad przy dodawaniu rekordu: " + e);
        }
    }

    public void addAuthor(String name, String last_name){
        try{
            Author a=new Author();
            a.setName(name);
            a.setLast_name(last_name);
            em.getTransaction().begin();
            em.persist(a);
            em.getTransaction().commit();
        }catch(Exception e){
            System.err.println("Blad przy dodawaniu rekordu: " + e);
        }
    }

    public void addAuthor(Author author){
        try{
            em.getTransaction().begin();
            em.persist(author);
            em.getTransaction().commit();
        }catch(Exception e){
            System.err.println("Blad przy dodawaniu rekordu: " + e);
        }

    }
    public void addBook(String title, Author author){
        try{
            Author a=helper.checkIfAuthorExist(author);
            if(a.getAuthor_id()==0){
                addAuthor(a);
            }
            Book book=new Book();
            book.setAuthor(a);
            book.setTitle(title);
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();
        }catch(Exception e){
            System.err.println("Blad przy dodawaniu rekordu: " + e);
        }
    }

    public void addBorrow(Reader reader, ArrayList<Book> to_borrow){
        try {
            for (Book b : to_borrow) {
                boolean check=helper.checkIfBookAvailable(b);
                if(!check){return;}
            }
            em.getTransaction().begin();
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            Calendar month = Calendar.getInstance();
            month.add(Calendar.MONTH, 1);
            for (Book b : to_borrow) {
                Borrow borrow = new Borrow();
                borrow.setReader(reader);
                borrow.setBorrow_date(today.getTime());
                borrow.setBook(b);
                borrow.setReturn_date(month.getTime());
                em.persist(borrow);

                em.flush();
                Query query = em.createQuery("UPDATE Book b SET b.borrow=" + borrow.getBorrow_id() +
                        " WHERE b.book_id=" + b.getBook_id());
                query.executeUpdate();
            }
            em.getTransaction().commit();
        }catch(Exception e){
            System.err.println("Blad przy dodawaniu rekordu: " + e);
        }
    }

    public void returnBorrowedBooks(ArrayList<Book> to_return){
        try{
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);

            ArrayList<Borrow> borrows=new ArrayList<Borrow>();
            for(Book b: to_return) {
                em.getTransaction().begin();
                borrows.add(b.getBorrow());
                String hql = "UPDATE Book b SET b.borrow=null WHERE b.id="+b.getBook_id();
                Query query = em.createQuery(hql);
                query.executeUpdate();
                em.getTransaction().commit();
            }

            for(Borrow b:borrows){
                em.getTransaction().begin();
                em.find(Borrow.class, b.getBorrow_id());
                b.setReturn_date(today.getTime());
                System.out.println(b.getReturn_date());
                em.merge(b);
                em.getTransaction().commit();
            }
        }catch(Exception e){
            System.err.println("Blad przy aktualizowaniu rekordu: " + e);
        }
    }
}
