<%-- 
    Document   : usuarios
    Created on : 31/05/2014, 13:29:15
    Author     : munifgebarajunior
--%>

<%@page import="br.com.munif.contatonanuvem.negocios.Negociao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%=Negociao.listaUsuarios()%>
    </body>
</html>
