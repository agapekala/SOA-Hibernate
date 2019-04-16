package helpers;

import dbconnection.Select;
import entities.Author;
import entities.Book;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class Helper {
    private Select select=new Select();
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
    private EntityManager em = factory.createEntityManager();

    public boolean checkIfBookAvailable(Book book) throws ValidatorException{
       Book b=select.selectBookById(book);
       if(b.getBorrow()!=null && b.getBorrow().getBorrow_id()!=0){
           FacesContext facesContext = FacesContext.getCurrentInstance();
           FacesMessage facesMessage = new FacesMessage("Książka \""+b.getTitle()+"\" jest wypożyczona!");
           facesContext.addMessage(null, facesMessage);
           return false;
       }
       return true;
    }

    public Author checkIfAuthorExist(Author a){
        String hql = "FROM Author a WHERE a.last_name like '"+a.getLast_name()+"'";
        Query query = em.createQuery(hql);
        List<Author> results =query.getResultList();
        if(results.isEmpty()){
            return a;
        }
        return results.get(0);
    }
}
