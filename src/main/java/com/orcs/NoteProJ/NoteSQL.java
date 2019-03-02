package com.orcs.NoteProJ;

import com.sun.xml.internal.bind.v2.TODO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NoteSQL {


    public void NoteSql()
    {
        //TODO insert notename noteid etc in constructor and remove the parameters from the functions
    }

    public void newNote(String date,String noteName,int userId) //sql query for creating a new note
    {
        Connection connection = ConnectionConfiguration.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO notes (date,noteName,userid)" +
                    "VALUES (?, ?,?)");
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, noteName);
            preparedStatement.setInt(3, userId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void editText(String subText,int noteId)//sql query to edit and update a text
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String Sql = "UPDATE notes set note=? where noteid = ?";
        try {
            Connection connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setString(1,subText);
            preparedStatement.setInt(2,noteId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e+"ERROR!!");
        }
    }

    public void deleteText(int noteId) // sql query to delete a note
    {
        Connection connection = ConnectionConfiguration.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String Sql = "DELETE from notes where noteid=?";
        try {
            preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setInt(1,noteId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addImageURL(int noteId,String url,String noteName) //sql query to add image
    {
        Connection connection = ConnectionConfiguration.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO photos (noteid,photo,noteName)" +
                    "VALUES (?, ?,?)");
            preparedStatement.setInt(1, noteId);
            preparedStatement.setString(2, url);
            preparedStatement.setString(3, noteName);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePhoto(int noteId) //sql query to delete foto
    {
        Connection connection = ConnectionConfiguration.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM photos WHERE noteID=?");
            preparedStatement.setInt(1, noteId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet showPhotos(int noteId) // sql query to show all photos
    {
        Connection connection = ConnectionConfiguration.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String Sql = "Select * from photos where noteid=?";

        try {
            preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setInt(1, noteId);
            rs = preparedStatement.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
        }
        return rs;
    }

    public ResultSet showAudios(int noteId) //sql query to show audio clip
    {
        Connection connection = ConnectionConfiguration.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String Sql = "Select * from notes where noteid=?";

        try {
            preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setInt(1, noteId);
            rs = preparedStatement.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
        }
        return rs;
    }

    public void addAudioURL(String soundUrl,int noteId) //sql query to add a new audio url
    {
        Connection connection = ConnectionConfiguration.getConnection();
        PreparedStatement preparedStatement1 = null;
        ResultSet rs1 = null;
        String Sql1 = "UPDATE notes set audios=? where noteid = ?";

        try {
            preparedStatement1 = connection.prepareStatement(Sql1);
            preparedStatement1.setString(1,soundUrl);
            preparedStatement1.setInt(2,noteId);
            preparedStatement1.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ResultSet login(String username) //sql query gia to login
    {
        Connection connection = ConnectionConfiguration.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String Sql = "Select * from registers where username=?";

        try {
            preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setString(1, username);
            rs = preparedStatement.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rs;
    }

    public void register(String usrname,String password,String email,String name,String surname) //sql query gia to register
    {
        Connection connection = ConnectionConfiguration.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO registers (username,password,email,name,surname)" +
                    "VALUES (?, ?,?,?,?)");
            preparedStatement.setString(1, usrname);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4,name);
            preparedStatement.setString(5,surname);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet BasicActivityLoad(int id) //sql query gia na fortwsei ta deodmena tis vasikis formas
    {
        Connection connection = ConnectionConfiguration.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String Sql = "Select * from registers where id=?";

        try {
            preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setString(1, String.valueOf(id));
            rs = preparedStatement.executeQuery();


        } catch (Exception e) {
            System.out.println(e);
        }

        return rs;
    }
}
