/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.action;

import ec.com.ingenio.administracion.bean.TipoPersonaBean;
import ec.com.ingenio.ingenioconta.remoto.GtipTipopersonaFacadeRemoto;
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
public class TipoPersonaAction {

    /**
     * DECLARACIÓN DE VARIABLES *
     */
    @EJB(lookup = "java:global/IngenioConta/GtipTipopersonaFacade!ec.com.ingenio.ingenioconta.remoto.GtipTipopersonaFacadeRemoto")
    private GtipTipopersonaFacadeRemoto tipoPersonaFacade;

    @Inject
    TipoPersonaBean tipoPersonaBean;

    // <editor-fold defaultstate="collapsed" desc="Métodos Principales">
    /**
     * Método para cargar variables necesarias
     */
    public void init() {
        cargarTipoPersona();
    }

    /**
     * Metodo para cargar lista de Tipos de Persona
     */
    public void cargarTipoPersona() {
        try {
            tipoPersonaBean.getListaTipoPersona().clear();
            tipoPersonaBean.setListaTipoPersona(tipoPersonaFacade.listarTipoPersona());
        } catch (Exception e) {
            System.err.println("Error al procesar cargarTipoPersona de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }
    //</editor-fold> 

}
