<%@page import="br.com.munif.contatonanuvem.Email"%>
<%@page import="br.com.munif.contatonanuvem.Telefone"%>
<%@page import="br.com.munif.contatonanuvem.negocios.Negociao"%>
<%@page import="br.com.munif.contatonanuvem.Contato"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Contatos na Nuvem</title>
    </head>
    <body>
        <h1>Contatos na Nuvem</h1>

        <%
            List<Contato> contatos = Negociao.listaContatos("123");

            for (Contato c : contatos) {
                out.println(c.getNome() + "<br/>");
                for (Telefone t : c.getTelefones()) {
                    out.println("---->"+t.getNumero()+ "<br/>");

                }
                for (Email e : c.getEmails()) {
                    out.println("---->"+e.getEndereco()+ "<br/>");
                }
            }

        %>


    </body>
</html>
