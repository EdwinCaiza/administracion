/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.bean;

import ec.com.ingenio.ingenioconta.modelo.SmosModulosistema;
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
public class ModuloSistemaBean {

    /**
     * DECLARACIÓN DE VARIABLES *
     */
    private boolean verDialog = false;
    private SmosModulosistema moduloActual = new SmosModulosistema();
    private List<SmosModulosistema> listaModulos = new ArrayList<SmosModulosistema>();

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
     * @return the moduloActual
     */
    public SmosModulosistema getModuloActual() {
        return moduloActual;
    }

    /**
     * @param moduloActual the moduloActual to set
     */
    public void setModuloActual(SmosModulosistema moduloActual) {
        this.moduloActual = moduloActual;
    }

    /**
     * @return the listaModulos
     */
    public List<SmosModulosistema> getListaModulos() {
        return listaModulos;
    }

    /**
     * @param listaModulos the listaModulos to set
     */
    public void setListaModulos(List<SmosModulosistema> listaModulos) {
        this.listaModulos = listaModulos;
    }

//</editor-fold>   
}
