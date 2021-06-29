/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.action;

import ec.com.ingenio.administracion.bean.ItemPorPerfilBean;
import ec.com.ingenio.administracion.bean.MenuItemsBean;
import ec.com.ingenio.administracion.bean.PerfilBean;
import ec.com.ingenio.administracion.util.UtilBean;
import ec.com.ingenio.ingenioconta.modelo.SitpItemporperfil;
import ec.com.ingenio.ingenioconta.modelo.SmeiMenuitems;
import ec.com.ingenio.ingenioconta.modelo.SpesPerfil;
import ec.com.ingenio.ingenioconta.remoto.SitpItemporperfilFacadeRemoto;
import ec.com.ingenio.ingenioconta.remoto.SmeiMenuitemsFacadeRemoto;
import ec.com.ingenio.ingenioconta.remoto.SpesPerfilFacadeRemoto;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.sf.jasperreports.functions.standard.LogicalFunctions;
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
public class ItemPorPerfilAction {

    /**
     * DECLARACIÓN DE VARIABLES
     */
    @EJB(lookup = "java:global/IngenioConta/SitpItemporperfilFacade!ec.com.ingenio.ingenioconta.remoto.SitpItemporperfilFacadeRemoto")
    private SitpItemporperfilFacadeRemoto itemPorPerfilRemoto;
    @EJB(lookup = "java:global/IngenioConta/SpesPerfilFacade!ec.com.ingenio.ingenioconta.remoto.SpesPerfilFacadeRemoto")
    private SpesPerfilFacadeRemoto perfilFacade;
    @EJB(lookup = "java:global/IngenioConta/SmeiMenuitemsFacade!ec.com.ingenio.ingenioconta.remoto.SmeiMenuitemsFacadeRemoto")
    private SmeiMenuitemsFacadeRemoto menuItemsRemoto;

    @Inject
    ItemPorPerfilBean itemPerfilBean;
    @Inject
    PerfilBean perfilesBean;
    @Inject
    UtilBean utilBean;
    @Inject
    MenuItemsBean menuItemBean;

// <editor-fold defaultstate="collapsed" desc="Métodos principales">
    /**
     * Método para cargar variables iniciales
     */
    public void init() {
        itemPerfilBean.setActivaGuardar(true);
        listrarPerfiles();
        cargarItemPorPerfil();
        listarItemPorPerfilPadre();
        itemPerfilBean.setRoot(new DefaultTreeNode("Root", null));
        adicionarNodos(itemPerfilBean.getListaObjItPefPadre(), itemPerfilBean.getRoot());
    }

