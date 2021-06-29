/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.bean;

import ec.com.ingenio.ingenioconta.modelo.SpeuPerfilusuario;
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
public class PerfilUsuarioBean {

    /**
     * DECLARACIÓN DE VARIABLES
     */
    private boolean verDialog = false;
    private boolean activaGuardar = true;
    private SpeuPerfilusuario perfilUsuarioActial = new SpeuPerfilusuario();
    private List<SpeuPerfilusuario> listaPerfilUsuario = new ArrayList<SpeuPerfilusuario>();
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
     * @return the perfilUsuarioActial
     */
    public SpeuPerfilusuario getPerfilUsuarioActial() {
        return perfilUsuarioActial;
    }

    /**
     * @param perfilUsuarioActial the perfilUsuarioActial to set
     */
    public void setPerfilUsuarioActial(SpeuPerfilusuario perfilUsuarioActial) {
        this.perfilUsuarioActial = perfilUsuarioActial;
    }

    /**
     * @return the listaPerfilUsuario
     */
    public List<SpeuPerfilusuario> getListaPerfilUsuario() {
        return listaPerfilUsuario;
    }

    /**
     * @param listaPerfilUsuario the listaPerfilUsuario to set
     */
    public void setListaPerfilUsuario(List<SpeuPerfilusuario> listaPerfilUsuario) {
        this.listaPerfilUsuario = listaPerfilUsuario;
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
//</editor-fold>     

}
