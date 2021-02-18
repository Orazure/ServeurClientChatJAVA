

import java.sql.*;


public class ConnectToDb{

    public static void main(String[] args) {// TEST POUR SAVOIR SI NOTRE BASE DE DONNEE EST DISPO
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:ServeurChat.db";
            try{
                Connection conn = DriverManager.getConnection(dbURL);
                String sql="Select * from user";
                Statement statement=conn.createStatement();
                ResultSet rs=statement.executeQuery(sql);
                while(rs.next()){
                    String name = rs.getString("identifiant");
                    String mdp = rs.getString("mdp");
                    System.out.println(name+mdp);
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }


    public int ajoutUtilisateur(String p_id, String p_mdp){
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:ServeurChat.db";
            try{
                Connection conn = DriverManager.getConnection(dbURL);
                System.out.println ( "Ouvert base de données avec succès");
                String sql="INSERT INTO user (identifiant,mdp) VALUES (?,?);";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1,p_id);
                preparedStatement.setString(2,p_mdp);
                int rs=preparedStatement.executeUpdate();
                return rs;
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return 0;
        }
        return 0;
    }

    public int connexion(String p_id, String p_mdp){
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:ServeurChat.db";
            try{
                Connection conn = DriverManager.getConnection(dbURL);
                System.out.println ( "Ouvert base de données avec succès");
                String sql="Select identifiant,mdp from user where identifiant=? and mdp=?;";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1,p_id);
                preparedStatement.setString(2,p_mdp);
                try {
                    ResultSet resSql=preparedStatement.executeQuery();
                    String value = resSql.getString(1);
                    System.out.println(value);
                    return 1;
                }catch(SQLException ex){
                    ex.printStackTrace();
                    return 0;
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return 0;
        }
        return 0;
    }

}