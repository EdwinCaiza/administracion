/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.util;

import ec.com.ingenio.administracion.bean.ApplicationBean;
import ec.com.ingenio.ingenioconta.modelo.SitpItemporperfil;
import ec.com.ingenio.ingenioconta.modelo.SmeiMenuitems;
import ec.com.ingenio.ingenioconta.modelo.SusiUsuariosistema;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author E.C
 */
@Stateful
@SessionScoped
@Named
public class UtilBean implements Serializable {

    /**
     * DECLARACIÓN DE VARIABLES
     */
    private Integer usuarioLogueado;

    private SusiUsuariosistema usuarioActual = new SusiUsuariosistema();

    private List<SitpItemporperfil> listaItemPorPerfil = new ArrayList<SitpItemporperfil>();

    private List<SmeiMenuitems> listaMenuItemsPorPerfil = new ArrayList<SmeiMenuitems>();

    private MenuModel model;

    @Inject
    ApplicationBean applicationBean;

    // <editor-fold defaultstate="collapsed" desc="Métodos Principales">
    @PostConstruct
    /**
     * Metodo para inicializar Modelo
     */
    public void init() {
        setModel(new DefaultMenuModel());
    }

    /**
     * Metodo generar Menu de acuerdo a Prime
     */
    public void generaMenu() {
        try {
            model = new DefaultMenuModel();
            for (SmeiMenuitems item : listaMenuItemsPorPerfil) {
                if (item.getSmeMeiCodigo() == null) {
                    DefaultSubMenu firstSubmenu = DefaultSubMenu.builder().label(item.getMeiDescripcion()).build();

                    for (SmeiMenuitems subItem : listaMenuItemsPorPerfil) {
                        SmeiMenuitems subMenu = subItem;
                        if (subMenu.getSmeMeiCodigo() != null) {
                            if (subMenu.getSmeMeiCodigo().getMeiCodigo().equals(item.getMeiCodigo())) {
                                DefaultMenuItem itemSub = DefaultMenuItem.builder()
                                        .value(subMenu.getMeiDescripcion())
                                        .icon("pi pi-external-link")
                                        .ajax(false)
                                        .url(subMenu.getMeiUrl())
                                        .update("messages")
                                        .build();
                                firstSubmenu.getElements().add(itemSub);
                            }

                        }

                    }
                    model.getElements().add(firstSubmenu);
                }

            }
        } catch (Exception e) {
            System.err.println("Error al procesar generaMenu de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método par abtener la opcion del menu
     *
     * @param menuItems
     */
    public void selecionarOpcionMenu(SmeiMenuitems menuItems) {
        // rolRecursoPermisos = rr;
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(menuItems.getMeiUrl());
        } catch (IOException ex) {
            Logger.getLogger(UtilBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @param usaPropiedades define si los valores a mostrar forman parte del
     * archivo de propiedades
     * @param severidad Indica severidad = 0 error, 1 dvertencia, 2 correcto
     * @param titulo Tittle of message
     * @param detalle Detail or content of message
     */
    public void mostrarMensaje(boolean usaPropiedades, int severidad, String titulo, String detalle) {
        if (usaPropiedades) {
            titulo = applicationBean.obtenerValorPropiedad(titulo);
            detalle = applicationBean.obtenerValorPropiedad(detalle);
        }
        if (severidad == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, detalle));
        }

        if (severidad == 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, detalle));
        }

        if (severidad == 2) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, detalle));
        }

    }

     /**
     * @param severidad Indica severidad = 0 error, 1 advertencia, 2 correcto
     * @param titulo Tittle of message
     * @param detalle Detail or content of message
     */
    public void mostrarMensajes( int severidad, String titulo, String detalle) {
      
            titulo = applicationBean.obtenerValorPropiedad(titulo);
            detalle = applicationBean.obtenerValorPropiedad(detalle);
      
        if (severidad == 0) {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, detalle));
        }

        if (severidad == 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, detalle));
        }

        if (severidad == 2) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, detalle));
        }

    }
    
    
    /**
     * Método para retornar Alias a descripciones
     *
     * @param dato
     * @return
     */
    public String retornaDescripcion(char dato) {
        try {
            switch (dato) {
                case 'A':
                    return "ABIERTO";
                case 'I':
                    return "INACTIVO";
                case 'C':
                    return "CERRADO";
                default:
                    return "";
            }
        } catch (Exception e) {
            System.err.println("Error al procesar retornaDesacripcion de " + getClass().getName() + ". Causado por:" + e.getMessage());
            return "";
        }
    }

    /**
     * Método para generar Clave aleatoria
     */
    public String generaContraseñaSegura() {
        try {
            String[] symbols = {"0", "1", "-", "*", "%", "$", "a", "b", "c"};
            String password = "";
            int length = 6;
            Random random;
            try {
                random = SecureRandom.getInstanceStrong();
                StringBuilder sb = new StringBuilder(length);
                for (int i = 0; i < length; i++) {
                    int indexRandom = random.nextInt(symbols.length);
                    sb.append(symbols[indexRandom]);
                }
                password = sb.toString();
                System.out.println(password);
            } catch (NoSuchAlgorithmException e) {
                System.out.println(e.toString());
            }
            return password;
        } catch (Exception e) {
            System.err.println("Error al procesar generaContraseñaSegura de " + getClass().getName() + ". Causado por:" + e.getMessage());
            return null;
        }
    }

    public boolean validadorDeCedula(String cedula) {
        boolean cedulaCorrecta = false;

        try {

            if (cedula.length() == 10) // ConstantesApp.LongitudCedula
            {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {
                    // Coeficientes de validación cédula
                    // El decimo digito se lo considera dígito verificador
                    int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                    int verificador = Integer.parseInt(cedula.substring(9, 10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }

                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    } else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            System.out.println("Una excepcion ocurrio en el proceso de validadcion");
            cedulaCorrecta = false;
        }

        if (!cedulaCorrecta) {
            System.out.println("La Cédula ingresada es Incorrecta");
        }
        return cedulaCorrecta;
    }

    //</editor-fold>   
    // <editor-fold defaultstate="collapsed" desc="Getter and Setter">
    /**
     * @return the usuarioLogueado
     */
    public Integer getUsuarioLogueado() {
        return usuarioLogueado;
    }

    /**
     * @param usuarioLogueado the usuarioLogueado to set
     */
    public void setUsuarioLogueado(Integer usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }

    /**
     * @return the usuarioActual
     */
    public SusiUsuariosistema getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * @param usuarioActual the usuarioActual to set
     */
    public void setUsuarioActual(SusiUsuariosistema usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    /**
     * @return the listaItemPorPerfil
     */
    public List<SitpItemporperfil> getListaItemPorPerfil() {
        return listaItemPorPerfil;
    }

    /**
     * @param listaItemPorPerfil the listaItemPorPerfil to set
     */
    public void setListaItemPorPerfil(List<SitpItemporperfil> listaItemPorPerfil) {
        this.listaItemPorPerfil = listaItemPorPerfil;
    }

    /**
     * @return the listaMenuItemsPorPerfil
     */
    public List<SmeiMenuitems> getListaMenuItemsPorPerfil() {
        return listaMenuItemsPorPerfil;
    }

    /**
     * @param listaMenuItemsPorPerfil the listaMenuItemsPorPerfil to set
     */
    public void setListaMenuItemsPorPerfil(List<SmeiMenuitems> listaMenuItemsPorPerfil) {
        this.listaMenuItemsPorPerfil = listaMenuItemsPorPerfil;
    }

    /**
     * @return the model
     */
    public MenuModel getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(MenuModel model) {
        this.model = model;
    }
    //</editor-fold>  

}
