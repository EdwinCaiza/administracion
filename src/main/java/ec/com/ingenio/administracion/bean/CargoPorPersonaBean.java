/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.bean;

import ec.com.ingenio.ingenioconta.modelo.GcapCargopersona;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateful;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author hacker
 */
@ViewScoped
@Stateful
@Named
public class CargoPorPersonaBean {

    /**
     * Declaración de Variables
     */
    private boolean verDialog = false;
    private GcapCargopersona cargoPersonaActual = new GcapCargopersona();
    private List<GcapCargopersona> listaCargoPersona = new ArrayList<GcapCargopersona>();

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
     * @return the cargoPersonaActual
     */
    public GcapCargopersona getCargoPersonaActual() {
        return cargoPersonaActual;
    }

    /**
     * @param cargoPersonaActual the cargoPersonaActual to set
     */
    public void setCargoPersonaActual(GcapCargopersona cargoPersonaActual) {
        this.cargoPersonaActual = cargoPersonaActual;
    }

    /**
     * @return the listaCargoPersona
     */
    public List<GcapCargopersona> getListaCargoPersona() {
        return listaCargoPersona;
    }

    /**
     * @param listaCargoPersona the listaCargoPersona to set
     */
    public void setListaCargoPersona(List<GcapCargopersona> listaCargoPersona) {
        this.listaCargoPersona = listaCargoPersona;
    }

//</editor-fold> 
}
