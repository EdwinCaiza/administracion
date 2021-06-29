/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.bean;

import ec.com.ingenio.ingenioconta.modelo.SmeiMenuitems;
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
public class MenuItemsBean {

    /**
     * DECLARACIÓN DE VARIABLES
     */
    private boolean verDialog = false;
    private boolean verModulo = true;
    private SmeiMenuitems menuItemActual = new SmeiMenuitems();
    private SmeiMenuitems menuItemAux = new SmeiMenuitems();
    private List<SmeiMenuitems> listaMenuItem = new ArrayList<SmeiMenuitems>();
    private List<SmeiMenuitems> listaMenuItemPadre = new ArrayList<SmeiMenuitems>();
    private List<SmeiMenuitems> listaMenuItemHijo = new ArrayList<SmeiMenuitems>();
    private TreeNode root;
    private TreeNode nodoSeleccionado;
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
     * @return the menuItemActual
     */
    public SmeiMenuitems getMenuItemActual() {
        return menuItemActual;
    }

    /**
     * @param menuItemActual the menuItemActual to set
     */
    public void setMenuItemActual(SmeiMenuitems menuItemActual) {
        this.menuItemActual = menuItemActual;
    }

    /**
     * @return the listaMenuItem
     */
    public List<SmeiMenuitems> getListaMenuItem() {
        return listaMenuItem;
    }

    /**
     * @param listaMenuItem the listaMenuItem to set
     */
    public void setListaMenuItem(List<SmeiMenuitems> listaMenuItem) {
        this.listaMenuItem = listaMenuItem;
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
     * @return the listaMenuItemPadre
     */
    public List<SmeiMenuitems> getListaMenuItemPadre() {
        return listaMenuItemPadre;
    }

    /**
     * @param listaMenuItemPadre the listaMenuItemPadre to set
     */
    public void setListaMenuItemPadre(List<SmeiMenuitems> listaMenuItemPadre) {
        this.listaMenuItemPadre = listaMenuItemPadre;
    }

    /**
     * @return the listaMenuItemHijo
     */
    public List<SmeiMenuitems> getListaMenuItemHijo() {
        return listaMenuItemHijo;
    }

    /**
     * @param listaMenuItemHijo the listaMenuItemHijo to set
     */
    public void setListaMenuItemHijo(List<SmeiMenuitems> listaMenuItemHijo) {
        this.listaMenuItemHijo = listaMenuItemHijo;
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
     * @return the menuItemAux
     */
    public SmeiMenuitems getMenuItemAux() {
        return menuItemAux;
    }

    /**
     * @param menuItemAux the menuItemAux to set
     */
    public void setMenuItemAux(SmeiMenuitems menuItemAux) {
        this.menuItemAux = menuItemAux;
    }

    /**
     * @return the verModulo
     */
    public boolean isVerModulo() {
        return verModulo;
    }

    /**
     * @param verModulo the verModulo to set
     */
    public void setVerModulo(boolean verModulo) {
        this.verModulo = verModulo;
    }
//</editor-fold> 

}
