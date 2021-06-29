/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.bean;

import ec.com.ingenio.ingenioconta.modelo.GperPersona;
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
public class PersonaBean {

    /**
     * DECLARACIÓN DE VARIABLES
     */
    private boolean verDialog = false;
    private GperPersona personaActual = new GperPersona();
    private List<GperPersona> listaPersonas = new ArrayList<GperPersona>();
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
    public List<GperPersona> getListaPersonas() {
        return listaPersonas;
    }

    /**
     * @param listaPersonas the listaPersonas to set
     */
    public void setListaPersonas(List<GperPersona> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }
    //</editor-fold> 
}
