/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.action;

import ec.com.ingenio.administracion.bean.CargoPorPersonaBean;
import ec.com.ingenio.administracion.bean.CargosBean;
import ec.com.ingenio.administracion.bean.InstitucionBean;
import ec.com.ingenio.administracion.util.UtilBean;
import ec.com.ingenio.ingenioconta.modelo.GcapCargopersona;
import ec.com.ingenio.ingenioconta.remoto.GcapCargopersonaFacadeRemoto;
import ec.com.ingenio.ingenioconta.remoto.GcarCargosFacadeRemoto;
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
public class CargoPorPersonaAction {

    /**
     * Declaración de Variables
     */
    @EJB(lookup = "java:global/IngenioConta/GcapCargopersonaFacade!ec.com.ingenio.ingenioconta.remoto.GcapCargopersonaFacadeRemoto")
    private GcapCargopersonaFacadeRemoto cargoPersonaRemoto;
    @EJB(lookup = "java:global/IngenioConta/PinsInstitucionFacade!ec.com.ingenio.ingenioconta.remoto.PinsInstitucionFacadeRemoto")
    private PinsInstitucionFacadeRemoto institucionRemoto;
    @EJB(lookup = "java:global/IngenioConta/GcarCargosFacade!ec.com.ingenio.ingenioconta.remoto.GcarCargosFacadeRemoto")
    private GcarCargosFacadeRemoto cargosRemoto;
    @Inject
    CargoPorPersonaBean cargoPersonaBean;
    @Inject
    InstitucionBean intitucionBean;
    @Inject
    CargosBean cargosBean;
    @Inject
    UtilBean utilBean;
// <editor-fold defaultstate="collapsed" desc="Métodos Principales">

    /**
     * Método para cargar variables iniciales
     */
    public void init() {
        try {
            listarInstituciones();
            listarCargoPersona();
            listarCargos();
        } catch (Exception e) {
            System.err.println("Erro al procesar init de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para cancelar acción
     */
    public void cancelar() {
        cargoPersonaBean.setCargoPersonaActual(new GcapCargopersona());
        cargoPersonaBean.setVerDialog(false);
    }

    /**
     * Método para crear Nuevo Cargo Por persona
     */
    public void crearNuevo() {
        try {
            cargoPersonaBean.setCargoPersonaActual(new GcapCargopersona());
            cargoPersonaBean.setVerDialog(true);
        } catch (Exception e) {
            System.err.println("Error al procesar crearNuevo de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }
/**
     * Método para cargar listado de Cargos 
     *
     */
    public void listarCargos(){
        try {
            cargosBean.setListaCargos(cargosRemoto.listarCargos());
        } catch (Exception e) {
            System.err.println("Error al procesar crearNuevo de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }
    /**
     * Método para cargar listado de Cargos por persona
     *
     */
    public void listarCargoPersona() {
        try {
            cargoPersonaBean.setListaCargoPersona(cargoPersonaRemoto.listarCargosPersona());
        } catch (Exception e) {
            System.err.println("Erro al procesar listarCargoPersona de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }
    /**
     * Método para cargar listado de Instituciones
     *
     */
    public void listarInstituciones(){
        try {
            intitucionBean.setListaInstitucion(institucionRemoto.listarInstituciones());
            System.out.println("********Numero de INst: " + intitucionBean.getListaInstitucion().size());
        } catch (Exception e) {
           System.err.println("Erro al procesar listarInstituciones de " + getClass().getName() + ". Causado por: " + e.getMessage()); 
        }
    }

    /**
     * Método para seleccionar cargo
     */
    public void verDialog() {
        try {
            cargoPersonaBean.setVerDialog(true);
        } catch (Exception e) {
            System.err.println("Error al procesar verDialog de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

//</editor-fold> 
}
