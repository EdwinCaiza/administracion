/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.action;

import ec.com.ingenio.administracion.bean.PerfilBean;
import ec.com.ingenio.administracion.util.UtilBean;
import ec.com.ingenio.ingenioconta.modelo.SpesPerfil;
import ec.com.ingenio.ingenioconta.remoto.SpesPerfilFacadeRemoto;
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
public class PerfilAction {

    /**
     * DECLARACION DE VARIABLES
     */
    @EJB(lookup = "java:global/IngenioConta/SpesPerfilFacade!ec.com.ingenio.ingenioconta.remoto.SpesPerfilFacadeRemoto")
    private SpesPerfilFacadeRemoto perfilFacade;
    @Inject
    PerfilBean perfilBean;
    @Inject
    UtilBean utilBean;

    // <editor-fold defaultstate="collapsed" desc="Métodos principales">
    /**
     * Método para carga Inicial de datos
     */
    public void init() {
        listarPerfiles();
    }

    /**
     * Método para cerrar dialogo
     */
    public void cancelar() {
        perfilBean.setPerfilActual(new SpesPerfil());
        perfilBean.setVerDialog(false);
    }

    /**
     * Método para eliminado lógico de datos
     *
     * @param perfil
     */
    public void borrarDatos(SpesPerfil perfil) {
        try {
            if (perfil.getPesCodigo() != null) {
                //perfilFacade.remove(perfil);
                if (perfilFacade.borradoLogico(perfil)) {
                    utilBean.mostrarMensaje(false, 2, "Exito", "Información eliminada");
                } else {
                    utilBean.mostrarMensaje(true, 1, "Advertencia", "No se elimino registro");
                }

            }
            listarPerfiles();
        } catch (Exception e) {
            System.err.println("Error al procesar borrarDatos de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para guardar datos
     */
    public void guardarDatos() {

        try {
            if (perfilBean.getPerfilActual().getPesCodigo() == null) {
                perfilBean.getPerfilActual().setPesBorradologico(true);
                perfilBean.getPerfilActual().setPesEstado("A");
                perfilBean.getPerfilActual().setPesAdmin("N");
                perfilFacade.create(perfilBean.getPerfilActual());

            } else {
                perfilFacade.edit(perfilBean.getPerfilActual());
            }
            listarPerfiles();
            perfilBean.setVerDialog(false);
            perfilBean.setPerfilActual(new SpesPerfil());
        } catch (Exception e) {
            System.err.println("Error al procesar guardarDatos de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para abrir dialogo de Nuevo Elemento
     */
    public void crearNuevo() {
        perfilBean.setPerfilActual(new SpesPerfil());
        perfilBean.setVerDialog(true);
    }

    /**
     * Método para carga Inicial de variables
     */
    public void listarPerfiles() {
        try {
            perfilBean.setListaPerfiles(perfilFacade.listarPerfiles());
        } catch (Exception e) {
            System.err.println("Error al procesar listarPerfiles de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para asignar item a entidad
     *
     * @param perfil
     */
    public void asignarEntidad(SpesPerfil perfil) {
        perfilBean.setPerfilActual(perfil);
        perfilBean.setVerDialog(true);
    }
    //</editor-fold> 
}
