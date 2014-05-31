/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.contatonanuvem.negocios;

import br.com.munif.contatonanuvem.Contato;
import br.com.munif.contatonanuvem.Usuario;
import br.com.munif.web.filtros.TransaFiltro;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author munifgebarajunior
 */
public class Negociao {

    public static List<Contato> listaContatos(String pin) {
        EntityManager em = TransaFiltro.tlem.get();
        Query q = em.createQuery("from Contato c where c.usuario.pin=:p order by c.nome");
        q.setParameter("p", pin);
        return q.getResultList();
    }

    public static Usuario recuperaUsuarioPorPin(String pin) {
        EntityManager em = TransaFiltro.tlem.get();
        Query q = em.createQuery("from Usuario usu where usu.pin=:p");
        q.setParameter("p", pin);
        q.setMaxResults(1);
        List<Usuario> lista = q.getResultList();
        if (lista.isEmpty()) {
            return null;
        }
        return (Usuario) q.getResultList().get(0);
    }

    public static Usuario recuperaUsuarioPorEmail(String email) {
        EntityManager em = TransaFiltro.tlem.get();
        Query q = em.createQuery("from Usuario usu where usu.email=:p");
        q.setParameter("p", email);
        q.setMaxResults(1);
        List<Usuario> lista = q.getResultList();
        if (lista.isEmpty()) {
            return null;
        }
        return (Usuario) q.getResultList().get(0);
    }

    public static void insere(Object o) {
        EntityManager em = TransaFiltro.tlem.get();
        em.persist(o);

    }

    public static String autoCadastro(String email) {
        Usuario usu = recuperaUsuarioPorEmail(email);
        if (usu != null) {
            throw new RuntimeException("Email já cadastrado");
        }
        String pin = geraPin();
        //TRABALHO OU TAREFA ENVIAR EMAIL PARA O CARA COM PIN
        Usuario u=new Usuario();
        u.setEmail(email);
        u.setPin(pin);
        u.setNivel("cliente");
        EntityManager em = TransaFiltro.tlem.get();
        em.persist(u);
        
        
        return pin;
    }

    private static String geraPin() {
        Random random = new Random();
        long numero=0l;
        Usuario u=null;
        do {
            numero = 999999 - random.nextInt(900000);
            u = recuperaUsuarioPorPin("" + numero);
        } while (u!=null);
        return ""+numero;
    }
    
    
    public static String emailLogado(){
        return TransaFiltro.tlus.get();
    }

}
