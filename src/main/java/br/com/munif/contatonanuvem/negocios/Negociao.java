/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.contatonanuvem.negocios;

import br.com.munif.contatonanuvem.Contato;
import br.com.munif.contatonanuvem.Email;
import br.com.munif.contatonanuvem.Telefone;
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

    public static List<Usuario> listaUsuarios() {
        EntityManager em = TransaFiltro.tlem.get();
        Query q = em.createQuery("from Usuario u order by u.email");
        return q.getResultList();
    }

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
            enviaPinPorEmail(usu);
            throw new RuntimeException("Email já cadastrado, seu pin foi enviado por email");
        }
        String pin = geraPin();
        //TRABALHO OU TAREFA ENVIAR EMAIL PARA O CARA COM PIN
        Usuario u = new Usuario();
        u.setEmail(email);
        u.setPin(pin);
        u.setNivel("cliente");
        EntityManager em = TransaFiltro.tlem.get();
        em.persist(u);
        enviaPinPorEmail(u);
        return pin;
    }

    public static void enviaPinPorEmail(final Usuario usu) {
        new Thread() {
            public void run() {
                SendMail.setAssuntoMail("PIN do ContatoNaNuvem");
                SendMail.setConteudoMail("Olá, bom dia ou boa tarde ou boa noite!\nSeu PIN vale " + usu.getPin() + "\n\n");
                SendMail.setEmailPara(usu.getEmail());
                SendMail.enviar();
            }
        }.start();
        
    }

    private static String geraPin() {
        Random random = new Random();
        long numero = 0l;
        Usuario u = null;
        do {
            numero = 999999 - random.nextInt(900000);
            u = recuperaUsuarioPorPin("" + numero);
        } while (u != null);
        return "" + numero;
    }

    public static String emailLogado() {
        return TransaFiltro.tlus.get();
    }

    public static void excluir(Object obj) {
        EntityManager em = TransaFiltro.tlem.get();
        em.remove(obj);
    }

    public static void excluirContatoEMailTelefone(Object obj) {
        EntityManager em = TransaFiltro.tlem.get();
        if (obj instanceof Contato) {
            Contato c = (Contato) obj;
            c = em.find(Contato.class, c.getId());

            if (c != null) {
                em.remove(c);
            }

        }
        if (obj instanceof Email) {
            Email e = (Email) obj;
            e = em.find(Email.class, e.getId());
            if (e != null) {
                e.getContato().getEmails().remove(e);
                em.remove(e);
            }
        }
        if (obj instanceof Telefone) {
            Telefone t = (Telefone) obj;

            t = em.find(Telefone.class, t.getId());
            if (t != null) {
                t.getContato().getTelefones().remove(t);
                em.remove(t);
            }
        }
    }

    public static Contato recuperaContato(Long id) {
        EntityManager em = TransaFiltro.tlem.get();
        return em.find(Contato.class, id);
    }

    public static void salvaContato(Contato contato) {
        EntityManager em = TransaFiltro.tlem.get();
        System.out.println(contato.getTelefones());

        Contato c = em.find(Contato.class, contato.getId());
        System.out.println("velho" + c.getTelefones());
        System.out.println("novo" + contato.getTelefones());

        em.merge(contato);

        for (Telefone t : contato.getTelefones()) {
            if (t.getId() == null) {
                em.persist(t);
            }
        }
        for (Email e : contato.getEmails()) {
            if (e.getId() == null) {
                em.persist(e);
            }
        }

    }

}
