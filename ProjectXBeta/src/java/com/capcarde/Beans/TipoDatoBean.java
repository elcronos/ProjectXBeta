package com.capcarde.Beans;

import com.capcarde.DB.PoolConectDB;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;

// Referenced classes of package com.capcarde.Beans:
//            ListaProyectoBean, ProyectoBean
@ManagedBean

public class TipoDatoBean
    implements Serializable
{

    public TipoDatoBean()
    {
    }

    public int getIDTipoDato()
    {
        return IDTipoDato;
    }

    public void setIDTipoDato(int IDTipoDato)
    {
        this.IDTipoDato = IDTipoDato;
    }

    public String getTipoDato()
    {
        return TipoDato;
    }

    public void setTipoDato(String TipoDato)
    {
        this.TipoDato = TipoDato;
    }

    public String getSQLTipo()
    {
        return SQLTipo;
    }

    public void setSQLTipo(String SQLTipo)
    {
        this.SQLTipo = SQLTipo;
    }

    public String getJavaTipo()
    {
        return JavaTipo;
    }

    public void setJavaTipo(String javaTipo)
    {
        JavaTipo = javaTipo;
    }

    public String getTipoValue()
    {
        return TipoValue;
    }

    public void setTipoValue(String TipoValue)
    {
        this.TipoValue = TipoValue;
    }

    public ArrayList getLista()
    {
        return lista;
    }

    public void setLista(ArrayList lista)
    {
        this.lista = lista;
    }

    public void insert()
    {
          PoolConectDB pc;
            pc = new PoolConectDB();
            ResultSet rs = null;
            Statement st = null;
        try {
          
            st = pc.getConnection().createStatement();
            String sql = (new StringBuilder()).append("insert into tipodato values(").append(secuencia()).append(",'").append(TipoDato).append("','").append(SQLTipo).append("','").append(JavaTipo).append("','").append(TipoValue).append("')").toString();
            st.execute(sql);
            SQLException ex;
            
            pc.getConnection().close();
        } catch (SQLException ex) {
            Logger.getLogger(TipoDatoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
     
         

    }

    public int secuencia()
    {
        int sec = 0;
        try
        {
            PoolConectDB p = new PoolConectDB();
            Statement s = p.getConnection().createStatement();
            for(ResultSet rs = s.executeQuery("select max(Idtipodato) from tipodato"); rs.next();)
                sec = rs.getInt(1);

            p.getConnection().close();
        }
        catch(SQLException ex)
        {
           
        }
        return sec + 1;
    }

    private int IDTipoDato;
    private String TipoDato;
    private String SQLTipo;
    private String JavaTipo;
    private String TipoValue;
    private ArrayList lista;
}

