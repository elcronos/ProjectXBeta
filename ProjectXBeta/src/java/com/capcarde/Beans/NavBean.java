package com.capcarde.Beans;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Stack;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
@ManagedBean(name="nav")
@SessionScoped
public class NavBean
    implements Serializable
{
    private String page;
    private Stack<String> pilaNav;
    public NavBean()
    {
        page = "welcome";
        pilaNav= new Stack();
    }

    public String getPage()
    {
        return page;
    }

    public void setPage(String page)
    {
        this.page = page;
    }

    public void printPage()
    {
        System.out.println(page);
    }

    public void redirige(String page)
    {
        this.page = page;
        System.out.println("Ver");
    }
    //Metodo para ir a una pagina
    public void goPage(String page){
        //Graba la pagina actual
        pilaNav.push(this.getPage());
        this.setPage(page);
        //Pagina a la que va
        pilaNav.push(this.getPage());            
    }
    
    public void goBack(){
       String v="";
       
       if(!pilaNav.empty()){
           
          if(pilaNav.peek().equals(page)){
              pilaNav.pop();
          } 
          if(!pilaNav.empty()){
            v= pilaNav.pop().toString();
            this.setPage(v);
          }
       }
       System.out.println("Volver:"+v);
    }
    
    
}
