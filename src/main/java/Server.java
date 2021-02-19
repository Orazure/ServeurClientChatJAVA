import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket listener;
    private final ConnectToDb conDb;
    private int clientNumber = 0;
    private final File m_file;
    private final List<ServiceThread> mesThreads = new ArrayList();
    private final List<String> identifiants = new ArrayList();
    private boolean isAvailable=false;
    public Server()
    {
        this.conDb=new ConnectToDb();
        try {
            listener = new ServerSocket(7777);

        }catch(IOException ex){
            ex.printStackTrace();
            System.exit(1);
        }
        this.m_file= new File("serverLog.txt","serverLog.txt");
    }

    public void leaveChat(ServiceThread pThread)
    {
        int index = mesThreads.indexOf(pThread);
        identifiants.remove(index);
        mesThreads.remove(pThread);
        clientNumber--;
        System.out.println(identifiants);
    }



    public void notifyAllMyClient(String pMsg, ServiceThread pEnvoyeur)
    {
        for (ServiceThread mtThread: mesThreads) {
            if(mtThread != pEnvoyeur && mtThread.isAvailable())
                mtThread.notify(pMsg);
        }
    }

    private class ServiceThread extends Thread {

        private final int clientNumber;
        private final Socket socketOfServer;
        private BufferedReader is;
        private BufferedWriter os;
        private String ID="";
        private final Weather meteo=new Weather();

        public ServiceThread(Socket socketOfServer, int clientNumber) {
            this.clientNumber = clientNumber;
            this.socketOfServer = socketOfServer;
            // Log
            log("New connection with client : " + this.clientNumber + " socket : " + socketOfServer);
        }

        public boolean isAvailable() {
            return isAvailable;
        }

        public void notify(String pMsg)
        {
            try {
                os.write(">> " + pMsg);
                // End of line.
                os.newLine();
                // Flush data.
                os.flush();
            } catch (IOException e) {
                System.out.println("Impossible d'envoyer le message à tous le monde");
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            boolean firstConnection=true;
            try {
                // Open input and output streams
                is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
                os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));

                while (true) {

                    String line ="";
                    //premiere connexion
                    if(firstConnection)
                    {
                        //os.write("<< Veuillez saisir un identifiant pour votre compte");
                        //os.newLine();
                        //os.flush();
                        notify("<< Création de compte ->1");
                        try {
                            sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        notify("<< Connexion ->2");
                        String choix=is.readLine();
                        if(choix.equals("1")){
                            notify("<< Veuillez saisir un identifiant pour votre compte");
                            String identifiant=is.readLine();
                            notify("<< Veuillez saisir un mot de passe pour votre compte");
                            String mdp=is.readLine();
                            if(conDb.testIdUnique(identifiant)==1)
                                notify("identifiant déjà pris");
                            else if(conDb.ajoutUtilisateur(identifiant,mdp)==1){
                                firstConnection=false;
                                isAvailable=true;
                                notify("10");

                            }else{
                                notify("Erreur lors de la requête");
                            }
                            this.ID=identifiant;
                            identifiants.add(identifiant);

                        }else
                        {
                            notify("<< Veuillez saisir un identifiant pour votre compte");
                            String identifiant=is.readLine();
                            System.out.println(identifiant);
                            try {
                                sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            notify("<< Veuillez saisir un mot de passe pour votre compte");
                            String mdp=is.readLine();
                            System.out.println(mdp);
                            if(conDb.connexion(identifiant,mdp)==1){
                                firstConnection=false;
                                isAvailable=true;
                                notify("10");
                            }else{
                                notify("Mauvais identifiant ou mot de passe");
                            }
                            this.ID=identifiant;
                            identifiants.add(identifiant);
                        }
                        //choix du salon
                    }else
                    {
                        try {
                            sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //lecture msg client
                        line = is.readLine();
                        //log de tous les messages
                        log("client n : "+this.clientNumber+" ip "+ socketOfServer +" msg : "+line);


                    switch(line) {
                        case "quit":
                            System.out.println("Saisie quit");
                            break;

                        case "!meteo":
                            System.out.println("meteo");
                            notify("!meteo");
                            try {
                                sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            notify(meteo.printTemp());
                            notify(meteo.printWind());
                            break;

                        case "!help":
                            System.out.println("Commande dispo");
                            notify("Oui salut");
                            break;

                        case "!connexion":

                            break;
                    }

                        if (line.equals("quit") || line.equals("QUIT")) {
                            os.write("GoodBye My Lover !");
                            os.newLine();
                            os.flush();
                            //on quitte le chat
                            System.out.println(is.readLine());
                            leaveChat(this);
                            break;
                        }
                        // Write to sockets

                        if(!line.equals(""))
                        {
                            line = this.ID+ " : " +line;
                            notifyAllMyClient(line,this);
                        }
                }



                    //lecture msg client
                    //line = is.readLine();
                    //log de tous les messages
                    //log("client n : "+this.clientNumber+" ip "+ socketOfServer +" msg : "+line);
                    // Si QUIT on kill le thread

                    // (Send to client)
                    //os.write(">> " + line);
                    // End of line.
                    // os.newLine();
                    // Flush data.
                    //os.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-4);
            }
        }
    }

    public void waitConnection()
    {
        try {
            while (true) {
                // Accept client connection request
                // Get new Socket at Server.
                if(mesThreads.size()==3) //TODO remettre 2 ici
                    System.out.println("Pas de place");
                else{
                    Socket socketOfServer = listener.accept();
                    //add to list
                    mesThreads.add(new ServiceThread(socketOfServer, clientNumber++));
                    //launch du last
                    mesThreads.get(mesThreads.size()-1).start();
                    System.out.println(mesThreads);
                }
                //new ServiceThread(socketOfServer, clientNumber++).start();
            }
        } catch(IOException connection)
        {
            connection.printStackTrace();
            System.exit(1);
        }finally {
            close();
        }
    }




    private void close(){
        try {
            listener.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-3);
        }
    }

    public void log(String message) {
        message=message+"\n";
        this.m_file.writeInFile(message);
        System.out.print(message);
    }
}
