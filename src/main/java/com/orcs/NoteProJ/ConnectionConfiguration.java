package com.orcs.NoteProJ;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionConfiguration {

    public static Connection getConnection()
    {
        Connection connection = null;


        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/notesDB","root","root");
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return  connection;
    }
}
