package com.capcarde.logic;

import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.capcarde.logic:
//            CRUDParameObject

public class CreateForms
{

    public CreateForms()
    {
        htmlTag = "";
        bodyTag = "";
        formTag = "";
        initClass();
    }

    private void initClass()
    {
        //Etiquetas: @body, @nameForm, @form,@inputForm
        htmlTag = "<html xmlns=\"http://www.w3.org/1999/xhtml\"\n\t"
                + "xmlns:h=\"http://java.sun.com/jsf/html\"\n\t"
                + "xmlns:f=\"http://java.sun.com/jsf/core\"\n\t"
                + "xmlns:p=\"http://primefaces.org/ui\">\n"
                + "\n<h:head>\n\t<h:outputStylesheet library=\"css\" name=\"style.css\"/>\n</h:head>\n@body\n</html>";
                 
        bodyTag= "<f:view contentType=\"text/html\">"
                +"\n\t<h:outputStylesheet library=\"css\" name=\"style.css\"/>\n"
                +"<f:facet name=\"form\">"
                +"<meta content='text/html; charset=UTF-8' http-equiv=\"Content-Type\"/>"
                +"\n\t<title>@nameForm</title>\n</f:facet>\n</h:head>\n<h:body>\n\n@form\n</h:body>";
                
       
        formTag = "<h:form id=\"form\">\n"
                + "<p:panelGrid id=\"grd\" columns=\"11\" style=\"font-size: 10px;\" rendered=\"true\">"
                + "\n<f:facet name=\"header\">\n\tNuevo @nameForm\n</f:facet>@inputForm"
                + "\n</h:form>";
    }
    //Crear un formulario CRUD simple(Vista)
    public String createForm(ArrayList listaParametros, String nombreForm)
    {
        htmlTag=htmlTag.replace("@body",bodyTag);
        htmlTag=htmlTag.replace("@form",formTag);
        htmlTag=htmlTag.replace("@inputForm",makeForm(listaParametros,nombreForm));
        htmlTag= htmlTag.replace("@nameForm", nombreForm);
        //NameForm

       
        //htmlTag.replaceAll("@body", bodyTag).replaceAll("@form", formTag).replaceAll("@form1", table).replaceAll("@panelGrid", panelGrid).replaceAll("@listaVariables", parametros);
        return htmlTag;
    }
    
     private String converVar(String var){
        String resultado="";
        
        resultado=var.replaceAll(" ","").trim().toLowerCase();
        return resultado;
    }
    //@inputForm replace
    private String makeForm(ArrayList listaParametros, String nombreForm){
        String formulario="";
        String parametros = "*******************************\n\n\t\tPARAMETROS\n\n*******************************";
        String tipo="";
        for(Iterator i$ = listaParametros.iterator(); i$.hasNext();)
        {
            CRUDParameObject param = (CRUDParameObject)i$.next();
            String nombreVar = (new StringBuilder()).append(converVar(param.getNombVariable())).toString();
            String required="";
            //Es obligatorio?
            if(param.isIsNull()){
                required="false";
            }else{required="true";}
            
             //Tipo Descripcion(Corregir para todos los tipos de datos)
            if(param.getTipoVariable().equals("VarChar2(250)")){
               tipo="inputTextArea";
            }else{
               tipo="inputText";
            }
            
            parametros = (new StringBuilder()).append(parametros).append("\n\n<h:outputLabel for=\"").append(nombreVar).append("Input").append("\" value=\"").append(param.getNombVariable()).append(" :\" />\n").append("<h:").append(tipo).append("id=\"").append(nombreVar).append("Input").append("\" value=\"").append("#{")
                         .append(nombreForm).append("Model").append(".").append(nombreVar).append("}\" required=\"").append(required).append("\"/>\n").append("\n\n").toString();
            
         }
         
        formulario=parametros;
        formulario+="<p:commandButton ajax=\"false\" update=\"dataTable\" value=\"Nuevo\">"
                    +"<f:actionListener binding=\"#{"+nombreForm+"Model"+".insert()}\"/>\n" 
                    +"<f:actionListener binding=\"#{"+nombreForm+"Controller"+".update()}\"/>\n" 
                    +"</p:commandButton>\n</p:panelGrid></h:form>";
        
        return formulario;
                    
    }
    

    

    private String htmlTag;
    private String bodyTag;
    private String formTag;
    private String formulario;
   
    
}

