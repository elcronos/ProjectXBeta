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

@ManagedBean(name="listaPara")
@SessionScoped
public class ListaParametroBean
  implements Serializable
{
  private ArrayList<ParametroBean> listaParametros;
  private ParametroBean selected;
  private int fkeyFormulario = 0;

  public ListaParametroBean() {
    initLista();
  }

  public int getSize() {
    return this.listaParametros.size();
  }

  public ArrayList<ParametroBean> getListaParametros() {
    return this.listaParametros;
  }

  public void setListaParametros(ArrayList<ParametroBean> listaParametros) {
    this.listaParametros = listaParametros;
  }

  public ParametroBean getSelected()
  {
    return this.selected;
  }

  public void setSelected(ParametroBean selected) {
    this.selected = selected;
  }

  public int getFkeyFormulario() {
    return this.fkeyFormulario;
  }

  public void setFkeyFormulario(int fkeyFormulario) {
    this.fkeyFormulario = fkeyFormulario;
  }

  public void onEdit(RowEditEvent event) {
    ParametroBean paraActual = (ParametroBean)event.getObject();
    this.selected = paraActual;
    updatePara();
  }
  public void onCancel(RowEditEvent event) {
    System.out.println("Cancelar");
  }

  public void updatePara() {
    PoolConectDB pc = new PoolConectDB();
    ResultSet rs = null;
    Statement st = null;
    try {
      st = pc.getConnection().createStatement();
      String sql = "update campoformulario set namecampo='" + this.selected.getNameCampo() + "',esobligatorio='" + this.selected.getEsObligatorio() + "',fkeytipodato=" + this.selected.getFkeyTipo() + ",fkeyformulario=" + this.selected.getFkeyForm() + " ,tipodato='" + this.selected.getTipoDato() + "',esPkey='" + this.selected.getEsPkey() + "' where IDCampoFormulario=" + this.selected.getIDCampo();

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

  public void update()
  {
    initLista();
  }

  private void initLista()
  {
    this.listaParametros = new ArrayList();
    PoolConectDB pc = new PoolConectDB();
    ResultSet rs = null;
    Statement st = null;
    try
    {
      st = pc.getConnection().createStatement();

      rs = st.executeQuery("select idcampoformulario,namecampo,fkeytipodato,fkeyformulario, esobligatorio, tipodato,EsPkey from campoformulario where fkeyformulario=" + this.fkeyFormulario);

      while (rs.next()) {
        ParametroBean para = new ParametroBean();
        para.setIDCampo(rs.getInt("idcampoformulario"));
        para.setNameCampo(rs.getString("namecampo"));
        para.setFkeyForm(rs.getInt("fkeyformulario"));
        para.setFkeyTipo(rs.getInt("fkeytipodato"));
        para.setEsObligatorio(rs.getString("esobligatorio"));
        para.setTipoDato(rs.getString("tipodato"));
        para.setEsPkey(rs.getString("EsPkey"));

        this.listaParametros.add(para);
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

  public void deleteForm()
  {
    PoolConectDB pc = new PoolConectDB();
    ResultSet rs = null;
    Statement st = null;
    try {
      st = pc.getConnection().createStatement();
      String sql = "delete from campoformulario where IDCampoFormulario=" + this.selected.getIDCampo();
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