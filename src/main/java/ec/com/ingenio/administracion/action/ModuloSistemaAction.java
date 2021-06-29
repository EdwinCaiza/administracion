/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.action;

import ec.com.ingenio.administracion.bean.ModuloSistemaBean;
import ec.com.ingenio.administracion.util.UtilBean;
import ec.com.ingenio.ingenioconta.modelo.SmosModulosistema;
import ec.com.ingenio.ingenioconta.remoto.SmosModulosistemaFacadeRemoto;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author hacker
 */
@ViewScoped
@Stateful
@Named
public class ModuloSistemaAction {

    /**
     * DECLARACIÓN DE VARIABLES *
     */
    @EJB(lookup = "java:global/IngenioConta/SmosModulosistemaFacade!ec.com.ingenio.ingenioconta.remoto.SmosModulosistemaFacadeRemoto")
    private SmosModulosistemaFacadeRemoto moduloSistemaFacade;
    @Inject
    ModuloSistemaBean moduloSistemaBean;
    @Inject
    UtilBean utilBean;
// <editor-fold defaultstate="collapsed" desc="Métodos Principales">

    /**
     * Método para cargar variables iniciales
     */
    public void init() {
        cargarListaModulos();
    }

    /**
     * Método para Borrado Logico de entidad seleccionada
     *
     * @param modulo
     */
    public void borrarItem(SmosModulosistema modulo) {
        try {
            if (moduloSistemaFacade.borradoLogico(modulo)) {
                utilBean.mostrarMensaje(false, 2, "Exito", "Información borrada con exito");
            } else {
                utilBean.mostrarMensaje(false, 1, "Advertenciua", "No se puede borrar registro");
            }
            moduloSistemaBean.setModuloActual(new SmosModulosistema());
            init();
        } catch (Exception e) {
            System.err.println("Error al procesar borrarItem de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para cancelar Edición / Nuevo
     */
    public void cancelar() {
        moduloSistemaBean.setModuloActual(new SmosModulosistema());
        moduloSistemaBean.setVerDialog(false);
    }

    /**
     * Método para cargar lista de Modulos Sistema
     */
    public void cargarListaModulos() {
        try {
            moduloSistemaBean.setListaModulos(moduloSistemaFacade.listarModulos());
        } catch (Exception e) {
            System.err.println("Error al procesar cargarListaModulos de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para crear nuevo Modulo Sistema
     */
    public void crearNuevo() {
        moduloSistemaBean.setModuloActual(new SmosModulosistema());
        moduloSistemaBean.setVerDialog(true);
    }

    /**
     * Método para Editar entidad seleccionada
     *
     * @param modulo
     */
    public void editarItem(SmosModulosistema modulo) {
        try {

            moduloSistemaBean.setModuloActual(modulo);
            moduloSistemaBean.setVerDialog(true);
        } catch (Exception e) {
            System.err.println("Error al procesar editarItem de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para Guardar datos de entidad
     *
     * @param modulo
     */
    public void guardarDatos() {
        try {
            if (moduloSistemaBean.getModuloActual().getMosCodigo() == null) {
                moduloSistemaBean.getModuloActual().setMosBorradologico(true);
                moduloSistemaBean.getModuloActual().setMosEstado("A");
                moduloSistemaFacade.create(moduloSistemaBean.getModuloActual());
            } else {
                moduloSistemaFacade.edit(moduloSistemaBean.getModuloActual());
            }
            moduloSistemaBean.setModuloActual(new SmosModulosistema());
            moduloSistemaBean.setVerDialog(false);
            init();
        } catch (Exception e) {
            System.err.println("Error al procesar guardarDatos de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    //</editor-fold> 
}
