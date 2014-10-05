<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:p="http://primefaces.org/ui">

<h:head>
	<h:outputStylesheet library="css" name="style.css"/>
</h:head>
<f:view contentType="text/html">
	<h:outputStylesheet library="css" name="style.css"/>
<f:facet name="form"><meta content='text/html; charset=UTF-8' http-equiv="Content-Type"/>
	<title>cap_prueba</title>
</f:facet>
</h:head>
<h:body>

<h:form id="form">
<p:panelGrid id="grd" columns="11" style="font-size: 10px;" rendered="true">
<f:facet name="header">
	Nuevo cap_prueba
</f:facet>*******************************

		PARAMETROS

*******************************

<h:outputLabel for="holaInput" value="hola :" />
<h:inputTextid="holaInput" value="#{cap_pruebaModel.hola}" required="true"/>




<h:outputLabel for="pruebaInput" value="prueba :" />
<h:inputTextid="pruebaInput" value="#{cap_pruebaModel.prueba}" required="true"/>


<p:commandButton ajax="false" update="dataTable" value="Nuevo"><f:actionListener binding="#{cap_pruebaModel.insert()}"/>
<f:actionListener binding="#{cap_pruebaController.update()}"/>
</p:commandButton>
</p:panelGrid></h:form>
</h:form>
</h:body>
</html>