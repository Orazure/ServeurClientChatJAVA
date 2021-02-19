

import java.io.*;
import java.net.Socket;

public class ThreadClient {
    private Socket maSocket;
    private final IOCommandes IO;

    public ThreadClient() {

        String IP = "127.0.0.1";
        int port = 7777;
        try {
            maSocket = new Socket(IP,port);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        this.IO= new IOCommandes(maSocket);
    }

    public void ecrireReseau(String text){
        OutputStream os = null;
        try {
            os = maSocket.getOutputStream();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-2);
        }
        DataOutputStream dos = null;
        dos = new DataOutputStream(new BufferedOutputStream(os));
        String msg="";
        try {
            //Scanner saisie = new Scanner(System.in);
            String t= "wsh la mif";
            PrintWriter pred = new PrintWriter(new BufferedWriter(new OutputStreamWriter(maSocket.getOutputStream())), true);
            //dos.writeUTF(text);
            pred.println(text);
            //dos.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-3);
        }
    }

    public String lireReseau()
    {

        InputStream is = null;
        try {
            is = maSocket.getInputStream();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-4);
        }
        DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
        String msg="Pas modif";
        try {
            msg=dis.readLine();
            //System.out.println(msg);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-5);
        }
        return msg;
    }
    

    public void startListening(){
        listeningThread myClient = new listeningThread();
        myClient.start();
    }

    public void writeMessages(){
        String saisie = "";
        do{
            saisie = IO.lireEcran();
            IO.ecrireReseau(saisie);
        }while((!saisie.equals("quit")) );
        ecrireReseau("GoodBye My Friend !");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        close();
    }

    public boolean close()
    {
        try {
            maSocket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return true;
    }

    public void menu(){
        System.out.println("--------");
        System.out.println("Bonjour bienvenue sur le serveur de chat");
        System.out.println("Copyright : 2021 -crée par Gregoire & Alexandre ");
        System.out.println("Les commandes : ");
        System.out.println("!meteo");
        System.out.println("!help");
        System.out.println("Quitter : tapez \"quit\"");
        System.out.println("--------");
    }

    private class listeningThread extends Thread {
        boolean connection =true;
        String isConnected="d";
        String response="d";
        @Override
        public void run() {
            while(true)
            {
                System.out.println("debut");
                if(connection) {
                    System.out.println(lireReseau());
                    System.out.println(lireReseau());
                    System.out.println(lireReseau());
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(lireReseau());
                    isConnected=lireReseau();
                    try {
                        sleep(6);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(isConnected.equals(">> 10")){
                        System.out.println("Connexion");
                        connection=false;
                        menu();
                    } else {
                       System.out.println(isConnected);
                    }
                }else {
                    System.out.println("Wait message");
                    try {
                        sleep(6);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    response = lireReseau();

                    switch(response){
                        case ">> !menu":

                        break;

                        case ">> !meteo":
                            System.out.println(lireReseau());
                            System.out.println(lireReseau());
                        break;

                        case ">> !help":
                            System.out.println("Commande dispo");
                        break;

                        default:
                            System.out.println(response);

                    }

                    if(response.equals("GoodBye My Lover !")){
                        break;}
                    }
                }
            }

        }
    }

