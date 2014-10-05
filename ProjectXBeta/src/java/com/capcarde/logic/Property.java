package com.capcarde.logic;

import java.io.Serializable;

public class Property
    implements Serializable
{

    public Property()
    {
        nulidad = "";
        esPkey = "";
    }

    public String toString()
    {
        return (new StringBuilder()).append("Property [nomVar=").append(nomVar).append(", tipoVar=").append(tipoVar).append("]").toString();
    }

    public String getNulidad()
    {
        if(nulo)
            return "NULL";
        else
            return "NOT NULL";
    }

    public String getEsPkey()
    {
        if(pkey)
            return "SI";
        else
            return "NO";
    }

    public boolean isPkey()
    {
        return pkey;
    }

    public void setPkey(boolean pkey)
    {
        this.pkey = pkey;
    }

    public boolean isNulo()
    {
        return nulo;
    }

    public void setNulo(boolean nulo)
    {
        this.nulo = nulo;
    }

    public String getTamVar()
    {
        return tamVar;
    }

    public void setTamVar(String tamVar)
    {
        this.tamVar = tamVar;
    }

    public String getNomVar()
    {
        return nomVar;
    }

    public void setNomVar(String nomVar)
    {
        this.nomVar = nomVar;
    }

    public String getTipoVar()
    {
        return tipoVar;
    }

    public void setTipoVar(String tipoVar)
    {
        this.tipoVar = tipoVar;
    }

    private String nomVar;
    private String tamVar;
    private boolean nulo;
    private String nulidad;
    private boolean pkey;
    private String esPkey;
    private String tipoVar;
}

