/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.bean;

import ec.com.ingenio.ingenioconta.modelo.SusiUsuariosistema;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateful;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author EC
 */
@ViewScoped
@Stateful
@Named
public class UsuarioBean {

    /**
     * ******DECLARACION DE VARIABLES
     */
    private boolean verDialog = false;
    private boolean habilitaBtnReset = true;
    private String correo = "";
    private SusiUsuariosistema usuarioActual = new SusiUsuariosistema();
    private List<SusiUsuariosistema> listaUsuarios = new ArrayList<SusiUsuariosistema>();

    // <editor-fold defaultstate="collapsed" desc="Métodos Principales">
    //</editor-fold> 
// <editor-fold defaultstate="collapsed" desc="Getter and Setter">
    /**
     * @return the verDialog
     */
    public boolean isVerDialog() {
        return verDialog;
    }

    /**
     * @param verDialog the verDialog to set
     */
    public void setVerDialog(boolean verDialog) {
        this.verDialog = verDialog;
    }

    /**
     * @return the usuarioActual
     */
    public SusiUsuariosistema getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * @param usuarioActual the usuarioActual to set
     */
    public void setUsuarioActual(SusiUsuariosistema usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    /**
     * @return the listaUsuarios
     */
    public List<SusiUsuariosistema> getListaUsuarios() {
        return listaUsuarios;
    }

    /**
     * @param listaUsuarios the listaUsuarios to set
     */
    public void setListaUsuarios(List<SusiUsuariosistema> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the habilitaBtnReset
     */
    public boolean isHabilitaBtnReset() {
        return habilitaBtnReset;
    }

    /**
     * @param habilitaBtnReset the habilitaBtnReset to set
     */
    public void setHabilitaBtnReset(boolean habilitaBtnReset) {
        this.habilitaBtnReset = habilitaBtnReset;
    }
    //</editor-fold> 

}
