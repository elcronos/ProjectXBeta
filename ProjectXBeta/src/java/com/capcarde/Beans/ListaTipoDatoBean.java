package com.capcarde.Beans;

import com.capcarde.DB.PoolConectDB;
import java.io.PrintStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name="listaTipo")
@SessionScoped
public class ListaTipoDatoBean
  implements Serializable
{
  private ArrayList<TipoDatoBean> lista;
  private TipoDatoBean selected;

  public ListaTipoDatoBean()
  {
    initLista();
  }

  private void initLista()
  {
    this.lista = new ArrayList();
    PoolConectDB pc = new PoolConectDB();
    ResultSet rs = null;
    Statement st = null;
    try
    {
      st = pc.getConnection().createStatement();

      rs = st.executeQuery("select IDTipoDato,TipoDato,SQLTipo,JavaTipo,TipoValue from TipoDato");
      while (rs.next()) {
        TipoDatoBean tipo = new TipoDatoBean();
        tipo.setIDTipoDato(rs.getInt("IDTipoDato"));
        tipo.setTipoDato(rs.getString("TipoDato"));
        tipo.setSQLTipo(rs.getString("SQLTipo"));
        tipo.setJavaTipo(rs.getString("JavaTipo"));
        tipo.setTipoValue(rs.getString("TipoValue"));
        this.lista.add(tipo);
      }
    }
    catch (SQLException ex) {
      Logger.getLogger(ListaProyectoBean.class.getName()).log(Level.SEVERE, null, ex);
    }
    finally {
      try {
        pc.getConnection().close();
      }
      catch (SQLException ex)
      {
      }
    }
  }

  public ArrayList<TipoDatoBean> getLista()
  {
    return this.lista;
  }

  public void setLista(ArrayList<TipoDatoBean> lista) {
    this.lista = lista;
  }

  public TipoDatoBean getSelected() {
    return this.selected;
  }

  public void setSelected(TipoDatoBean selected) {
    this.selected = selected;
  }

  public void update() {
    initLista();
  }

  public void onEdit(RowEditEvent event) {
    TipoDatoBean tipo = (TipoDatoBean)event.getObject();
    this.selected = tipo;
    update();
  }

  public void onCancel(RowEditEvent event) {
    System.out.println("Cancelar");
  }

  public void updateForm()
  {
    PoolConectDB pc = new PoolConectDB();
    ResultSet rs = null;
    Statement st = null;
    try {
      st = pc.getConnection().createStatement();
      String sql = "update tipodato set idtipodato=" + this.selected.getIDTipoDato() + ",tipodato='" + this.selected.getTipoDato() + "',SQLTipo='" + this.selected.getSQLTipo() + "'," + "javaTipo='" + this.selected.getJavaTipo() + "',tipovalue='" + this.selected.getTipoValue();

      st.execute(sql);
    } catch (SQLException ex) {
      Logger.getLogger(ListaProyectoBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        pc.getConnection().close();
      }
      catch (SQLException ex) {
        Logger.getLogger(ListaProyectoBean.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  public void deleteForm()
  {
    PoolConectDB pc = new PoolConectDB();
    ResultSet rs = null;
    Statement st = null;
    try {
      st = pc.getConnection().createStatement();
      String sql = "delete from tipodato where IDTipoDato=" + this.selected.getIDTipoDato();
      st.execute(sql);
    } catch (SQLException ex) {
      Logger.getLogger(ListaProyectoBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        pc.getConnection().close();
      }
      catch (SQLException ex) {
        Logger.getLogger(ListaProyectoBean.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
}