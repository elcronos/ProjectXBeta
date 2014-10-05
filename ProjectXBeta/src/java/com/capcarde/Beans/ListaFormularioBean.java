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

@ManagedBean(name="listaForm")
@SessionScoped
public class ListaFormularioBean
  implements Serializable
{
  private List<FormularioBean> listaFormulario;
  private FormularioBean selected;
  private int fkeyProyecto = 0;

  public ListaFormularioBean() {
    this.listaFormulario = new ArrayList();
    this.selected = new FormularioBean();
    initLista();
  }

  public List<FormularioBean> getListaFormulario() {
    return this.listaFormulario;
  }

  public int getSize() {
    return this.listaFormulario.size();
  }

  public void setListaFormulario(List<FormularioBean> listaFormulario) {
    this.listaFormulario = listaFormulario;
  }

  public FormularioBean getSelected() {
    return this.selected;
  }

  public void setSelected(FormularioBean selected) {
    this.selected = selected;
  }

  public int getFkeyProyecto() {
    return this.fkeyProyecto;
  }

  public void setFkeyProyecto(int fkeyProyecto) {
    this.fkeyProyecto = fkeyProyecto;
  }

  public void onEdit(RowEditEvent event) {
    FormularioBean formActual = (FormularioBean)event.getObject();
    this.selected = formActual;
    updateForm();
  }
  public void onCancel(RowEditEvent event) {
    System.out.println("Cancelar");
  }

  private void initLista()
  {
    PoolConectDB pc = new PoolConectDB();
    ResultSet rs = null;
    Statement st = null;
    try
    {
      st = pc.getConnection().createStatement();

      rs = st.executeQuery("select idprojectformulario,fkeyproject,nameformulario,descformulario from projectformulario where fkeyproject=" + this.fkeyProyecto);
      if(this.fkeyProyecto>0){
      while (rs.next()) {    
        selected.setIDProjectFormulario(rs.getInt("idprojectformulario"));
        selected.setFkeyProject(fkeyProyecto);
        selected.setNameFormulario(rs.getString("nameformulario"));
        selected.setDescFormulario(rs.getString("descformulario")); 
        this.listaFormulario.add(selected);
      }
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

  public void update()
  {
    ArrayList lista2 = new ArrayList();
    PoolConectDB pc = new PoolConectDB();
    ResultSet rs = null;
    Statement st = null;
    try
    {
      st = pc.getConnection().createStatement();

      rs = st.executeQuery("select idprojectformulario,fkeyproject,nameformulario,descformulario from projectformulario where fkeyproject=" +selected.getFkeyProject());
      
      while (rs.next()) { 
        FormularioBean f= new FormularioBean();  
        f.setIDProjectFormulario(rs.getInt("idprojectformulario"));
        f.setFkeyProject(selected.getFkeyProject());
        f.setNameFormulario(rs.getString("nameformulario"));
        f.setDescFormulario(rs.getString("descformulario")); 
        lista2.add(f);
      
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
    setListaFormulario(lista2);
  }

  public void updateForm()
  {
    PoolConectDB pc = new PoolConectDB();
    ResultSet rs = null;
    Statement st = null;
    try {
      st = pc.getConnection().createStatement();
      String sql = "update projectformulario set nameformulario='" + this.selected.getNameFormulario() + "',descformulario='" + this.selected.getDescFormulario() + "'" + " where IDProjectFormulario=" + this.selected.getIDProjectFormulario();

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
      String sql = "delete from projectformulario where IDProjectFormulario=" + this.selected.getIDProjectFormulario();
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