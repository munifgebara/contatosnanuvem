/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.munif.contatonanuvem.negocios;

import br.com.munif.contatonanuvem.Contato;
import br.com.munif.web.filtros.TransaFiltro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author munifgebarajunior
 */
public class Negociao {
    
    public static List<Contato> listaContatos(String pin){
        EntityManager em=TransaFiltro.tlem.get();
        Query q=em.createQuery("from Contato c where c.usuario.pin=:p order by c.nome");
        q.setParameter("p", pin);
        return q.getResultList();
    }
    
    
    
}
