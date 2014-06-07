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
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.SelectableDataModel;
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
    private Contato selecionado;
    private Email emailNovo;
    private Telefone telefoneNovo;
    private List<Email> emailsSelecionados;
    private List<Telefone> telefonesSelecionados;

    public List<Email> getEmailsSelecionados() {
        return emailsSelecionados;
    }

    public void setEmailsSelecionados(List<Email> emailsSelecionados) {
        this.emailsSelecionados = emailsSelecionados;
    }

    public List<Telefone> getTelefonesSelecionados() {
        return telefonesSelecionados;
    }

    public void setTelefonesSelecionados(List<Telefone> telefonesSelecionados) {
        this.telefonesSelecionados = telefonesSelecionados;
    }

    public Email getEmailNovo() {
        return emailNovo;
    }

    public void setEmailNovo(Email emailNovo) {
        this.emailNovo = emailNovo;
    }

    public Telefone getTelefoneNovo() {
        return telefoneNovo;
    }

    public void setTelefoneNovo(Telefone telefoneNovo) {
        this.telefoneNovo = telefoneNovo;
    }

    public List<Contato> getListaContatos() {
        return listaContatos;
    }

    public void setListaContatos(List<Contato> listaContatos) {
        this.listaContatos = listaContatos;
    }

    public Contato getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Contato selecionado) {
        this.selecionado = selecionado;
    }

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
        Usuario u = Negociao.recuperaUsuarioPorEmail(Negociao.emailLogado());
        listaContatos = Negociao.listaContatos(u.getPin());
        raizContatos = new DefaultTreeNode("Contatos", null);
        raizContatos.setExpanded(true);
        for (Contato contato : getContatos()) {
            TreeNode noNome = new DefaultTreeNode(contato, raizContatos);
            noNome.setExpanded(true);
            if (contato.getTelefones() != null && !contato.getTelefones().isEmpty()) {
                TreeNode raizTelefones = new DefaultTreeNode("telefones", noNome);
                for (Telefone tel : contato.getTelefones()) {
                    TreeNode noTel = new DefaultTreeNode(tel, raizTelefones);
                }
            }
            if (contato.getEmails() != null && !contato.getEmails().isEmpty()) {
                TreeNode raizEmail = new DefaultTreeNode("emails", noNome);
                for (Email email : contato.getEmails()) {
                    TreeNode noEmail = new DefaultTreeNode(email, raizEmail);
                }
            }

        }
    }

    public void excluir(Object obj) {
        Negociao.excluirContatoEMailTelefone(obj);
        //Negociao.commitaStarta();
        criaRaizContatos();

    }

    public void editar(Contato contato) {
        emailsSelecionados = new ArrayList<Email>();
        telefonesSelecionados = new ArrayList<Telefone>();
        selecionado = Negociao.recuperaContato(contato.getId());
        telefoneNovo = new Telefone();
        emailNovo = new Email();

    }

    public void salvar() {
        Negociao.salvaContato(selecionado);
        criaRaizContatos();
    }

    public void cancelar() {

    }

    public void salvaTelefoneNovo() {
        if (telefoneNovo.getNumero() == null || telefoneNovo.getNumero().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("mensagens", new FacesMessage(FacesMessage.SEVERITY_INFO, "Falta telefone", "Falta o número do telefone"));
            return;
        }
        for (Telefone t : selecionado.getTelefones()) {
            if (t.getNumero().equals(telefoneNovo.getNumero())) {
                FacesContext.getCurrentInstance().addMessage("mensagens", new FacesMessage(FacesMessage.SEVERITY_INFO, "Telefone duplicado", "Este telefone não é único"));
                return;
            }
        }
        telefoneNovo.setContato(selecionado);
        selecionado.getTelefones().add(telefoneNovo);
        telefoneNovo = new Telefone();

    }

    public void salvaEmailNovo() {
        if (emailNovo.getEndereco() == null || emailNovo.getEndereco().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("mensagens", new FacesMessage(FacesMessage.SEVERITY_INFO, "Falta email", "Falta o endereço de email"));
            return;
        }
        for (Email e : selecionado.getEmails()) {
            if (e.getEndereco().equals(emailNovo.getEndereco())) {
                FacesContext.getCurrentInstance().addMessage("mensagens", new FacesMessage(FacesMessage.SEVERITY_INFO, "Email duplicado", "Este email não é único"));
                return;
            }
        }
        emailNovo.setContato(selecionado);
        selecionado.getEmails().add(emailNovo);
        emailNovo = new Email();

    }

    public boolean eString(Object obj) {
        return obj instanceof String;
    }

    public boolean eContato(Object obj) {
        return obj instanceof Contato;
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Car Selected", "ok");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Car Unselected", "ok");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void excluirTelefones() {
        System.out.println("TS:" + telefonesSelecionados);
        for (Telefone t:telefonesSelecionados){
            selecionado.getTelefones().remove(t);
        }
    }

}
