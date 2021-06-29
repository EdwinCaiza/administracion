/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.bean;

import ec.com.ingenio.ingenioconta.modelo.GcarCargos;
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
public class CargosBean {

    /**
     * Declaracion de variables
     */
    private boolean verDialog = false;
    private boolean verEstado=false;
    private boolean activaGuardar = true;
    private GcarCargos cargoActual = new GcarCargos();
    private List<GcarCargos> listaCargos = new ArrayList<GcarCargos>();

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
     * @return the cargoActual
     */
    public GcarCargos getCargoActual() {
        return cargoActual;
    }

    /**
     * @param cargoActual the cargoActual to set
     */
    public void setCargoActual(GcarCargos cargoActual) {
        this.cargoActual = cargoActual;
    }

    /**
     * @return the listaCargos
     */
    public List<GcarCargos> getListaCargos() {
        return listaCargos;
    }

    /**
     * @param listaCargos the listaCargos to set
     */
    public void setListaCargos(List<GcarCargos> listaCargos) {
        this.listaCargos = listaCargos;
    }

    /**
     * @return the activaGuardar
     */
    public boolean isActivaGuardar() {
        return activaGuardar;
    }

    /**
     * @param activaGuardar the activaGuardar to set
     */
    public void setActivaGuardar(boolean activaGuardar) {
        this.activaGuardar = activaGuardar;
    }
    
    /**
     * @return the verEstado
     */
    public boolean isVerEstado() {
        return verEstado;
    }

    /**
     * @param verEstado the verEstado to set
     */
    public void setVerEstado(boolean verEstado) {
        this.verEstado = verEstado;
    }
    //</editor-fold> 

    

}
