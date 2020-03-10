/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gsb.rv.dr.technique;
import gsb.rv.dr.entities.Visiteur;
/**
 *
 * @author root
 */
public class Session {
    private static Session session = null;
    private static Visiteur leVisiteur;

    private Session(Visiteur leVisiteur) {
        this.leVisiteur =leVisiteur;
    }
    public static void ouvrir(Visiteur unVisiteur){
        Session.session=new Session(unVisiteur);
    }
       
    public Session getUneSession() {
        return session;
    }

    public static Visiteur getLeVisiteur() {
        return leVisiteur;
    }
   
    
   
    
    
    
}
