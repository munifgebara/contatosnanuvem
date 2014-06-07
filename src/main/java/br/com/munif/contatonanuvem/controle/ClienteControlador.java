/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.contatonanuvem.controle;

import br.com.munif.contatonanuvem.Contato;
import br.com.munif.contatonanuvem.Email;
import br.com.munif.contatonanuvem.Telefone;
import br.com.munif.contatonanuvem.Usuario;
import br.com.munif.contatonanuvem.negocios.Negociao;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author munifgebarajunior
 */
@ManagedBean
@SessionScoped
public class ClienteControlador {

    private List<Contato> listaContatos;
    private TreeNode raizContatos;

    public TreeNode getRaizContatos() {
        if (raizContatos == null) {
            criaRaizContatos();
        }
        return raizContatos;
    }

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

    private void criaRaizContatos() {
        raizContatos = new DefaultTreeNode("Contatos", null);
        raizContatos.setExpanded(true);
        for (Contato contato : getContatos()) {
            TreeNode noNome = new DefaultTreeNode(contato, raizContatos);
            noNome.setExpanded(true);
            if (contato.getTelefones() != null && !contato.getTelefones().isEmpty()) {
                TreeNode raizTelefones = new DefaultTreeNode("telefones", noNome);
                raizTelefones.setExpanded(true);
                for (Telefone tel : contato.getTelefones()) {
                    TreeNode noTel = new DefaultTreeNode(tel, raizTelefones);
                }
            }
            if (contato.getEmails() != null && !contato.getEmails().isEmpty()) {
                TreeNode raizEmail = new DefaultTreeNode("emails", noNome);
                raizEmail.setExpanded(true);
                for (Email email : contato.getEmails()) {
                    TreeNode noEmail = new DefaultTreeNode(email, raizEmail);
                }
            }

        }
    }

    private void criaRaizContatos(Object deletado) {
        raizContatos = new DefaultTreeNode("Contatos", null);
        raizContatos.setExpanded(true);
        for (Contato contato : getContatos()) {
            if (contato.equals(deletado)) {
                continue;
            }
            TreeNode noNome = new DefaultTreeNode(contato, raizContatos);
            noNome.setExpanded(true);
            if (contato.getTelefones() != null && !contato.getTelefones().isEmpty()) {
                TreeNode raizTelefones = new DefaultTreeNode("telefones", noNome);
                raizTelefones.setExpanded(true);
                for (Telefone tel : contato.getTelefones()) {
                    if (tel.equals(deletado)) {
                        continue;
                    }
                    TreeNode noTel = new DefaultTreeNode(tel, raizTelefones);
                }
            }
            if (contato.getEmails() != null && !contato.getEmails().isEmpty()) {
                TreeNode raizEmail = new DefaultTreeNode("emails", noNome);
                raizEmail.setExpanded(true);
                for (Email email : contato.getEmails()) {
                    if (email.equals(deletado)) {
                        continue;
                    }
                    TreeNode noEmail = new DefaultTreeNode(email, raizEmail);
                }
            }

        }

    }

    public void excluir(Object obj) {
        Negociao.excluirContatoEMailTelefone(obj);
        Negociao.commitaStarta();
        criaRaizContatos();
        

    }

    public boolean eString(Object obj) {
        return obj instanceof String;
    }

}
