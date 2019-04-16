package dbconnection;

import entities.Author;
import entities.Book;
import entities.Borrow;
import entities.Reader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Select {
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
    private EntityManager em = factory.createEntityManager();

    public List<Reader> selectReaders(){
        String hql = "FROM Reader r";
        Query query = em.createQuery(hql);
        List<Reader> results =query.getResultList();
        return results;
    }

    public List<Author> selectAuthors(){
        String hql = "FROM Author a";
        Query query = em.createQuery(hql);
        List<Author> results =query.getResultList();
        return results;
    }

    public Author selectAuthorById(int id){
        String hql = "FROM Author a WHERE a.author_id="+id;
        Query query = em.createQuery(hql);
        List<Author> results =query.getResultList();
        return results.get(0);
    }

    public Book selectBookById(Book book){
        ArrayList<Book> results=new ArrayList<Book>();
        try {
            em.getTransaction().begin();
            String hql="SELECT b FROM Book b WHERE b.id="+book.getBook_id();
            Query query = em.createQuery(hql);
            results = (ArrayList<Book>) query.getResultList();
            em.getTransaction().commit();
            return results.get(0);
        }
        catch(Exception e) {
            System.err.println("Blad przy pobieraniu rekordu: " + e);
        }
        return null;
    }

    public Reader selectById(int id){
        String hql = "FROM Reader r WHERE r.reader_id="+id;
        Query query = em.createQuery(hql);
        List<Reader> results =query.getResultList();
        return results.get(0);
    }

    public List<Book> selectBooks(){
        String hql = "FROM Book b";
        Query query = em.createQuery(hql);
        ArrayList<Book> results =(ArrayList<Book>) query.getResultList();
        ArrayList<Book> new_result=new ArrayList<Book>();
        for(Book b: results){
            if(!new_result.contains(b)){
                new_result.add(b);
            }
        }

        return new_result;
    }

    public List selectUserBooks(Reader reader){
        List<Book> results=new ArrayList<Book>();
        try {
            String hql = "SELECT b FROM Book b JOIN b.borrow br WHERE br.reader=" + reader.getReader_id();
            Query query = em.createQuery(hql);
            results = query.getResultList();
            return results;
        }catch (Exception e){
            System.out.println("Nie udało się odczytać "+e);
        }
        return results;
    }


}

