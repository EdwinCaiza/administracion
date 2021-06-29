/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.action;

import ec.com.ingenio.administracion.bean.CargosBean;
import ec.com.ingenio.administracion.util.UtilBean;
import ec.com.ingenio.ingenioconta.modelo.GcarCargos;
import ec.com.ingenio.ingenioconta.remoto.GcarCargosFacadeRemoto;
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
public class CagosAction {

    /**
     * Declaración de Variables
     */
    @EJB(lookup = "java:global/IngenioConta/GcarCargosFacade!ec.com.ingenio.ingenioconta.remoto.GcarCargosFacadeRemoto")
    private GcarCargosFacadeRemoto cargosRemoto;
    @Inject
    CargosBean cargoBean;
    @Inject
    UtilBean utilBean;

// <editor-fold defaultstate="collapsed" desc="Métodos Principales">
    /**
     * Método para inicializar variables requeridas
     */
    public void init() {
        try {
            listarCargos();
        } catch (Exception e) {
            System.err.println("Erro al procesar init de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para borrado lógico de datos
     *
     * @param cargo
     */
    public void borrarDatos(GcarCargos cargo) {
        try {
            if (cargo.getCarCodigo() != null) {
                if (cargosRemoto.borradoLogico(cargo)) {
                    utilBean.mostrarMensaje(false, 2, "Exito", "Información borrada con éxito");
                } else {
                    utilBean.mostrarMensaje(false, 1, "Advertencia", "No se puede borrar cargo");
                }

            } else {
                utilBean.mostrarMensaje(false, 1, "Advertencia", "Seleccione cargo a borrar");
            }
            init();
        } catch (Exception e) {
            System.err.println("Erro al procesar borrarDatos de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para cancelar Accion
     */
    public void cancelar() {
        cargoBean.setCargoActual(new GcarCargos());
        cargoBean.setVerDialog(false);
        cargoBean.setVerEstado(false);
        init();
    }

    /**
     * Método para crear nuevo cargo
     */
    public void crearNuevo() {
        try {
            cargoBean.setCargoActual(new GcarCargos());
            cargoBean.setVerDialog(true);
            cargoBean.setVerEstado(false);
        } catch (Exception e) {
            System.err.println("Erro al procesar crearNuevo de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para Guardar Información
     */
    public void guardarDatos() {
        try {
            if (cargoBean.getCargoActual().getCarCodigo() == null) {
                cargoBean.getCargoActual().setCarEstado("A");
                cargoBean.getCargoActual().setCarBorradologico(true);
                cargosRemoto.create(cargoBean.getCargoActual());
                utilBean.mostrarMensaje(false, 2, "Exito", "Información almacenada correctamente");
            } else {
                cargosRemoto.edit(cargoBean.getCargoActual());
                utilBean.mostrarMensaje(false, 2, "Exito", "Información modificada correctamente");
            }
            cargoBean.setCargoActual(new GcarCargos());
            cargoBean.setActivaGuardar(true);
            cargoBean.setVerDialog(false);
            cargoBean.setVerEstado(false);
            init();
        } catch (Exception e) {
            System.err.println("Erro al procesar guardarDatos de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para listar cargos
     */
    public void listarCargos() {
        try {
            cargoBean.setListaCargos(cargosRemoto.listarCargos());
        } catch (Exception e) {
            System.err.println("Erro al procesar listarCargos de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para cargar seleccionar Cargo
     */
    public void selecionaCargo() {
        try {
            cargoBean.setVerDialog(true);
            cargoBean.setVerEstado(true);
        } catch (Exception e) {
            System.err.println("Error al procesar selecionaCargo de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }

    }

    /**
     * Método para listar cargos
     */
    public void validaExistencia() {
        try {
            if (cargoBean.getCargoActual().getCarDescripcion() == null) {
                utilBean.mostrarMensaje(false, 1, "Advertencia", "Ingrese descripción");
                cargoBean.setActivaGuardar(true);
            } else if (cargosRemoto.validaExistencia(cargoBean.getCargoActual().getCarDescripcion())) {
                utilBean.mostrarMensaje(false, 1, "Advertencia", "Cargo ya registrado");
                cargoBean.setCargoActual(new GcarCargos());
                cargoBean.setActivaGuardar(true);
            } else {
                cargoBean.setActivaGuardar(false);
            }
        } catch (Exception e) {
            System.err.println("Erro al procesar validaExistencia de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

//</editor-fold> 
}
