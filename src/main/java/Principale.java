public class Principale {

    public static void main(String[] args) throws InterruptedException {
        //String IP = "grit.esiee-amiens.fr";
        //int port = 8599;
        /*
        String IP = "127.0.0.1";
        int port = 7777;
        Socket maSocket = null;
        try {
            maSocket = new Socket(IP,port);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        IOCommandes IO= new IOCommandes(maSocket);
        file monFichier= new file("test.txt","test.txt");
        if(monFichier.writeInFile("Elyes est nul a cs\nsalut"))
            System.out.println(monFichier.readFile());
        String saisie = "";
        do{
            saisie = IO.lireEcran();
            IO.ecrireReseau(saisie);
            IO.lireReaseau();
        }while((!saisie.equals("quit")) );
        try {
            maSocket.close();
        }catch(IOException e){
            e.printStackTrace();
        }*/
        //monFichier.close();
        ThreadClient myClient = new ThreadClient();

        myClient.startListening();
        myClient.writeMessages();

    }
}