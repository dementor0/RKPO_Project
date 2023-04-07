package com.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/server?useSSL=no";
    private static final String user = "root";
    private static final String password = "root";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;



    public static void main(String[] args) throws SQLException {
        LinkedList<String> temp = new LinkedList<>();
        temp.add("SELECT COUNT(user_id) FROM server.users");
        // открываем соединение с базой данных
        con = DriverManager.getConnection(url, user, password);
        stmt = con.createStatement();
        rs = stmt.executeQuery(temp.get(0));
        // очередь запросов к бд
        while (rs.next()) {
            quantity_users = rs.getInt(1);
        }
        String Action = "";
        do {
            Socket_in p1= new Socket_in(port);
            Action = p1.get();
            port +=1;

            switch (Action) {
                case ("Authorization"): {
                    System.out.println("Authorization");
                    String Client_Login = Socket_data();
                    port += 1;

                    String Client_Password = Socket_data();
                    port += 1;

                    boolean result = false;
                    String out = "no";
                    result = Authorization(Client_Login, Client_Password);
                    if (result == true) {
                        out = "ok";
                    }
                    new Socket_out(9000, out);
                    break;
                }
                case ("Registration"): {
                    Registration();
                    quantity_users += 1;
                    break;
                }
                case ("Card"): {
                    System.out.println("Card");
                    Card(id_user);
                    System.out.println(card);
                    break;
                }
            }
        }while (Action!="Close");
        try {
            // открываем соединение с базой данных
            con = DriverManager.getConnection(url, user, password);

            stmt = con.createStatement();
        }
        catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and result set here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }
        }
    }
        /*  АВТОРИЗАЦИЯ  */
    public static boolean Authorization (String Front_Login, String Front_Password) throws SQLException {
        String Server_Login;
        String Server_Password;
        boolean result = false;
        for (int i = 0; i < quantity_users; i++) {
            ArrayList<String> query = new ArrayList<>();
            query.add("SELECT * FROM server.users WHERE user_id = " + i);
            rs = stmt.executeQuery(query.get(0));
            while (rs.next()) {
                Server_Login = rs.getString(2);
                Server_Password = rs.getString(3);
                if ((Server_Login.equals(Front_Login)) && (Server_Password.equals(Front_Password))) {
                    result = true;
                    id_user = i+1;
                    System.out.println(id_user);
                }
            }
        }
        return result;
    }
        /*  РЕГИСТРАЦИЯ  */
    public static void Registration ()throws SQLException{
        String New_Login = "Kennys";
        String New_Password = "ACE";
        String codeword = "DEagle";
        String request = ("INSERT INTO users(user_id, login, password, word) VALUES (?,?,?,?)");
        PreparedStatement pst = con.prepareStatement(request);
        pst.setInt(1,quantity_users);
        pst.setString(2,New_Login);
        pst.setString(3,New_Password);
        pst.setString(4,codeword);
        pst.execute();
    }
    public static String Socket_data(){
            String str = "";
            Socket_in p1= new Socket_in(port);
            str = p1.get();
            return str;
    }

    /*  ПОЛУЧЕНИЕ ДАННЫХ КАРТЫ */
    public static void Card (int user_id) throws SQLException {
            ArrayList<String> query = new ArrayList<>();
            query.add("SELECT * FROM server.cards WHERE user_id = " + user_id);
            rs = stmt.executeQuery(query.get(0));
            while (rs.next()){
                card.add(rs.getString(3));
                card.add(rs.getString(4));
                card.add(rs.getString(5));
            }
    }
    /*  ПОЛУЧЕНИЕ РАСХОДОВ  */
    public static void Expenses(int card_id) throws SQLException{
        ArrayList<String> query = new ArrayList<>();
        ArrayList<String> query_exp = new ArrayList<>();
        ArrayList<String> month = new ArrayList<>();
        String sum_exp = "";
        query.add("SELECT expence_id FROM server.expence WHERE card id = " + card_id);
        rs = stmt.executeQuery(query.get(0));
        String temp = "";
        while (rs.next()){
            temp = rs.getString(0);
          //  if (temp)
        }

        for(int i=0; i < query.size(); i++){
            query_exp.add("SELECT value, month FROM server.expence WHERE expence_id " +i);
            rs = stmt.executeQuery(query.get(0));
            sum_exp+= rs.getString(3);
       }
        for(int j=0; j < query.size(); j++) {
            query_exp.add("SELECT month FROM server.expence WHERE expence_id " + j);
            rs = stmt.executeQuery(query.get(0));
            while(rs.next()){
                month.add(rs.getString(4));
            }
        }
    }
    /*  ПОЛУЧЕНИЕ ДОХОДОВ  */
    public static void Income(int card_id) throws SQLException{
        ArrayList<String> que = new ArrayList<>();
        ArrayList<String> query_inc = new ArrayList<>();
        ArrayList<String> month = new ArrayList<>();
        String sum_exp = "";
        que.add("SELECT income_id FROM server.income WHERE card_id = " + card_id);
        rs = stmt.executeQuery(que.get(0));
        for(int i=0; i < que.size(); i++){
            query_inc.add("SELECT value, month FROM server.income WHERE income_id " +i);
            rs = stmt.executeQuery(que.get(0));
            sum_exp+= rs.getString(3);
        }
        for(int j=0; j < que.size(); j++) {
            query_inc.add("SELECT month FROM server.income WHERE income_id " + j);
            rs = stmt.executeQuery(que.get(0));
            while(rs.next()){
                month.add(rs.getString(4));
            }
        }
    }
}

