/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.bean;

import ec.com.ingenio.ingenioconta.modelo.SpesPerfil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateful;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author hacker
 */
@ViewScoped
@Stateful
@Named
public class PerfilBean {

    /**
     * DECLARACIÓN DE VARIABLES
     */
    private boolean verDialog = false;
    private SpesPerfil perfilActual = new SpesPerfil();
    private List<SpesPerfil> listaPerfiles = new ArrayList<SpesPerfil>();
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
     * @return the perfilActual
     */
    public SpesPerfil getPerfilActual() {
        return perfilActual;
    }

    /**
     * @param perfilActual the perfilActual to set
     */
    public void setPerfilActual(SpesPerfil perfilActual) {
        this.perfilActual = perfilActual;
    }

    /**
     * @return the listaPerfiles
     */
    public List<SpesPerfil> getListaPerfiles() {
        return listaPerfiles;
    }

    /**
     * @param listaPerfiles the listaPerfiles to set
     */
    public void setListaPerfiles(List<SpesPerfil> listaPerfiles) {
        this.listaPerfiles = listaPerfiles;
    }
    //</editor-fold> 

}
