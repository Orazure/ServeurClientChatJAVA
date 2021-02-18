import java.io.*;
import java.net.Socket;

public class IOCommandes {
    private PrintStream ecritureEcran;

    private BufferedReader lectureEcran;

    private PrintStream ecritureReseau;

    private BufferedReader lectureReseau;

    private BufferedReader lectureAsynchrone;

    private final Socket maSocket;

    public IOCommandes(Socket pSocket)
    {
        ecritureEcran=System.out;
        lectureEcran=new BufferedReader(new InputStreamReader(System.in));

        try
        {
            ecritureReseau=new PrintStream(pSocket.getOutputStream(), true);
            lectureReseau=new BufferedReader(new InputStreamReader(pSocket.getInputStream()));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        this.maSocket = pSocket;
        InputStreamReader in = new InputStreamReader(System.in);
        this.lectureEcran=new BufferedReader(in);
        this.ecritureEcran=new PrintStream(System.out);
    }

    public void ecrireEcran(String pText){
        ecritureEcran.println(pText);
    }

    public String lireEcran()
    {
        String saisie = "";
        try {
            saisie=lectureEcran.readLine();
        }catch(IOException erreur)
        {
            erreur.printStackTrace();
        }
        return saisie;
    }

    public void ecrireReseau(String texte)
    {
        //ecrireEcran(">"+texte);
        ecritureReseau.println(texte);
    }

    public String lireReseau()
    {
        String ligne=null;
        try
        {
            ligne=lectureReseau.readLine();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return(ligne);
    }
}
