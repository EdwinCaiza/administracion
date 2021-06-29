/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.bean;

import ec.com.ingenio.ingenioconta.modelo.SitpItemporperfil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateful;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.TreeNode;

/**
 *
 * @author hacker
 */
@ViewScoped
@Stateful
@Named
public class ItemPorPerfilBean {

    /**
     * DECLARACIÓN DE VARIABLES
     */
    private boolean verDialog = false;
    private boolean verEstado = false;
    private boolean verPerfil = false;
    private boolean activaGuardar = true;
    private SitpItemporperfil itemPerfilActual = new SitpItemporperfil();
    private List<SitpItemporperfil> listaItemPerfil = new ArrayList<SitpItemporperfil>();
    private TreeNode root;
    private TreeNode nodoSeleccionado;
    private List<SitpItemporperfil> listaItemPerfilPadre = new ArrayList<SitpItemporperfil>();
    private List<SitpItemporperfil> listaItemPerfilHijos = new ArrayList<SitpItemporperfil>();
    private List<Object[]> listaObjItPefPadre = new ArrayList<Object[]>();
    private List<Object[]> listaObjItPefHijo = new ArrayList<Object[]>();
    private List<Object[]> listaObjMenu = new ArrayList<Object[]>();
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
     * @return the itemPerfilActual
     */
    public SitpItemporperfil getItemPerfilActual() {
        return itemPerfilActual;
    }

    /**
     * @param itemPerfilActual the itemPerfilActual to set
     */
    public void setItemPerfilActual(SitpItemporperfil itemPerfilActual) {
        this.itemPerfilActual = itemPerfilActual;
    }

    /**
     * @return the listaItemPerfil
     */
    public List<SitpItemporperfil> getListaItemPerfil() {
        return listaItemPerfil;
    }

    /**
     * @param listaItemPerfil the listaItemPerfil to set
     */
    public void setListaItemPerfil(List<SitpItemporperfil> listaItemPerfil) {
        this.listaItemPerfil = listaItemPerfil;
    }

    /**
     * @return the root
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    /**
     * @return the listaItemPerfilPadre
     */
    public List<SitpItemporperfil> getListaItemPerfilPadre() {
        return listaItemPerfilPadre;
    }

    /**
     * @param listaItemPerfilPadre the listaItemPerfilPadre to set
     */
    public void setListaItemPerfilPadre(List<SitpItemporperfil> listaItemPerfilPadre) {
        this.listaItemPerfilPadre = listaItemPerfilPadre;
    }

    /**
     * @return the listaItemPerfilHijos
     */
    public List<SitpItemporperfil> getListaItemPerfilHijos() {
        return listaItemPerfilHijos;
    }

    /**
     * @param listaItemPerfilHijos the listaItemPerfilHijos to set
     */
    public void setListaItemPerfilHijos(List<SitpItemporperfil> listaItemPerfilHijos) {
        this.listaItemPerfilHijos = listaItemPerfilHijos;
    }

    /**
     * @return the listaObjItPefPadre
     */
    public List<Object[]> getListaObjItPefPadre() {
        return listaObjItPefPadre;
    }

    /**
     * @param listaObjItPefPadre the listaObjItPefPadre to set
     */
    public void setListaObjItPefPadre(List<Object[]> listaObjItPefPadre) {
        this.listaObjItPefPadre = listaObjItPefPadre;
    }

    /**
     * @return the listaObjItPefHijo
     */
    public List<Object[]> getListaObjItPefHijo() {
        return listaObjItPefHijo;
    }

    /**
     * @param listaObjItPefHijo the listaObjItPefHijo to set
     */
    public void setListaObjItPefHijo(List<Object[]> listaObjItPefHijo) {
        this.listaObjItPefHijo = listaObjItPefHijo;
    }

    /**
     * @return the nodoSeleccionado
     */
    public TreeNode getNodoSeleccionado() {
        return nodoSeleccionado;
    }

    /**
     * @param nodoSeleccionado the nodoSeleccionado to set
     */
    public void setNodoSeleccionado(TreeNode nodoSeleccionado) {
        this.nodoSeleccionado = nodoSeleccionado;
    }

    /**
     * @return the listaObjMenu
     */
    public List<Object[]> getListaObjMenu() {
        return listaObjMenu;
    }

    /**
     * @param listaObjMenu the listaObjMenu to set
     */
    public void setListaObjMenu(List<Object[]> listaObjMenu) {
        this.listaObjMenu = listaObjMenu;
    }

    /**
     * @return the verEstado
     */
    public boolean isVerEstado() {
        return verEstado;
    }

    /**
     * @param verEstado the verEstado to set
     */
    public void setVerEstado(boolean verEstado) {
        this.verEstado = verEstado;
    }

    /**
     * @return the verPerfil
     */
    public boolean isVerPerfil() {
        return verPerfil;
    }

    /**
     * @param verPerfil the verPerfil to set
     */
    public void setVerPerfil(boolean verPerfil) {
        this.verPerfil = verPerfil;
    }

    /**
     * @return the activaGuardar
     */
    public boolean isActivaGuardar() {
        return activaGuardar;
    }

    /**
     * @param activaGuardar the activaGuardar to set
     */
    public void setActivaGuardar(boolean activaGuardar) {
        this.activaGuardar = activaGuardar;
    }

//</editor-fold> 
}
