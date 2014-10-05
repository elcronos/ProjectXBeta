package com.capcarde.logic;

import java.util.*;

// Referenced classes of package com.capcarde.logic:
//            CRUDParameObject

public class ParserSQL
{

    public ParserSQL()
    {
    }

    public String templateSQL(ArrayList listaParame, String nombTabla)
    {
        String nombPkey = buscarNombPkey(listaParame);
        String createTabla = parameToSQLTable(listaParame, nombTabla);
        String fecha = obtenerFecha();
        String plantilla = "------------------------------------------------------------------------------------------------\n--\t\tGenerado Automaticamente por MPDF(MetaProgrammingFaces)\n--\t\t@fecha\n------------------------------------------------------------------------------------------------\n\n--Drop Table @nameTable;\n\n-- Tabla : @nameTable\n\n@createTable\n\n------------------------------------------------------------------------------------------------\n-- Llave primaria para @nameTable (@namePkey)\n------------------------------------------------------------------------------------------------\n\n\tAlter Table @nameTable ADD Constraint P@namePkey Primary Key (@namePkey);\n\n------------------------------------------------------------------------------------------------\n\n\tCreate Sequence SEC_@nameTable INCREMENT BY 1 START WITH 10 MAXVALUE 1.0E28\n\tMINVALUE 1 NOCYCLE  CACHE 20 NOORDER;\n\n------------------------------------------------------------------------------------------------\n-- TRIGGER para @nameTable (@namePkey)\n------------------------------------------------------------------------------------------------\n\n\tCREATE OR REPLACE TRIGGER TRG_@nameTable_INSERT BEFORE INSERT ON @nameTable\n\tFOR EACH ROW\n\tBEGIN\n\t\tIf :New.@namePkey = 0 Then\n\t\t\tSelect SEC_@nameTable.NextVal Into :New.@namePkey From Dual;\n\t\tEnd If;\n\tEND;";
        plantilla = plantilla.replaceAll("@nameTable", nombTabla);
        plantilla = plantilla.replaceAll("@namePkey", nombPkey);
        plantilla = plantilla.replaceAll("@createTable", createTabla);
        plantilla = plantilla.replaceAll("@fecha", fecha);
        return plantilla;
    }

