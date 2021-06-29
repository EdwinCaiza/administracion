/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.action;

import ec.com.ingenio.administracion.bean.MenuItemsBean;
import ec.com.ingenio.administracion.bean.ModuloSistemaBean;
import ec.com.ingenio.administracion.util.UtilBean;
import ec.com.ingenio.ingenioconta.modelo.SmeiMenuitems;
import ec.com.ingenio.ingenioconta.remoto.SmeiMenuitemsFacadeRemoto;
import ec.com.ingenio.ingenioconta.remoto.SmosModulosistemaFacadeRemoto;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author hacker
 */
@ViewScoped
@Stateful
@Named
public class MenuItemsAction {

    /**
     * DECLARACIÓN DE VARIABLES
     */
    @EJB(lookup = "java:global/IngenioConta/SmeiMenuitemsFacade!ec.com.ingenio.ingenioconta.remoto.SmeiMenuitemsFacadeRemoto")
    private SmeiMenuitemsFacadeRemoto menuItemsRemoto;
    @EJB(lookup = "java:global/IngenioConta/SmosModulosistemaFacade!ec.com.ingenio.ingenioconta.remoto.SmosModulosistemaFacadeRemoto")
    private SmosModulosistemaFacadeRemoto moduloRemoto;
    @Inject
    MenuItemsBean menuItemBean;
    @Inject
    UtilBean utilBean;
    @Inject
    ModuloSistemaBean moduloBean;
// <editor-fold defaultstate="collapsed" desc="Métodos Principales">

    /**
     * Método para cargar datos de Inicio
     */
    public void init() {
        listarMenuItems();
        cargarMenuItemsPadre();
        listarModulos();
        menuItemBean.setRoot(new DefaultTreeNode("Root", null));
        adicionarNodos(menuItemBean.getListaMenuItemPadre(), menuItemBean.getRoot());

    }

    /**
     * Método para Dicionar nodos al TreeNode
     *
     * @param listaMenuItem
     * @param root
     */
    public void adicionarNodos(List<SmeiMenuitems> listaMenuItem, TreeNode root) {

        try {
            for (SmeiMenuitems item : listaMenuItem) {
                TreeNode node0 = new DefaultTreeNode(item, root);
                menuItemBean.setListaMenuItemHijo(menuItemsRemoto.listarMenuItemsHijo(new SmeiMenuitems(node0.getData().hashCode())));
                adicionarNodos(menuItemBean.getListaMenuItemHijo(), node0);
            }
        } catch (Exception e) {
            System.err.println("Erro al procesar adicionarNodos de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para activar dialogo de nuevo menuItem
     *
     * @param menuItem
     */
    public void asignarEntidad(SmeiMenuitems menuItem) {
        menuItemBean.setMenuItemActual(menuItem);
        menuItemBean.setVerDialog(true);
    }

    /**
     * Método para borrai Item
     *
     * @param menuItem
     */
    public void borrarItem(SmeiMenuitems menuItem) {
        try {
            if (menuItemsRemoto.listarMenuItemsHijo(menuItem).size() > 0) {
                utilBean.mostrarMensaje(false, 1, "Advertencia", "Registro tiene dependientes");
            } else {
                if (menuItemsRemoto.borradoLogico(menuItem)) {
                    utilBean.mostrarMensaje(false, 2, "Exito", "Datos Eliminados correctamente");
                } else {
                    utilBean.mostrarMensaje(false, 1, "Advertencia", "Registro NO borrado");
                }
            }
            menuItemBean.setVerDialog(false);
            menuItemBean.setVerModulo(false);
            init();
        } catch (Exception e) {
            System.err.println("Error al procesar borrarItem de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para Cancelar
     */
    public void cancelar() {
        menuItemBean.setVerDialog(false);
        menuItemBean.setVerModulo(false);
    }

    /**
     * Método para Cargar MenuItems Padre
     */
    public void cargarMenuItemsPadre() {
        try {
            menuItemBean.setListaMenuItemPadre(menuItemsRemoto.listarMenuItemsPadre());
        } catch (Exception e) {
            System.err.println("Error al procesar cargarMenuItemsPadre de" + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para Cargar dialogo de Nuevo Item
     */
    public void crearNuevoMenuItem() {
        menuItemBean.setVerDialog(true);
        menuItemBean.setMenuItemActual(new SmeiMenuitems());
        menuItemBean.setVerModulo(true);
    }

    /**
     * Método para Cargar dialogo de Nuevo Item
     *
     * @param menuItem
     */
    public void crearNuenoMenuSubItem(SmeiMenuitems menuItem) {
        menuItemBean.setVerDialog(true);
        menuItemBean.setMenuItemAux(menuItem);
        menuItemBean.setMenuItemActual(new SmeiMenuitems());
        menuItemBean.getMenuItemActual().setSmeMeiCodigo(menuItemBean.getMenuItemAux());
        menuItemBean.getMenuItemActual().setMosCodigo(menuItemBean.getMenuItemAux().getMosCodigo());
        menuItemBean.setVerModulo(false);
    }

    /**
     * Método para Guardar datos
     */
    public void guardarDatos() {
        try {
            if (menuItemBean.getMenuItemActual().getMeiCodigo() == null) {
                menuItemBean.getMenuItemActual().setMeiEstado("A");
                menuItemBean.getMenuItemActual().setMeiBorradologico(true);
                menuItemsRemoto.create(menuItemBean.getMenuItemActual());
                utilBean.mostrarMensaje(false, 2, "Exito", "Datos Almacenador correctamente");
            } else {
                menuItemsRemoto.edit(menuItemBean.getMenuItemActual());
                utilBean.mostrarMensaje(false, 2, "Exito", "Datos Modificados correctamente");

            }
            menuItemBean.setVerDialog(false);
            init();
        } catch (Exception e) {
            System.err.println("Error el procesar guardarDatos de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para activar dialogo de nuevo menuItem
     */
    public void nuevoMenuItem() {
        menuItemBean.setMenuItemActual(new SmeiMenuitems());
        menuItemBean.setVerDialog(true);
    }

    /**
     * Método para cargar lista de MenuItems
     */
    public void listarMenuItems() {
        try {
            menuItemBean.setListaMenuItem(menuItemsRemoto.listarMenuItems());
        } catch (Exception e) {
            System.err.println("Error al procesar listarMenuItems de " + getClass().getName() + ". Caudado por: " + e.getMessage());
        }
    }

    /**
     * Método para cargar lista de Modulo Sistema
     */
    public void listarModulos() {
        try {
            moduloBean.setListaModulos(moduloRemoto.listarModulos());
        } catch (Exception e) {
            System.err.println("Error al procesar listarMenuItems de " + getClass().getName() + ". Caudado por: " + e.getMessage());
        }
    }

    /**
     * Método para seleccionar MenuItem de TreeTable
     *
     * @param event
     */
    public void seleccionaMenu(NodeSelectEvent event) {
        try {
            Integer codigoMenu = event.getTreeNode().getData().hashCode();
            menuItemBean.setMenuItemActual(menuItemsRemoto.retornaMenuItem(new SmeiMenuitems(codigoMenu)));
            menuItemBean.setVerDialog(true);
        } catch (Exception e) {
            System.err.println("Error al procesar SeleccionaMenu de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }
//</editor-fold> 
}
