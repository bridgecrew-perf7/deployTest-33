package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;
import dtos.BookDTO;
import entities.Book;
import entities.User;
import errorhandling.API_Exception;
import errorhandling.GenericExceptionMapper;
import facades.BookFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utils.EMF_Creator;

@Path("book")
public class BookResource {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create(); 
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static BookFacade facade = BookFacade.getBookFacade(EMF);
   
    @POST
    //@RolesAllowed()
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("add")
    public String addbook(String jsonString) {
        
  
        //Vi konvertere vores json tekst streng til en BookDTO. 
        BookDTO bookdto = GSON.fromJson(jsonString, BookDTO.class);
        
        
        //Så laver vi et nyt objekt, hvor vi tager det objekt der nu er json tekst i,
        //og bruger som parameter i vores facade.addbook metode. 
       
        BookDTO bookAdded = facade.addBook(bookdto);
        return GSON.toJson(bookAdded);
    }

    
    
    
    
    
    
    
    
      @GET
      @Produces(MediaType.APPLICATION_JSON)
      @Path("all")
      public String getAllBooks(){
          
           List<BookDTO> getAllBooksDTO = facade.getBookList();    
            return GSON.toJson(getAllBooksDTO);
        }
      
      
      
      
     
      
      
      @DELETE
      @Path("delete/{isbn}")
      @Produces(MediaType.APPLICATION_JSON)
      public void deleteBook(@PathParam("isbn") Long isbn){
            
  
         facade.deleteBook(isbn);

          
      }

      
      }
   
