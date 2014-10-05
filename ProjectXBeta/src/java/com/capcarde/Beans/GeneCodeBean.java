package com.capcarde.Beans;

import com.capcarde.logic.CRUDParameObject;
import com.capcarde.logic.CreateForms;
import com.capcarde.logic.ParserSQL;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean(name="geneCode")
public class GeneCodeBean
{
  private String result;
  private ArrayList<ParametroBean> lista;
  private String nombEnti = "";
  private String abrev = "";
  private String nombForm = "";

  public String getAbrev() {
    return this.abrev;
  }

  public void setAbrev(String abrev) {
    this.abrev = abrev;
  }

  public String getNombForm() {
    return this.nombForm;
  }

  public void setNombForm(String nombForm) {
    this.nombForm = nombForm;
  }

  public ArrayList<ParametroBean> getLista() {
    return this.lista;
  }

  public void setLista(ArrayList<ParametroBean> lista) {
    this.lista = lista;
  }

  public String getNombEnti() {
    return this.nombEnti;
  }

  public void crearNombEnti() {
    this.nombEnti = (this.abrev + "_" + this.nombForm);
  }

  public void crearLista()
  {
  }

  public void setNombEnti(String nombEnti) {
    this.nombEnti = nombEnti;
  }

  public void geneForm() {
    ArrayList crud = new ArrayList();

    for (ParametroBean p : this.lista) {
      boolean esNulo = true;
      boolean esPkey = false;
      if (p.getEsPkey().equals("S")) {
        esPkey = true;
      }
      if (p.getEsObligatorio().equals("S")) {
        esNulo = false;
      }
      CRUDParameObject c = new CRUDParameObject(p.getNameCampo(), p.getTipoDato(), esNulo, esPkey);
      crud.add(c);
    }

    CreateForms form = new CreateForms();
    setResult(form.createForm(crud, this.nombEnti));
  }

  public void geneSQL() {
    ArrayList crud = new ArrayList();
    for (ParametroBean p : this.lista) {
      boolean esNulo = true;
      boolean esPkey = false;
      if (p.getEsPkey().equals("S")) {
        esPkey = true;
      }
      if (p.getEsObligatorio().equals("S")) {
        esNulo = false;
      }
      CRUDParameObject c = new CRUDParameObject(p.getNameCampo(), p.getTipoDato(), esNulo, esPkey);
      crud.add(c);
    }

    ParserSQL psql = new ParserSQL();
    setResult(psql.templateSQL(crud, this.nombEnti));

    createFile(psql.templateSQL(crud, this.nombEnti), "SQLScript.txt");
  }

  public void geneDto() {
    ArrayList crud = new ArrayList();
    for (ParametroBean p : this.lista) {
      boolean esNulo = true;
      boolean esPkey = false;
      if (p.getEsPkey().equals("S")) {
        esPkey = true;
      }
      if (p.getEsObligatorio().equals("S")) {
        esNulo = false;
      }
      CRUDParameObject c = new CRUDParameObject(p.getNameCampo(), p.getTipoDato(), esNulo, esPkey);
      crud.add(c);
    }
    ParserSQL psql = new ParserSQL();
    setResult(psql.generateDto(crud, this.nombEnti));
  }

  public void geneDao() {
    ArrayList crud = new ArrayList();
    for (ParametroBean p : this.lista) {
      boolean esNulo = true;
      boolean esPkey = false;
      if (p.getEsPkey().equals("S")) {
        esPkey = true;
      }
      if (p.getEsObligatorio().equals("S")) {
        esNulo = false;
      }
      CRUDParameObject c = new CRUDParameObject(p.getNameCampo(), p.getTipoDato(), esNulo, esPkey);
      crud.add(c);
    }
    ParserSQL psql = new ParserSQL();
    setResult(psql.generateDao(crud, this.nombEnti));
  }

  public String getResult()
  {
    return this.result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public void generateAll()
  {
    ArrayList crud = new ArrayList();
    for (ParametroBean p : this.lista) {
      boolean esNulo = true;
      boolean esPkey = false;
      if (p.getEsPkey().equals("S")) {
        esPkey = true;
      }
      if (p.getEsObligatorio().equals("S")) {
        esNulo = false;
      }
      CRUDParameObject c = new CRUDParameObject(p.getNameCampo(), p.getTipoDato(), esNulo, esPkey);
      crud.add(c);
    }
    ParserSQL psql = new ParserSQL();
    String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "/" + this.nombEnti;

    File f = new File(realPath);
    if (!f.exists()) {
      f.mkdir();
    }

    createFile(psql.templateSQL(crud, this.nombEnti), "SQLScript.txt");

    createFile(psql.generateDto(crud, this.nombEnti), this.nombEnti + "Dto.java");

    createFile(psql.generateDao(crud, this.nombEnti), this.nombEnti + "Dao.java");

    CreateForms form = new CreateForms();
    createFile(form.createForm(crud, this.nombEnti), this.nombEnti + "Form.java");
    try {
      zipFolder(realPath, FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "/com.zip");
    } catch (Exception ex) {
      Logger.getLogger(GeneCodeBean.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void createFile(String content, String nombFile) {
    BufferedWriter writer = null;
    try
    {
      String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "/" + this.nombEnti + "/" + nombFile;
      File file = new File(realPath);

      FileWriter f = new FileWriter(file, false);
      f.write(content);
      f.close();
    }
    catch (IOException e) {
    }
  }

  public static void zipFolder(String srcFolder, String destZipFile) throws Exception {
    ZipOutputStream zip = null;
    FileOutputStream fileWriter = null;

    fileWriter = new FileOutputStream(destZipFile);
    zip = new ZipOutputStream(fileWriter);

    addFolderToZip("", srcFolder, zip);
    zip.flush();
    zip.close();
  }

  private static void addFileToZip(String path, String srcFile, ZipOutputStream zip)
    throws Exception
  {
    File folder = new File(srcFile);
    if (folder.isDirectory()) {
      addFolderToZip(path, srcFile, zip);
    } else {
      byte[] buf = new byte[1024];

      FileInputStream in = new FileInputStream(srcFile);
      zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
      int len;
      while ((len = in.read(buf)) > 0)
        zip.write(buf, 0, len);
    }
  }

  private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip)
    throws Exception
  {
    File folder = new File(srcFolder);

    for (String fileName : folder.list())
      if (path.equals(""))
        addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
      else
        addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
  }
}