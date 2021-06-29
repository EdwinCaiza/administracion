/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.bean;

import ec.com.ingenio.ingenioconta.modelo.GtipTipopersona;
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
public class TipoPersonaBean {

    /**
     * DECALRACION DE VARIABLES
     */
    private boolean verDialog = false;
    private GtipTipopersona tipoPersonaActual = new GtipTipopersona();
    private List<GtipTipopersona> listaTipoPersona = new ArrayList<GtipTipopersona>();

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
     * @return the tipoPersonaActual
     */
    public GtipTipopersona getTipoPersonaActual() {
        return tipoPersonaActual;
    }

    /**
     * @param tipoPersonaActual the tipoPersonaActual to set
     */
    public void setTipoPersonaActual(GtipTipopersona tipoPersonaActual) {
        this.tipoPersonaActual = tipoPersonaActual;
    }

    /**
     * @return the listaTipoPersona
     */
    public List<GtipTipopersona> getListaTipoPersona() {
        return listaTipoPersona;
    }

    /**
     * @param listaTipoPersona the listaTipoPersona to set
     */
    public void setListaTipoPersona(List<GtipTipopersona> listaTipoPersona) {
        this.listaTipoPersona = listaTipoPersona;
    }
    //</editor-fold> 
}
