<%-- 
    Document   : principal
    Created on : 31/05/2014, 08:54:43
    Author     : munifgebarajunior
--%>

<%@page import="br.com.munif.contatonanuvem.negocios.Negociao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Contato Na Nuvem</title>
    </head>
    <body>
        <h1>Contato Na Nuvem</h1>
        <%=Negociao.emailLogado()%><br/>
        
        


        <p>Bem Vindo ao Contato na Nuvem, uma aplicação do pessoal da pós de Paranavaí.</p>


        <h2>Cadastre-se</h2>
        <form action="controlador" method="get">
            <input type="hidden" name="acao" value="autocadastro" />
            Email:<input type="text" name="email" value="" />
            <input type="submit" value="Enviar" />
        </form>
        <h2><a href="faces/cliente/contatos.xhtml">Acesse a seus contatos</a>  </h2>
        
    </body>
</html>