    /**
     * Método para Dicionar nodos al TreeNode
     *
     * @param listaItemPerfil
     * @param root
     */
    public void adicionarNodos(List<Object[]> listaItemPerfil, TreeNode root) {
        try {
            if (perfilesBean.getPerfilActual().getPesCodigo() != null) {
                for (Object[] item : listaItemPerfil) {
                    TreeNode node0 = new DefaultTreeNode(item, root);
                    itemPerfilBean.setListaObjItPefHijo(itemPorPerfilRemoto.listarItemPerfilHijo(Integer.valueOf(item[0].toString()), perfilesBean.getPerfilActual().getPesCodigo()));
                    if (itemPorPerfilRemoto.listarItemPerfilHijo(Integer.valueOf(item[0].toString()), perfilesBean.getPerfilActual().getPesCodigo()) != null) {
                        adicionarNodos(itemPerfilBean.getListaObjItPefHijo(), node0);
                    }
                }
            } else {
                for (Object[] item : listaItemPerfil) {
                    TreeNode node0 = new DefaultTreeNode(item, root);
                    itemPerfilBean.setListaObjItPefHijo(itemPorPerfilRemoto.listarItemPerfilHijo(Integer.valueOf(item[1].toString()), Integer.valueOf(item[9].toString())));
                    if (itemPorPerfilRemoto.listarItemPerfilHijo(Integer.valueOf(item[1].toString()), Integer.valueOf(item[9].toString())) != null) {
                        adicionarNodos(itemPerfilBean.getListaObjItPefHijo(), node0);
                    }

                }
            }

        } catch (Exception e) {
            System.err.println("Erro al procesar adicionarNodos de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para conbertir lista Object a Lista entidad MenuItem
     */
    public void aisgnarListaMenuItem() {
        try {
            menuItemBean.getListaMenuItem().clear();
            for (Object[] itemLista : itemPerfilBean.getListaObjMenu()) {
                menuItemBean.setMenuItemActual(menuItemsRemoto.retornaMenuItem(new SmeiMenuitems(Integer.valueOf(itemLista[0].toString()))));
                menuItemBean.getListaMenuItem().add(menuItemBean.getMenuItemActual());
            }
        } catch (Exception e) {
            System.err.println("Error al procesar aisgnarListaMenuItem de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para cancelar Acción
     */
    public void cancelar() {
        itemPerfilBean.setVerDialog(false);
        itemPerfilBean.setActivaGuardar(true);
    }

    /**
     * Método para cargar variables iniciales
     */
    public void cargarItemPorPerfil() {
        try {
            itemPerfilBean.setListaItemPerfil(itemPorPerfilRemoto.listarItemPorPerfil());
        } catch (Exception e) {
            System.err.println("Error al procesar cargarItemPorPerfil de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para crear Nuevo Menu
     *
     */
    public void crearNuevoItem() {
        try {
            itemPerfilBean.setItemPerfilActual(new SitpItemporperfil());
            itemPerfilBean.setListaObjMenu(itemPorPerfilRemoto.listarModuloMenu());
            aisgnarListaMenuItem();
            itemPerfilBean.setVerDialog(true);
            itemPerfilBean.setVerEstado(false);
            itemPerfilBean.setVerPerfil(true);
        } catch (Exception e) {
            System.err.println("Error al procesar crearNuevoItem de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para crear nuevo SubItem
     *
     * @param codItem
     * @param codPerfil
     */
    public void crearNuevoSubItem(String codItem, String codPerfil) {
        try {
            itemPerfilBean.setItemPerfilActual(new SitpItemporperfil());
//            itemPerfilBean.setListaObjMenu(itemPorPerfilRemoto.ListarSubMenuItem(Integer.parseInt(codItem)));
//            aisgnarListaMenuItem();
            menuItemBean.setListaMenuItem(menuItemsRemoto.listarMenuItemsHijo(new SmeiMenuitems(Integer.parseInt(codItem))));
            itemPerfilBean.getItemPerfilActual().setPesCodigo(new SpesPerfil(Integer.parseInt(codPerfil)));
            itemPerfilBean.setVerDialog(true);
            itemPerfilBean.setVerEstado(false);
            itemPerfilBean.setVerPerfil(false);
        } catch (Exception e) {
            System.err.println("Error al procesar crearNuevoSubItem de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para Guardar Entidad
     */
    public void guardaDatos() {
        try {
            if (itemPerfilBean.getItemPerfilActual().getItpCodigo() == null) {
                itemPerfilBean.getItemPerfilActual().setItpBorradologico(true);
                itemPerfilBean.getItemPerfilActual().setItpEstado("A");
                itemPerfilBean.getItemPerfilActual().setItpHabilita(true);
                itemPerfilBean.getItemPerfilActual().setItpOpcnuevo(true);
                itemPerfilBean.getItemPerfilActual().setItpOpcedita(true);
                itemPerfilBean.getItemPerfilActual().setItpOpcelimina(true);
                itemPorPerfilRemoto.create(itemPerfilBean.getItemPerfilActual());
                utilBean.mostrarMensaje(false, 2, "Exito", "Información almacenada Correctamente");
            } else {
                itemPorPerfilRemoto.edit(itemPerfilBean.getItemPerfilActual());
                utilBean.mostrarMensaje(false, 2, "Exito", "Información modificada Correctamente");
            }
            itemPerfilBean.setVerDialog(false);
            perfilesBean.setPerfilActual(new SpesPerfil());
            itemPerfilBean.setItemPerfilActual(new SitpItemporperfil());
            init();
        } catch (Exception e) {
            System.err.println("Error al procesar guardaDatos de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para Guardar Cambios en la tabla
     */
    public void guardarCambiosTabla() {
        try {
            for (TreeNode item : itemPerfilBean.getRoot().getChildren()) {
                Object[] obj = (Object[]) item.getData();
                itemPerfilBean.setItemPerfilActual(itemPorPerfilRemoto.retornaItemPorPerfil(new SitpItemporperfil(Integer.valueOf(obj[0].toString()))));
                itemPerfilBean.getItemPerfilActual().setItpHabilita(Boolean.valueOf(obj[5].toString()));
                itemPerfilBean.getItemPerfilActual().setItpOpcnuevo(Boolean.valueOf(obj[6].toString()));
                itemPerfilBean.getItemPerfilActual().setItpOpcedita(Boolean.valueOf(obj[7].toString()));
                itemPerfilBean.getItemPerfilActual().setItpOpcelimina(Boolean.valueOf(obj[8].toString()));
                itemPorPerfilRemoto.edit(itemPerfilBean.getItemPerfilActual());
                if (item.getChildCount() > 0) {
                    for (TreeNode subItem : item.getChildren()) {
                        Object[] objSubItem = (Object[]) subItem.getData();
                        itemPerfilBean.setItemPerfilActual(itemPorPerfilRemoto.retornaItemPorPerfil(new SitpItemporperfil(Integer.valueOf(objSubItem[0].toString()))));
                        itemPerfilBean.getItemPerfilActual().setItpHabilita(Boolean.valueOf(objSubItem[5].toString()));
                        itemPerfilBean.getItemPerfilActual().setItpOpcnuevo(Boolean.valueOf(objSubItem[6].toString()));
                        itemPerfilBean.getItemPerfilActual().setItpOpcedita(Boolean.valueOf(objSubItem[7].toString()));
                        itemPerfilBean.getItemPerfilActual().setItpOpcelimina(Boolean.valueOf(objSubItem[8].toString()));
                        itemPorPerfilRemoto.edit(itemPerfilBean.getItemPerfilActual());
                    }
                }
            }
            itemPerfilBean.setVerDialog(false);
            perfilesBean.setPerfilActual(new SpesPerfil());
            itemPerfilBean.setItemPerfilActual(new SitpItemporperfil());
            init();
        } catch (Exception e) {
            System.err.println("Error al procesar guardarCambiosTabla de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para Cargar MenuItems Padre
     */
    public void listarItemPorPerfilPadre() {
        try {
            itemPerfilBean.setListaObjItPefPadre(itemPorPerfilRemoto.listarItemPerfilPadre());

        } catch (Exception e) {
            System.err.println("Error al procesar listarItemPorPerfilPadre de" + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para Cargar lista de Perfiles
     */
    public void listrarPerfiles() {
        try {
            perfilesBean.setListaPerfiles(perfilFacade.listarPerfiles());
        } catch (Exception e) {
            System.err.println("Error al procesar listrarPerfiles de" + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para seleccionar MenuItem de TreeTable
     *
     * @param event
     */
    public void seleccionaItem(NodeSelectEvent event) {
        try {
            Object[] obj = (Object[]) itemPerfilBean.getNodoSeleccionado().getData();
            itemPerfilBean.setListaObjMenu(itemPorPerfilRemoto.ListarSubMenuItem(Integer.parseInt(obj[9].toString())));
            if (obj[2] == null) {
                menuItemBean.setListaMenuItem(menuItemsRemoto.listarMenuItemsPadre());
            } else {
                menuItemBean.setListaMenuItem(menuItemsRemoto.listarMenuItemsHijo(new SmeiMenuitems(Integer.parseInt(obj[2].toString()))));
            }
            itemPerfilBean.setItemPerfilActual(itemPorPerfilRemoto.retornaItemPorPerfil(new SitpItemporperfil(Integer.parseInt(obj[0].toString()))));
            itemPerfilBean.setVerDialog(true);
            itemPerfilBean.setVerEstado(true);
            itemPerfilBean.setVerPerfil(true);
        } catch (Exception e) {
            System.err.println("Error al procesar SeleccionaMenu de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para validar existencia de Item
     *
     */
    public void validaExistencia() {
        try {
            if (itemPerfilBean.getItemPerfilActual().getMeiCodigo() != null && itemPerfilBean.getItemPerfilActual().getPesCodigo() != null) {
                if (itemPorPerfilRemoto.validaExistenciaRegistro(itemPerfilBean.getItemPerfilActual().getMeiCodigo(), itemPerfilBean.getItemPerfilActual().getPesCodigo())) {
                    System.out.println("entrando que is existe");
                    itemPerfilBean.setActivaGuardar(true);
                    utilBean.mostrarMensaje(false, 1, "Advertencia", "Registro ya existe");
                } else {

                    itemPerfilBean.setActivaGuardar(false);
                    System.out.println(" no xiste" + itemPerfilBean.isActivaGuardar());
                }
            }

        } catch (Exception e) {
            System.err.println("Error al procesar validaExistencia de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }
//</editor-fold> 
}
