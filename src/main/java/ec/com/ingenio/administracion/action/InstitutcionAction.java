/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.action;

import ec.com.ingenio.administracion.bean.InstitucionBean;
import ec.com.ingenio.administracion.util.UtilBean;
import ec.com.ingenio.ingenioconta.modelo.PinsInstitucion;
import ec.com.ingenio.ingenioconta.remoto.PinsInstitucionFacadeRemoto;
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
public class InstitutcionAction {

    /**
     * DECLARACION DE VARIABLES
     */
    @EJB(lookup = "java:global/IngenioConta/PinsInstitucionFacade!ec.com.ingenio.ingenioconta.remoto.PinsInstitucionFacadeRemoto")
    private PinsInstitucionFacadeRemoto institucionRemoto;
    @Inject
    InstitucionBean institucionBean;
    @Inject
    UtilBean utilBean;

    // <editor-fold defaultstate="collapsed" desc="Métodos Principales">
    /**
     * Metodo para inicializar variables de aranque de pagina
     */
    public void init() {
        listarIntituciones();
    }

    /**
     * Método para borrado lógico de datos
     *
     * @param institucion
     */
    public void borrarDatos(PinsInstitucion institucion) {
        try {
            institucionRemoto.remove(institucion);
            utilBean.mostrarMensaje(false, 2, "Exito", "Información borrada con Éxito");
            init();
        } catch (Exception e) {
            System.err.println("Error al procesar borrarDatos de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para Cancelar Acción
     */
    public void cancelar() {
        institucionBean.setInstitucionActiva(new PinsInstitucion());
        institucionBean.setVerDialog(false);
        init();
    }

    /**
     * Método para Crear nueva Institución
     */
    public void crearNuevo() {
        try {
            institucionBean.setInstitucionActiva(new PinsInstitucion());
            institucionBean.setVerDialog(true);
        } catch (Exception e) {
            System.err.println("Error al procesar crearNuevo de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para Guardar cambios
     */
    public void guardarDatos() {
        try {
            if (institucionBean.getInstitucionActiva().getInsCodigo() == null) {
                institucionBean.getInstitucionActiva().setInsBorradologico(true);
                institucionBean.getInstitucionActiva().setInsEstado("A");
                institucionRemoto.create(institucionBean.getInstitucionActiva());
                utilBean.mostrarMensaje(false, 2, "Exito", "Información almacenada con éxito");
            } else {
                institucionRemoto.edit(institucionBean.getInstitucionActiva());
                utilBean.mostrarMensaje(false, 2, "Exito", "Información modificada con éxito");
            }
            init();
            institucionBean.setInstitucionActiva(new PinsInstitucion());
            institucionBean.setVerDialog(false);
        } catch (Exception e) {
            System.err.println("Error al procesar guardarDatos de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para listar Instituciones Activas
     */
    public void listarIntituciones() {
        try {
            institucionBean.setListaInstitucion(institucionRemoto.listarInstituciones());
        } catch (Exception e) {
            System.err.println("Error al procesar listarIntituciones de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para seleccionar Institución
     */
    public void seleccionaInstituto() {
        try {
            institucionBean.setVerDialog(true);
        } catch (Exception e) {
            System.err.println("Error al procesar seleccionaInstituto de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    //</editor-fold> 
}
