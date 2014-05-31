/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.contatonanuvem.controle;

import br.com.munif.contatonanuvem.Contato;
import br.com.munif.contatonanuvem.Usuario;
import br.com.munif.contatonanuvem.negocios.Negociao;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author munifgebarajunior
 */
@ManagedBean
@SessionScoped
public class ClienteControlador {

    private List<Contato> listaContatos;

    public String getUsuario() {
        return Negociao.emailLogado();
    }

    public List<Contato> getContatos() {
        if (listaContatos == null) {
            Usuario u = Negociao.recuperaUsuarioPorEmail(Negociao.emailLogado());
            listaContatos = Negociao.listaContatos(u.getPin());
        }
        return listaContatos;
    }

}
