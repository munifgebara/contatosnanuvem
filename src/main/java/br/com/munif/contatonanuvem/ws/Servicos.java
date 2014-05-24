/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.contatonanuvem.ws;

import br.com.munif.contatonanuvem.Contato;
import br.com.munif.contatonanuvem.ContatoAndroid;
import br.com.munif.contatonanuvem.Email;
import br.com.munif.contatonanuvem.Telefone;
import br.com.munif.contatonanuvem.Usuario;
import br.com.munif.contatonanuvem.negocios.Negociao;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

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
    @Path("/insere/{param}")
    public Response insere(@PathParam("param")String contatoJson){
        Gson gson = new Gson();
        ContatoAndroid ca = gson.fromJson(contatoJson, ContatoAndroid.class);
        
        Usuario u=Negociao.recuperaUsuario(ca.getPin());
        if (u==null) {
            return Response.status(400).entity("Pin invalido").build();
        }
        converteEInsereContato(u, ca);
        return Response.status(201).entity("OK").build();
    }

    public void converteEInsereContato(Usuario u, ContatoAndroid ca) {
        Contato c=new Contato();
        c.setUsuario(u);
        c.setNome(ca.getNome());
        Negociao.insere(c);
        if (ca.getTelefones()!=null){
            for (String s:ca.getTelefones()){
                Telefone t=new Telefone();
                t.setContato(c);
                t.setNumero(s);
                Negociao.insere(t);
            }
        }
        if (ca.getEmails()!=null){
            for (String s:ca.getEmails()){
                Email e=new Email();
                e.setContato(c);
                e.setEndereco(s);
                Negociao.insere(e);
            }
        }
    }

    @GET
    @Produces("application/json")
    @Path("/lista")
    public String lista() {
        System.out.println("Execuntando o método lista ");
        Gson gson = new Gson();
        List<ContatoAndroid> aRetornar=new ArrayList<ContatoAndroid>();
        //converter de Contato para ContatoAndrois
        List<Contato> listaContatos = Negociao.listaContatos("123");
        
        for (Contato c:listaContatos){
            ContatoAndroid ca = converteContato(c);
            aRetornar.add(ca);
        }
        return gson.toJson(aRetornar);
    }

    public ContatoAndroid converteContato(Contato c) {
        ContatoAndroid ca=new ContatoAndroid();
        ca.setNome(c.getNome());
        ca.setPin(c.getUsuario().getPin());
        if (c.getTelefones()!=null){
            ca.setTelefones(new String[c.getTelefones().size()]);
            for (int i=0;i<c.getTelefones().size();i++){
                ca.getTelefones()[i]=c.getTelefones().get(i).getNumero();
            }
        }
        if (c.getEmails()!=null){
            ca.setEmails(new String[c.getEmails().size()]);
            for (int i=0;i<c.getEmails().size();i++){
                ca.getEmails()[i]=c.getEmails().get(i).getEndereco();
            }
        }
        return ca;
    }

}
