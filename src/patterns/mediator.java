/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package patterns;

/**
 *
 * @author Fabio Ricchiuti <Fab1234@hotmail.it>
 */
public class mediator{
public static abstract class Mediatore {
 
 
  public abstract void invia_ricevi(String Mittente, String Destinatario, String messaggio, Object o);
  
  
    }
  
 


public static abstract class Collega {
    
    public Mediatore m;
    String name;
    
    public Collega(Mediatore m){
        this.m=m;
    }
 
     public abstract void ricevi(String Mittente,String Messaggio, Object o);
     
     public void invia(String dest, String messagio, Object o){
         m.invia_ricevi(name, dest, messagio, o);
     }
     
    
     }

}