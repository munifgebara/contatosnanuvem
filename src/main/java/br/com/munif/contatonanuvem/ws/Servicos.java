/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.contatonanuvem.ws;

import br.com.munif.contatonanuvem.negocios.Negociao;
import com.google.gson.Gson;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author munifgebarajunior
 */
@Path(value = "/contatos")
public class Servicos {

    @GET
    @Path("/echo/{param}")
    public Response getMsg(@PathParam("param") String msg) {
        System.out.println("Execuntando o método echo " + msg);
        String output = "Jersey say : " + msg;
        return Response.status(200).entity(output).build();
    }

    @GET
    @Produces("application/json")
    @Path("/lista")
    public String lista() {
        System.out.println("Execuntando o método lista ");
        Gson gson = new Gson();
        return gson.toJson(Negociao.listaContatos("123"));
    }

}
