/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.munif.contatonanuvem.negocios;

import br.com.munif.contatonanuvem.DadosAuditoria;
import br.com.munif.contatonanuvem.RevisaoAuditoria;
import br.com.munif.web.filtros.TransaFiltro;
import java.util.Date;
import org.hibernate.envers.RevisionListener;

/**
 *
 * @author munifgebarajunior
 */
public class OuvinteAuditoria implements RevisionListener{

    
    public void newRevision(Object o) {
        RevisaoAuditoria ra=(RevisaoAuditoria)o;
        
        DadosAuditoria da=TransaFiltro.tlda.get();
        
        ra.setIp(da.getIp());
        ra.setUsuario(da.getUsuario());
        ra.setQuando(new Date());
        
    }
    
}
