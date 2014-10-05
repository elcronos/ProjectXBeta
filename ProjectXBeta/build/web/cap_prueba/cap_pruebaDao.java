//------------------------------------------------------------------------------------------------
//--		Generado Automaticamente por MPDF(MetaProgrammingFaces)
//--		Dia: 5	Mes: 40	Año :2014
//--		CLASE DAO: cap_pruebaDao
//------------------------------------------------------------------------------------------------

package com.cp.cap_pruebaForm.Controllers;

//------------------------------------------------------------------------------------------------
//--		Imports
//------------------------------------------------------------------------------------------------

import java.io.*;
import java.util.*;
import Defaults.BaseDao;
import Defaults.Connect;
import java.io.Serializable;
import java.sql.SQLException;
import com.cp.cap_pruebaForm.data.cap_pruebaDto;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.ResultSet;
import java.sql.Statement;

//------------------------------------------------------------------------------------------------


@ManagedBean(name="cap_pruebaController")
@SessionScoped
public class cap_pruebaDao extends BaseDao implements Serializable {
private cap_pruebaDto dto;
private cap_pruebaDto selected;
private void inicializaDao(){
	nameTable = "cap_prueba";
	selected= new cap_pruebaDto();
	dto= new cap_pruebaDto();
}

public cap_pruebaDao() {
	inicializaDao();
	this.setDataConnect(new Connect());
}

//------------------------------------------------------------------------------------------------
//--		FindByPkey
//------------------------------------------------------------------------------------------------

public cap_pruebaDto findByPkey(int hola) throws SQLException {
	cap_pruebaDto retorno=null;
	try{
		String sql= " Select * from cap_prueba Where hola = " + hola;
		openConSelect(sql);
		if (rs.next()) {
			retorno= createDto(rs);
		}
	}catch (Exception ex) {

	}finally{
		closeConnection();
	}
return retorno;
}
//------------------------------------------------------------------------------------------------





public ArrayList<cap_pruebaDto> findAll() throws SQLException{
	ArrayList<cap_pruebaDto> listaDto= new ArrayList<cap_pruebaDto>();
	try {
		Statement select= this.getDataConnect().getConnection().createStatement();
		String sql="Select * from "+getNameTable();
		ResultSet result= select.executeQuery(sqlAdds(sql));
		while(result.next()){
			listaDto.add(createDto(result));
		}
	} catch (SQLException ex) {
	}finally{
		closeConnection();
	}
	return listaDto;
}

public boolean insert(cap_pruebaDto pdto) throws SQLException{
	pdto.sethola(0);
	dto=pdto;
	boolean result=true;
	try{
		cn = getDataConnect().getConnection();
		String sql = " insert into cap_prueba (" +
		"hola," +
		" prueba " +
		") values(?,?) ";

		pst = cn.prepareStatement(sql);
		pst.setInt( 1, dto.gethola());
		pst.setInt( 2, dto.getprueba());
		pst.execute();
	} finally {
		closeConnection();
	}
	return result;
}
public boolean update(cap_pruebaDto pdto) throws SQLException {
	pdto.sethola(getSelected().gethola());
	dto=pdto;
	boolean result=true;
	try{
		cn = getDataConnect().getConnection();
		String sql= " update cap_prueba set " +
		"hola = ?, " +
		"prueba = ? " +
		" Where hola = ?";
		
		pst = cn.prepareStatement(sql);
		pst.setInt( 1, dto.gethola());
		pst.setInt( 2, dto.getprueba());
		pst.setInt( 3, dto.gethola());
		pst.execute();
	} finally {
		closeConnection();
	}
	return result;
}
public boolean delete () throws SQLException {
	dto=getSelected();
	boolean result=true;
	try{
		String sql = "delete cap_prueba Where hola = "+dto.getHola();
		openConExecute(sql);
	} catch(Exception ex) {
		result=false;
	} finally {
		closeConnection();
	}
	return result;
}










//------------------------------------------------------------------------------------------------
//--		CreateDto
//------------------------------------------------------------------------------------------------

private cap_pruebaDto createDto(ResultSet rs) throws SQLException {
	cap_pruebaDto retorno = new cap_pruebaDto();
	retorno.setHola (rs.getInt("hola"));
	retorno.setPrueba (rs.getInt("prueba"));
	return retorno;
}



//------------------------------------------------------------------------------------------------
//--		GET's y SET's
//------------------------------------------------------------------------------------------------

//public String toString(){Agregue el retorno del toString}

//--------------------------------------selected----------------------------------------------

public cap_pruebaDto getSelected(){return selected;}
public void setSelected(cap_pruebaDto selected){this.selected = selected;}

//------------------------------------------------------------------------------------------------
}