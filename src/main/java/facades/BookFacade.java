package facades;

import dtos.BookDTO;
import entities.Book;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class BookFacade {

    private static BookFacade instance;
    private static EntityManagerFactory emf;

    public static BookFacade getBookFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BookFacade();
        }
        return instance;
    }

    
    
    
    
    public BookDTO addBook(BookDTO bookDTO) {
        Book book = new Book(bookDTO.getIsbn(), bookDTO.getTitle(), bookDTO.getAuthor(), bookDTO.getPublisher(), bookDTO.getPublishYear());
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(book);
  
            
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new BookDTO(book);
    }

    

    
    //først laver vi en metode som skal returnere en liste af BOOKDTO's
    public List<BookDTO> getBookList() {
        //instantiere entitymanageren
        EntityManager em = emf.createEntityManager();
        
        //laver tom arraylist af BOOKDTO og kalder den "dtos"
        List<BookDTO> dtos = new ArrayList();
        try{
            
            //starter transaktionen
            em.getTransaction().begin();
            
            // laver en typed quiry af Books
            TypedQuery<Book> TQ = em.createQuery("SELECT b FROM Book b", Book.class);
            
            
            //Så henter vi dataen via getResultList fra typedquery(TQ) og getResultlist levere en liste af books som
            //vi gemmer i List<Book> books. 
            List<Book> books =  TQ.getResultList();
            
            //For hver book der er i books 
            
            for (Book book : books) {
                //laver vi en ny dto via book fra books
                BookDTO dto = new BookDTO(book);
                
                //adder vi den nye dto til arraylisten dtos
                dtos.add(dto);
                
            }
            
            
        } finally{
            em.close();
        }
       
        //returnere arraylisten dtos, som den nye dto er tilføjet til. 
        return dtos;
    }
    
    public void deleteBook(long isbn) {
        EntityManager em = emf.createEntityManager();

     //   BookDTO bookDTO = em.find(BookDTO.class, isbn);
        Book book = em.find(Book.class, isbn);

        em.getTransaction().begin();
        em.remove(book);
        em.getTransaction().commit();
        em.close();
    }
    
    
    

    
}


