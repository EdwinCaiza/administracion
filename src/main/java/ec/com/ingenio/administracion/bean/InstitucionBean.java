/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.bean;

import ec.com.ingenio.ingenioconta.modelo.PinsInstitucion;
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
public class InstitucionBean {

    /**
     * DECLARACION DE VARIABLES
     */
    private boolean verDialog = false;
    private PinsInstitucion institucionActiva = new PinsInstitucion();
    private List<PinsInstitucion> listaInstitucion = new ArrayList<PinsInstitucion>();

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
     * @return the institucionActiva
     */
    public PinsInstitucion getInstitucionActiva() {
        return institucionActiva;
    }

    /**
     * @param institucionActiva the institucionActiva to set
     */
    public void setInstitucionActiva(PinsInstitucion institucionActiva) {
        this.institucionActiva = institucionActiva;
    }

    /**
     * @return the listaInstitucion
     */
    public List<PinsInstitucion> getListaInstitucion() {
        return listaInstitucion;
    }

    /**
     * @param listaInstitucion the listaInstitucion to set
     */
    public void setListaInstitucion(List<PinsInstitucion> listaInstitucion) {
        this.listaInstitucion = listaInstitucion;
    }

    //</editor-fold> 
}
