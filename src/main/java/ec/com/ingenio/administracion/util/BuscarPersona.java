/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.util;

import ec.com.ingenio.consultascomunes.remoto.consultasNominaRemoto;
import ec.com.ingenio.ingenioconta.modelo.GperPersona;
import ec.com.ingenio.ingenioconta.remoto.GperPersonaFacadeRemoto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author hacker
 */
@Stateful
@SessionScoped
@Named
public class BuscarPersona implements Serializable {

    /**
     * ************* DECLARACIÓN DE VARIABLES ***********
     */
    private boolean verDialogBuscarPersona = false;
    private String datoBuscar = "";
    private GperPersona personaActual = new GperPersona();
    private List<Object[]> listaPersonas;

    @EJB(lookup = "java:global/consultasComunes-ejb/consultasNomina!ec.com.ingenio.consultascomunes.remoto.consultasNominaRemoto")
    private consultasNominaRemoto consultasNomina;
   

    @Inject
    UtilBean utilBean;

    // <editor-fold defaultstate="collapsed" desc="Métodos Principales">
    /**
     * Metodo para Activar ventana de buscar persona
     */
    public void buscarPersona() {
        setDatoBuscar("");
        setVerDialogBuscarPersona(true);
    }

    /**
     * Metodo para buscar persona segun el dato proporcionado
     */
    public void buscarPersonaConDato() {
        try {
            if (!datoBuscar.isEmpty()) {
                if (datoBuscar.matches("[+-]?\\d*(\\.\\d+)?")) {
                    setListaPersonas(consultasNomina.buscarPersonaCedula(datoBuscar));
                } else {
                    setListaPersonas(consultasNomina.buscarPersonaApellido(datoBuscar));
                }
            } else {
                utilBean.mostrarMensaje(false, 1, "Advertencia", "Digite dato a buscar");
            }
        } catch (Exception e) {
            System.err.println("Error al procesar buscarPersonaConDato de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para cancelar la Busqueda
     */
    public void cancelarBusqueda() {
        setVerDialogBuscarPersona(false);
    }

    /**
     * Método para Retornar Nombres/Apellidos de Persona
     *
     * @param codigoPersona
     * @return
     */
    public String retornaApellidoNombre(Integer codigoPersona) {
        if (codigoPersona != null) {
            return consultasNomina.retornaNombrePersona(codigoPersona);
        } else {
            return null;
        }

    }

    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Getter and Setter">
    /**
     * @return the verDialogBuscarPersona
     */
    public boolean isVerDialogBuscarPersona() {
        return verDialogBuscarPersona;
    }

    /**
     * @param verDialogBuscarPersona the verDialogBuscarPersona to set
     */
    public void setVerDialogBuscarPersona(boolean verDialogBuscarPersona) {
        this.verDialogBuscarPersona = verDialogBuscarPersona;
    }

    /**
     * @return the datoBuscar
     */
    public String getDatoBuscar() {
        return datoBuscar;
    }

    /**
     * @param datoBuscar the datoBuscar to set
     */
    public void setDatoBuscar(String datoBuscar) {
        this.datoBuscar = datoBuscar;
    }

    /**
     * @return the personaActual
     */
    public GperPersona getPersonaActual() {
        return personaActual;
    }

    /**
     * @param personaActual the personaActual to set
     */
    public void setPersonaActual(GperPersona personaActual) {
        this.personaActual = personaActual;
    }

    /**
     * @return the listaPersonas
     */
    public List<Object[]> getListaPersonas() {
        return listaPersonas;
    }

    /**
     * @param listaPersonas the listaPersonas to set
     */
    public void setListaPersonas(List<Object[]> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    //</editor-fold>
}
