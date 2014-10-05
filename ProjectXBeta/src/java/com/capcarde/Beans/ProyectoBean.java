package com.capcarde.Beans;

import com.capcarde.DB.PoolConectDB;
import java.io.PrintStream;
import java.io.Serializable;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="proyecto")
public class ProyectoBean
    implements Serializable
{

    public ProyectoBean()
    {
    }

    public String getNombreProyecto()
    {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto)
    {
        this.nombreProyecto = nombreProyecto;
    }

    public String getDescProyecto()
    {
        return descProyecto;
    }

    public void setDescProyecto(String descProyecto)
    {
        this.descProyecto = descProyecto;
    }

    public String getAbrevProyecto()
    {
        return abrevProyecto;
    }

    public void setAbrevProyecto(String abrevProyecto)
    {
        this.abrevProyecto = abrevProyecto;
    }

    public void result()
    {
        System.out.println((new StringBuilder()).append("Name:").append(nombreProyecto).append(" abre:").append(abrevProyecto).append(" Desc:").append(descProyecto).toString());
    }

    public void crearProyecto()
    {
        try
        {
            PoolConectDB p = new PoolConectDB();
            Statement s = p.getConnection().createStatement();
            this.IDProyecto= secuencia();
            String sql = (new StringBuilder()).append("insert into projecttable values(").append(IDProyecto).append(",'").append(nombreProyecto).append("','").append(descProyecto).append("','").append(abrevProyecto).append("');").toString();
            s.execute(sql);
            System.out.println("Proyecto Creado OK");
            p.getConnection().close();
        }
        catch(SQLException ex)
        {
          
        }
    }

    public int secuencia()
    {
        int sec = 0;
        try
        {
            PoolConectDB p = new PoolConectDB();
            Statement s = p.getConnection().createStatement();
            for(ResultSet rs = s.executeQuery("select max(idproject) from ProjectTable"); rs.next();)
                sec = rs.getInt(1);

            p.getConnection().close();
        }
        catch(SQLException ex)
        {
          
        }
        return sec + 1;
    }

    public int getIDProyecto()
    {
        return IDProyecto;
    }

    public void setIDProyecto(int IDProyecto)
    {
        this.IDProyecto = IDProyecto;
    }

    private int IDProyecto;
    private String nombreProyecto;
    private String descProyecto;
    private String abrevProyecto;
}