    private String buscarNombPkey(ArrayList listaParame)
    {
        String nombPkey = "";
        Iterator i$ = listaParame.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            CRUDParameObject para = (CRUDParameObject)i$.next();
            if(para.isIsPkey())
                nombPkey = para.getNombVariable();
        } while(true);
        return nombPkey;
    }

    private String obtenerFecha()
    {
        Calendar calendar = Calendar.getInstance();
        return (new StringBuilder()).append("Dia: ").append(calendar.get(5)).append("\tMes: ").append(calendar.get(3)).append("\tA\361o :").append(calendar.get(1)).toString();
    }

    private String parameToSQLTable(ArrayList listaParame, String nombTabla)
    {
        String sql = (new StringBuilder()).append("\t\tCreate Table ").append(nombTabla).append(" (\n").toString();
        int cont = 1;
        for(Iterator i$ = listaParame.iterator(); i$.hasNext();)
        {
            CRUDParameObject para = (CRUDParameObject)i$.next();
            sql = (new StringBuilder()).append(sql).append("\t\t\t").append(para.getNombVariable().toUpperCase()).append(" ").append(para.getTipoVariable()).toString();
            if(para.isIsNull())
                sql = (new StringBuilder()).append(sql).append(" NULL").toString();
            else
                sql = (new StringBuilder()).append(sql).append(" NOT NULL").toString();
            if(cont < listaParame.size())
                sql = (new StringBuilder()).append(sql).append(",\n").toString();
            else
                sql = (new StringBuilder()).append(sql).append("\n").toString();
            cont++;
        }

        sql = (new StringBuilder()).append(sql).append("\t\t);").toString();
        return sql;
    }

    public String generateDto(ArrayList listaParame, String nombTabla)
    {
        String plantilla = "//------------------------------------------------------------------------------------------------\n//--\t\tGenerado Automaticamente por MPDF(MetaProgrammingFaces)\n//--\t\t@fecha\n//--\t\tCLASE DTO: @nameTableDto\n//------------------------------------------------------------------------------------------------\n\npackage com.cp.@nameTableForm.data;\n\n@imports\n\n@ManagedBean(name=\"@nameTable\")\n@SessionScoped\npublic class @nameTableDto implements Serializable {\n@listaRangos\n\n@constructora\n\n@variables\n\n@allgetset\n}";
        plantilla = plantilla.replaceAll("@fecha", obtenerFecha());
        plantilla = plantilla.replaceAll("@imports", obtenerImports("import javax.faces.bean.ManagedBean;\nimport javax.faces.bean.SessionScoped;\n"));
        plantilla = plantilla.replaceAll("@listaRangos", obtenerListaRangos());
        plantilla = plantilla.replaceAll("@constructora", obtenerConstructora());
        plantilla = plantilla.replaceAll("@variables", obtenerVariables(listaParame));
        plantilla = plantilla.replaceAll("@allgetset", obtenerAllGetySet(listaParame));
        plantilla = plantilla.replaceAll("@nameTable", nombTabla);
        plantilla = plantilla.replaceAll("@namePkey", buscarNombPkey(listaParame));
        return plantilla;
    }

    private String obtenerImports(String addImports)
    {
        String basico = "//------------------------------------------------------------------------------------------------\n//--\t\tImports\n//------------------------------------------------------------------------------------------------\n\nimport java.io.*;\nimport java.util.*;\n@addImports\n//------------------------------------------------------------------------------------------------\n";
        basico = basico.replaceAll("@addImports", addImports);
        return basico;
    }

    private String obtenerListaRangos()
    {
        return "";
    }

    private String inicializaDao(String nombTabla)
    {
        String plantilla = "private @nameTableDto dto;\nprivate @nameTableDto selected;\nprivate void inicializaDao(){\n\tnameTable = \"@nameTable\";\n\tselected= new @nameTableDto();\n\tdto= new @nameTableDto();\n}\n\npublic @nameTableDao() {\n\tinicializaDao();\n\tthis.setDataConnect(new Connect());\n}";
        return plantilla;
    }

    private String obtenerConstructora()
    {
        String constructora = "//------------------------------------------------------------------------------------------------\n//--\t\tConstructora\n//------------------------------------------------------------------------------------------------\n\npublic @nameTableDto(){\n\t@namePkey=0;\n}\n\n//------------------------------------------------------------------------------------------------\n\n";
        return constructora;
    }

    private String obtenerVariables(ArrayList listaParame)
    {
        String variables = "";
        for(Iterator i$ = listaParame.iterator(); i$.hasNext();)
        {
            CRUDParameObject para = (CRUDParameObject)i$.next();
            String tipoVar = convertSQL2Java(para.getTipoVariable());
            variables = (new StringBuilder()).append(variables).append("private ").append(tipoVar).append(" ").append(para.getNombVariable()).append(";\n").toString();
        }

        return variables;
    }

    private String convertSQL2Java(String tipo)
    {
        String java = tipo;
        if(java.toLowerCase().contains("varchar2"))
            java = "String";
        else
        if(java.toLowerCase().contains("number"))
            java = "int";
        return java;
    }

    private String obtenerAllGetySet(ArrayList listaParame)
    {
        String getyset = "//------------------------------------------------------------------------------------------------\n//--\t\tGET's y SET's\n//------------------------------------------------------------------------------------------------\n\n//public String toString(){Agregue el retorno del toString}\n\n";
        for(Iterator i$ = listaParame.iterator(); i$.hasNext();)
        {
            CRUDParameObject para = (CRUDParameObject)i$.next();
            getyset = (new StringBuilder()).append(getyset).append("//--------------------------------------").append(para.getNombVariable()).append("----------------------------------------------\n\n").toString();
            getyset = (new StringBuilder()).append(getyset).append("public ").append(convertSQL2Java(para.getTipoVariable())).append(" get").append(para.getNombVariable()).append("(){return ").append(para.getNombVariable()).append(";}\n").toString();
            getyset = (new StringBuilder()).append(getyset).append("public void set").append(para.getNombVariable()).append("(").append(convertSQL2Java(para.getTipoVariable())).append(" ").append(para.getNombVariable()).append("){this.").append(para.getNombVariable()).append(" = ").append(para.getNombVariable()).append(";}\n").toString();
        }

        getyset = (new StringBuilder()).append(getyset).append("\n//------------------------------------------------------------------------------------------------\n").toString();
        return getyset;
    }

    private String obtenerGetySet(CRUDParameObject para)
    {
        String getyset = "//------------------------------------------------------------------------------------------------\n//--\t\tGET's y SET's\n//------------------------------------------------------------------------------------------------\n\n//public String toString(){Agregue el retorno del toString}\n\n";
        getyset = (new StringBuilder()).append(getyset).append("//--------------------------------------").append(para.getNombVariable()).append("----------------------------------------------\n\n").toString();
        getyset = (new StringBuilder()).append(getyset).append("public ").append(convertSQL2Java(para.getTipoVariable())).append(" get").append(capitalLetra(para.getNombVariable())).append("(){return ").append(para.getNombVariable()).append(";}\n").toString();
        getyset = (new StringBuilder()).append(getyset).append("public void set").append(capitalLetra(para.getNombVariable())).append("(").append(convertSQL2Java(para.getTipoVariable())).append(" ").append(para.getNombVariable()).append("){this.").append(para.getNombVariable()).append(" = ").append(para.getNombVariable()).append(";}\n").toString();
        getyset = (new StringBuilder()).append(getyset).append("\n//------------------------------------------------------------------------------------------------\n").toString();
        return getyset;
    }

    public String generateDao(ArrayList listaParame, String nombTabla)
    {
        CRUDParameObject para = new CRUDParameObject("selected", "@nameTableDto", false, false);
        String plantilla = "//------------------------------------------------------------------------------------------------\n//--\t\tGenerado Automaticamente por MPDF(MetaProgrammingFaces)\n//--\t\t@fecha\n//--\t\tCLASE DAO: @nameTableDao\n//------------------------------------------------------------------------------------------------\n\npackage com.cp.@nameTableForm.Controllers;\n\n@imports\n\n@ManagedBean(name=\"@nameTableController\")\n@SessionScoped\npublic class @nameTableDao extends BaseDao implements Serializable {\n@initDao\n\n@findByPkey\n\n@findFirst\n\n@findAll\n\n@insert\n@update\n@delete\n@condition\n\n@countAll\n\n@countWhere\n\n@maxValue\n\n@minValue\n\n@createDto\n\n@getyset}";
        plantilla = plantilla.replaceAll("@fecha", obtenerFecha());
        plantilla = plantilla.replaceAll("@initDao", inicializaDao(nombTabla));
        plantilla = plantilla.replaceAll("@imports", obtenerImports("import Defaults.BaseDao;\nimport Defaults.Connect;\nimport java.io.Serializable;\nimport java.sql.SQLException;\nimport com.cp.@nameTableForm.data.@nameTableDto;\nimport javax.faces.bean.ManagedBean;\nimport javax.faces.bean.SessionScoped;\nimport java.sql.ResultSet;\nimport java.sql.Statement;\n"));
        plantilla = plantilla.replaceAll("@findFirst", "");
        plantilla = plantilla.replaceAll("@condition", "");
        plantilla = plantilla.replaceAll("@countAll", "");
        plantilla = plantilla.replaceAll("@countWhere", "");
        plantilla = plantilla.replaceAll("@maxValue", "");
        plantilla = plantilla.replaceAll("@minValue", "");
        plantilla = plantilla.replaceAll("@getyset", obtenerGetySet(para));
        plantilla = plantilla.replaceAll("@findAll", findAll());
        plantilla = plantilla.replaceAll("@findByPkey", findByPkey(listaParame, nombTabla));
        plantilla = plantilla.replaceAll("@insert", insert(listaParame));
        plantilla = plantilla.replaceAll("@update", update(listaParame));
        plantilla = plantilla.replaceAll("@delete", delete(listaParame));
        plantilla = plantilla.replaceAll("@createDto", makeDto(listaParame));
        plantilla = plantilla.replaceAll("@nameTable", nombTabla);
        plantilla = plantilla.replaceAll("@namePkey", buscarNombPkey(listaParame));
        return plantilla;
    }

    private String findByPkey(ArrayList listaParame, String nombTabla)
    {
        String nombPkey = obtenerPkey(listaParame);
        String plantilla = (new StringBuilder()).append("//------------------------------------------------------------------------------------------------\n//--\t\tFindByPkey\n//------------------------------------------------------------------------------------------------\n\npublic @nameTableDto findByPkey(int ").append(nombPkey).append(") throws SQLException {\n\t@nameTableDto retorno=null;\n\t").append("try{\n\t\tString sql= \" Select * from ").append("@nameTable Where ").append(nombPkey).append(" = \" + ").append(nombPkey).append(";\n\t\topenConSelect(sql);").append("\n\t\tif (rs.next()) {\n\t\t\tretorno= createDto(rs);\n\t\t}\n\t}catch (Exception ex) {\n\n\t}finally{\n\t\t").append("closeConnection();\n\t}\nreturn retorno;\n}\n").append("//------------------------------------------------------------------------------------------------\n\n").toString();
        plantilla = plantilla.replaceAll("@nameTable", nombTabla);
        return plantilla;
    }

    private String insert(ArrayList listaParame)
    {
        int cont = 1;
        String values = "\n\t\t\") values(";
        String pst = "\n\n\t\tpst = cn.prepareStatement(sql);";
        String plantilla = "public boolean insert(@nameTableDto pdto) throws SQLException{\n\tpdto.set@namePkey(0);\n\tdto=pdto;\n\tboolean result=true;\n\ttry{\n\t\tcn = getDataConnect().getConnection();\n\t\tString sql = \" insert into @nameTable (\" +";
        for(Iterator i$ = listaParame.iterator(); i$.hasNext();)
        {
            CRUDParameObject para = (CRUDParameObject)i$.next();
            if(cont < listaParame.size())
            {
                plantilla = (new StringBuilder()).append(plantilla).append("\n\t\t\"").append(para.getNombVariable()).append(",\" +").toString();
                values = (new StringBuilder()).append(values).append("?,").toString();
            } else
            {
                plantilla = (new StringBuilder()).append(plantilla).append("\n\t\t\" ").append(para.getNombVariable()).append(" \" +").toString();
                values = (new StringBuilder()).append(values).append("?) \";").toString();
            }
            pst = (new StringBuilder()).append(pst).append("\n\t\tpst.set").append(capitalLetra(convertSQL2Java(para.getTipoVariable()))).append("( ").append(cont).append(", dto.get").append(para.getNombVariable()).append("());").toString();
            cont++;
        }

        pst = (new StringBuilder()).append(pst).append("\n\t\tpst.execute();\n\t} finally {\n\t\tcloseConnection();\n\t}\n\treturn result;\n}").toString();
        plantilla = (new StringBuilder()).append(plantilla).append(values).append(pst).toString();
        return plantilla;
    }

    private String update(ArrayList listaParame)
    {
        int cont = 1;
        String nombPkey = obtenerPkey(listaParame);
        String aux = "";
        String aux2 = "";
        String plantilla = "public boolean update(@nameTableDto pdto) throws SQLException {\n\tpdto.set@namePkey(getSelected().get@namePkey());\n\tdto=pdto;\n\tboolean result=true;\n\ttry{\n\t\tcn = getDataConnect().getConnection();\n\t\tString sql= \" update @nameTable set \" +";
        String pst = "\n\t\tpst = cn.prepareStatement(sql);";
        for(Iterator i$ = listaParame.iterator(); i$.hasNext();)
        {
            CRUDParameObject para = (CRUDParameObject)i$.next();
            if(para.isIsPkey())
                aux = (new StringBuilder()).append("\n\t\tpst.set").append(capitalLetra(convertSQL2Java(para.getTipoVariable()))).append("( @auxCont, dto.get").append(para.getNombVariable()).append("());").toString();
            if(cont < listaParame.size())
                plantilla = (new StringBuilder()).append(plantilla).append("\n\t\t\"").append(para.getNombVariable()).append(" = ?, \" +").toString();
            else
                plantilla = (new StringBuilder()).append(plantilla).append("\n\t\t\"").append(para.getNombVariable()).append(" = ? \" +").toString();
            pst = (new StringBuilder()).append(pst).append("\n\t\tpst.set").append(capitalLetra(convertSQL2Java(para.getTipoVariable()))).append("( ").append(cont).append(", dto.get").append(para.getNombVariable()).append("());").toString();
            cont++;
        }

        aux = aux.replace("@auxCont", (new StringBuilder()).append(cont).append("").toString());
        aux2 = (new StringBuilder()).append(aux2).append("\n\t\tpst.execute();\n\t} finally {\n\t\tcloseConnection();\n\t}\n\treturn result;\n}").toString();
        plantilla = (new StringBuilder()).append(plantilla).append("\n\t\t\" Where ").append(nombPkey).append(" = ?\";\n\t\t").append(pst).append(aux).append(aux2).toString();
        return plantilla;
    }

    private String delete(ArrayList listaParame)
    {
        String pkey = obtenerPkey(listaParame);
        String plantilla = (new StringBuilder()).append("public boolean delete () throws SQLException {\n\tdto=getSelected();\n\tboolean result=true;\n\ttry{\n\t\tString sql = \"delete @nameTable Where ").append(pkey).append(" = \"+dto.get").append(capitalLetra(pkey)).append("();\n\t\topenConExecute(sql);\n\t} catch(Exception ex) {\n\t\tresult=false;\n\t} finally {\n\t\tcloseConnection();\n\t}").append("\n\treturn result;\n}").toString();
        return plantilla;
    }

    private String makeDto(ArrayList listaParame)
    {
        String plantilla = "//------------------------------------------------------------------------------------------------\n//--\t\tCreateDto\n//------------------------------------------------------------------------------------------------\n\nprivate @nameTableDto createDto(ResultSet rs) throws SQLException {\n\t@nameTableDto retorno = new @nameTableDto();";
        for(Iterator i$ = listaParame.iterator(); i$.hasNext();)
        {
            CRUDParameObject para = (CRUDParameObject)i$.next();
            plantilla = (new StringBuilder()).append(plantilla).append("\n\tretorno.set").append(capitalLetra(para.getNombVariable())).append(" (rs.get").append(capitalLetra(convertSQL2Java(para.getTipoVariable()))).append("(").append("\"").append(para.getNombVariable()).append("\"));").toString();
        }

        plantilla = (new StringBuilder()).append(plantilla).append("\n\treturn retorno;\n}\n\n").toString();
        return plantilla;
    }

    private String findAll()
    {
        String plantilla = "public ArrayList<@nameTableDto> findAll() throws SQLException{\n\tArrayList<@nameTableDto> listaDto= new ArrayList<@nameTableDto>();\n\ttry {\n\t\tStatement select= this.getDataConnect().getConnection().createStatement();\n\t\tString sql=\"Select * from \"+getNameTable();\n\t\tResultSet result= select.executeQuery(sqlAdds(sql));\n\t\twhile(result.next()){\n\t\t\tlistaDto.add(createDto(result));\n\t\t}\n\t} catch (SQLException ex) {\n\t}finally{\n\t\tcloseConnection();\n\t}\n\treturn listaDto;\n}";
        return plantilla;
    }

    private String obtenerPkey(ArrayList listaParame)
    {
        String nombPkey = "";
        Iterator i$ = listaParame.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            CRUDParameObject para = (CRUDParameObject)i$.next();
            if(!para.isIsPkey())
                continue;
            nombPkey = para.getNombVariable();
            break;
        } while(true);
        return nombPkey;
    }

    private String capitalLetra(String capital)
    {
        capital = (new StringBuilder()).append(capital.substring(0, 1).toUpperCase()).append(capital.substring(1, capital.length()).toLowerCase()).toString();
        return capital;
    }
}

