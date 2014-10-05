package com.capcarde.Beans;

import com.capcarde.DB.PoolConectDB;
import java.io.PrintStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name="listaProy")
@SessionScoped
public class ListaProyectoBean
  implements Serializable
{
  private List<ProyectoBean> lista;
  private List<ProyectoBean> filtroProy;
  private ProyectoBean selectedProy;

  public ListaProyectoBean()
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
      rs = st.executeQuery("select idproject,abreproject,nameproject, descproject from ProjectTable");
      while (rs.next()) {
        ProyectoBean proyecto = new ProyectoBean();
        proyecto.setIDProyecto(rs.getInt("IDProject"));
        proyecto.setNombreProyecto(rs.getString("nameproject"));
        proyecto.setDescProyecto(rs.getString("descproject"));
        proyecto.setAbrevProyecto(rs.getString("abreproject"));
        this.lista.add(proyecto);
      }
    }
    catch (SQLException ex) {
      Logger.getLogger(ListaProyectoBean.class.getName()).log(Level.SEVERE, null, ex);
    }
    finally {
      try {
        pc.getConnection().close();
        st.close();
        rs.close();
      }
      catch (SQLException ex)
      {
      }
    }
  }

  public List<ProyectoBean> getLista() {
    return this.lista;
  }

  public void setLista(List<ProyectoBean> lista) {
    this.lista = lista;
  }

  public List<ProyectoBean> getFiltroProy() {
    return this.filtroProy;
  }

  public void setFiltroProy(List<ProyectoBean> filtroProy) {
    this.filtroProy = filtroProy;
  }

  public int getSize() {
    return this.lista.size();
  }

  public ProyectoBean getSelectedProy() {
    return this.selectedProy;
  }

  public void setSelectedProy(ProyectoBean selectedProy) {
    this.selectedProy = selectedProy;
  }

  public void onEdit(RowEditEvent event) {
    ProyectoBean proyectoActual = (ProyectoBean)event.getObject();
    this.selectedProy = proyectoActual;
    updateProyecto();
  }

  public void onCancel(RowEditEvent event) {
    System.out.println("Cancelar");
  }

  public void update()
  {
    ArrayList lista2 = new ArrayList();
    PoolConectDB pc = new PoolConectDB();
    ResultSet rs = null;
    Statement st = null;
    try {
      st = pc.getConnection().createStatement();
      rs = st.executeQuery("select idproject,abreproject,nameproject, descproject from ProjectTable");
      while (rs.next()) {
        ProyectoBean proyecto = new ProyectoBean();
        proyecto.setIDProyecto(rs.getInt("IDProject"));
        proyecto.setNombreProyecto(rs.getString("nameproject"));
        proyecto.setDescProyecto(rs.getString("descproject"));
        proyecto.setAbrevProyecto(rs.getString("abreproject"));
        lista2.add(proyecto);
      }
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
    setLista(lista2);
  }

  public void updateProyecto()
  {
    PoolConectDB pc = new PoolConectDB();
    ResultSet rs = null;
    Statement st = null;
    try {
      st = pc.getConnection().createStatement();
      String sql = "update projecttable set nameproject='" + this.selectedProy.getNombreProyecto() + "',descproject='" + this.selectedProy.getDescProyecto() + "',abreproject='" + this.selectedProy.getAbrevProyecto() + "' where IDProject=" + this.selectedProy.getIDProyecto();

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

  public void deleteProyecto()
  {
    PoolConectDB pc = new PoolConectDB();
    ResultSet rs = null;
    Statement st = null;
    
    try {
      st = pc.getConnection().createStatement();
      String sql = "delete from projecttable where IDProject=" + this.selectedProy.getIDProyecto();
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