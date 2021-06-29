/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.bean;

import java.util.Properties;
import javax.ejb.Stateful;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author E.C
 */
@ViewScoped
@Stateful
@Named
@ManagedBean
public class ApplicationBean {

    /**
     * DECLARACIÓN DE VARIABLES
     */
    private Properties properties = new Properties();

    // <editor-fold defaultstate="collapsed" desc="Métodos">  
    /**
     * Método para
     *
     * @param clave
     * @return
     */
    public String obtenerValorPropiedad(String clave) {
        return properties.getProperty(clave);
    }
    //</editor-fold>   
}
