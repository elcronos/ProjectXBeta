package com.capcarde.Beans;

import com.capcarde.DB.PoolConectDB;
import java.io.PrintStream;
import java.io.Serializable;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="formulario")
public class FormularioBean
    implements Serializable
{

    public FormularioBean()
    {
        IDProjectFormulario = 0;
        FkeyProject = 0;
    }

    public int getIDProjectFormulario()
    {
        return IDProjectFormulario;
    }

    public void setIDProjectFormulario(int IDProjectFormulario)
    {
        this.IDProjectFormulario = IDProjectFormulario;
    }

    public int getFkeyProject()
    {
        return FkeyProject;
    }

    public void setFkeyProject(int FkeyProject)
    {
        this.FkeyProject = FkeyProject;
    }

    public String getNameFormulario()
    {
        return nameFormulario;
    }

    public void setNameFormulario(String nameFormulario)
    {
        this.nameFormulario = nameFormulario;
    }

    public String getDescFormulario()
    {
        return descFormulario;
    }

    public void setDescFormulario(String descFormulario)
    {
        this.descFormulario = descFormulario;
    }

    public void insert()
    {
        try
        {
            PoolConectDB p = new PoolConectDB();
            Statement s = p.getConnection().createStatement();
            String sql = (new StringBuilder()).append("insert into projectformulario values(").append(secuencia()).append(",").append(FkeyProject).append(",'").append(nameFormulario).append("','").append(descFormulario).append("');").toString();
            s.execute(sql);
            System.out.println("Formulario Creado OK");
            p.getConnection().close();
        }
        catch(SQLException ex)
        {
           
        }
    }

    public void update()
    {
        try
        {
            PoolConectDB p = new PoolConectDB();
            Statement s = p.getConnection().createStatement();
            String sql = (new StringBuilder()).append("update projectformulario set nameformulario='").append(nameFormulario).append("',descformulario='")
                    .append(descFormulario).append("' where IDProjectFormulario=").append(IDProjectFormulario).toString();
            s.execute(sql);
            System.out.println("Formulario Actualizado OK");
            p.getConnection().close();
        }
        catch(SQLException ex)
        {
            
        }
    }

    public void delete()
    {
        try
        {
            PoolConectDB p = new PoolConectDB();
            Statement s = p.getConnection().createStatement();
            String sql = (new StringBuilder()).append("delete projectformulario where IDProjectFormulario=").append(IDProjectFormulario).toString();
            s.execute(sql);
            System.out.println("Formulario Actualizado OK");
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
            for(ResultSet rs = s.executeQuery("select max(idprojectformulario) from ProjectFormulario"); rs.next();)
                sec = rs.getInt(1);

            p.getConnection().close();
        }
        catch(SQLException ex)
        {
            
        }
        return sec + 1;
    }

    private int IDProjectFormulario;
    private int FkeyProject;
    private String nameFormulario;
    private String descFormulario;
}

