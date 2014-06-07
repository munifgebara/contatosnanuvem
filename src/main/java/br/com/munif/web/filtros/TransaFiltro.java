/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.web.filtros;

import br.com.munif.contatonanuvem.DadosAuditoria;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Principal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author munifgebarajunior
 */
@WebFilter(filterName = "TransaFiltro", urlPatterns = {"/*"})
public class TransaFiltro implements Filter {

    public static final ThreadLocal<EntityManager> tlem = new ThreadLocal<EntityManager>();
    public static final ThreadLocal<String> tlus = new ThreadLocal<String>();
    public static final ThreadLocal<DadosAuditoria> tlda=new ThreadLocal<DadosAuditoria>();
    

    private EntityManagerFactory emf;

    public TransaFiltro() {
        emf = Persistence.createEntityManagerFactory("criaListaPU");

    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        Principal userPrincipal = ((HttpServletRequest) request).getUserPrincipal();

        String us = userPrincipal != null ? userPrincipal.getName() : "nao logado";
        /*
         String us;
         if (userPrincipal!=null){
         us=userPrincipal.getName();
         }
         else{
         us="nao logado";
         }
         */
        tlus.set(us);
        
        DadosAuditoria da=new DadosAuditoria();
        da.setIp(request.getRemoteAddr());
        da.setUsuario(us);
        tlda.set(da);

        EntityManager em = emf.createEntityManager();
        tlem.set(em);

        try {

            long inicio = System.currentTimeMillis();
            em.getTransaction().begin();
            chain.doFilter(request, response); //Realmente acontece a requisição
            em.getTransaction().commit();
            long tempo = System.currentTimeMillis() - inicio;
            //System.out.println("A requisicao levou " + tempo + "ms");

        } catch (Throwable t) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            t.printStackTrace();
        }
        em.close();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
        emf.close();
    }

}
