class autherClient {
    public static void main(String[] args) throws InterruptedException {
        //String IP = "grit.esiee-amiens.fr";
        //int port = 8599;
        ThreadClient myClient = new ThreadClient();
        myClient.startListening();
        myClient.writeMessages();
    }
}