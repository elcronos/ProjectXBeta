package com.capcarde.DB;

import java.io.PrintStream;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PoolConectDB
{

    public PoolConectDB()
    {
        connection = null;
        initConect();
    }

    private void initConect()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            String path = "/Users/camilo/NetBeansProjects/ProjectXBeta/db/ProjectDB.sqlite";
            System.out.println((new StringBuilder()).append("PRUEBA:").append(path).toString());
            connection = DriverManager.getConnection((new StringBuilder()).append("jdbc:sqlite:").append(path).toString());
        }
        catch(ClassNotFoundException ex)
        {
           
        }
        catch(SQLException ex)
        {
            
        }
    }

    public Connection getConnection()
    {
        try
        {
            if(connection.isClosed())
                initConect();
        }
        catch(SQLException ex)
        {
            
        }
        return connection;
    }

    private Connection connection;
}

