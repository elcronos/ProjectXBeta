package com.capcarde.Beans;

import com.capcarde.DB.PoolConectDB;
import java.io.Serializable;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class LoginBean
    implements Serializable
{
    
    private String user;
    private String pass;

    public LoginBean()
    {
        user = "";
        pass = "";
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getPass()
    {
        return pass;
    }

    public void setPass(String pass)
    {
        this.pass = pass;
    }

    public String validaUsuario(){
        PoolConectDB db;
        int idUser;
        String sql;
        db = new PoolConectDB();
        Statement st = null;

        String result = "";
        idUser = 0;
        sql = "select iduser from usuariosistema where nickname='"+user+"' and password='"+pass+"'";
        
        try {
            st = db.getConnection().createStatement();
        } catch (SQLException ex) {
           
        }
        try {
            for(ResultSet rs = st.executeQuery(sql); rs.next();) {
                idUser += rs.getInt(1);
            }
            db.getConnection().close();
        
        if(idUser > 0) {
                result = "index.xhtml";
                this.setUser(user);
                this.setPass(pass);
                
            }
        else {
                result = "views/loginWrong.xhtml";
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return result;
    }


    public String logOut()
    {
        user = null;
        pass = null;
        return "login.xhtml";
    }

  
}
