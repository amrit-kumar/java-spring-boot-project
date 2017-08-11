package com.test;
import java.sql.Connection;
import java.sql.DriverManager;

public class SqlConnection {

    static public Connection getConn()
    {
        Connection con=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testDb","root","password");
        }
        catch(Exception e)
        {
            System.out.println("ERROR"+e);
        }
        return con;
    }
}
