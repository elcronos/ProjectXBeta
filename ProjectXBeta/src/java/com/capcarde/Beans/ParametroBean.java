package com.capcarde.Beans;

import com.capcarde.DB.PoolConectDB;
import java.io.Serializable;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

@ManagedBean(name="parametro")
public class ParametroBean
        implements Serializable {

    public ParametroBean() {
        FkeyTipo = 0;
        FkeyForm = 0;
        esObligatorio = "S";
        esPkeyBol = false;
        esPkey = "N";
    }

    public SelectItem[] getCboPara() {
        return cboPara;
    }

    public int getIDCampo() {
        return IDCampo;
    }

    public void setIDCampo(int IDCampo) {
        this.IDCampo = IDCampo;
    }

    public String getNameCampo() {
        return NameCampo;
    }

    public void setNameCampo(String NameCampo) {
        this.NameCampo = NameCampo;
    }

    public int getFkeyTipo() {
        return FkeyTipo;
    }

    public void setFkeyTipo(int FkeyTipo) {
        this.FkeyTipo = FkeyTipo;
    }

    public int getFkeyForm() {
        return FkeyForm;
    }

    public void setFkeyForm(int FkeyForm) {
        this.FkeyForm = FkeyForm;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getEsObligatorio() {
        return esObligatorio;
    }

    public void setEsObligatorio(String esObligatorio) {
        this.esObligatorio = esObligatorio;
    }

    public SelectItem[] getComboTipo() {
        return comboTipo;
    }

    public void setComboTipo(SelectItem comboTipo[]) {
        this.comboTipo = comboTipo;
    }

    public boolean isEsPkeyBol() {
        return esPkeyBol;
    }

    public void setEsPkeyBol(boolean esPkeyBol) {
        this.esPkeyBol = esPkeyBol;
        if (esPkeyBol) {
            esPkey = "S";
        } else {
            esPkey = "N";
        }
    }

    public String getEsPkey() {
        return esPkey;
    }

    public void setEsPkey(String esPkey) {
        this.esPkey = esPkey;
    }

    public void insert() {
        PoolConectDB pc;
        pc = new PoolConectDB();
        ResultSet rs = null;
        Statement st = null;
        try {
            st = pc.getConnection().createStatement();
            String sql = (new StringBuilder()).append("insert into campoformulario values(").append(secuencia()).append(",'").append(NameCampo).append("',").append(FkeyTipo).append(",").append(FkeyForm).append(",'").append(esObligatorio).append("','").append(tipoDato).append("','").append(esPkey).append("')").toString();
            st.execute(sql);

            pc.getConnection().close();
        } catch (SQLException ex) {
            Logger.getLogger(ParametroBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        esPkeyBol = false;

    }

    public int secuencia() {
        int sec = 0;
        try {
            PoolConectDB p = new PoolConectDB();
            Statement s = p.getConnection().createStatement();
            for (ResultSet rs = s.executeQuery("select max(IDCampoFormulario) from campoformulario"); rs.next();) {
                sec = rs.getInt(1);
            }

            p.getConnection().close();
        } catch (SQLException ex) {

        }
        return sec + 1;
    }

    private int IDCampo;
    private String NameCampo;
    private int FkeyTipo;
    private int FkeyForm;
    private String esObligatorio;
    private String tipoDato;
    private SelectItem comboTipo[] = {
        new SelectItem("Number(10)", "NUMERICO"), new SelectItem("VarChar2(80)", "NOMBRE"), new SelectItem("VarChar2(250)", "DESCRIPCION")
    };
    private boolean esPkeyBol;
    private String esPkey;
    private final SelectItem cboPara[] = {
        new SelectItem("S", "SI"), new SelectItem("N", "NO")
    };
}
