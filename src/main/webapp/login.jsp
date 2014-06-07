<%-- 
    Document   : newjsp
    Created on : 28/03/2013, 09:06:08
    Author     : Munif
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <style type="text/css">
            body {
                background-color: #fff;
                text-align: center;
                font: 20px Arial, Helvetica, sans-serif;
            }
        </style>
    </head>
    <body>
        <h1 style="margin-top: 100px">Contato na Nuvem</h1>
        <hr style="height: 15px;background-color: #000"/>
        <form method="post" action="j_security_check">
            Usuário:
            <input size=15 name="j_username" value="munif@munif.com.br">
            Senha:
            <input type="password" size=15 name="j_password" value="446720">
            <input type="submit" value=" Ok ">
        </form>
        <hr style="height: 15px;background-color: #000"/>
    </body>
</html>
