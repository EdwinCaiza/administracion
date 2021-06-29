/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.bean;

import ec.com.ingenio.administracion.util.BuscarPersona;
import ec.com.ingenio.administracion.util.EncriptMD5;
import ec.com.ingenio.administracion.util.EnviaMailBean;
import ec.com.ingenio.administracion.util.UtilBean;
import ec.com.ingenio.ingenioconta.modelo.SitpItemporperfil;
import ec.com.ingenio.ingenioconta.modelo.SmeiMenuitems;
import ec.com.ingenio.ingenioconta.modelo.SpeuPerfilusuario;
import ec.com.ingenio.ingenioconta.modelo.SusiUsuariosistema;
import ec.com.ingenio.ingenioconta.remoto.SitpItemporperfilFacadeRemoto;
import ec.com.ingenio.ingenioconta.remoto.SmeiMenuitemsFacadeRemoto;
import ec.com.ingenio.ingenioconta.remoto.SpeuPerfilusuarioFacadeRemoto;
import ec.com.ingenio.ingenioconta.remoto.SusiUsuariosistemaFacadeRemoto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author E.C
 */
@ViewScoped
@Stateful
@Named
public class accesoBean {

    /**
     * DECLARACIÓN DE VARIABLES
     */
    private String usuario = "";
    private String contrasena = "";
    private String contrasenaNueva = "";
    private String moduloAcceso = "MÓDULO DE ADMINISTRACIÓN";
    private boolean verDialogCambioContraseña;
    private boolean verRegistro = false;
    private boolean activaTxtContrasena = true;
    private boolean activaCambiaContraseña = true;
    Integer numeroIntentoRestante;
    Integer numeroIntento;

    private List<SusiUsuariosistema> listaUsuario = new ArrayList<SusiUsuariosistema>();
    private SusiUsuariosistema usuarioActual = new SusiUsuariosistema();

    private SpeuPerfilusuario perfilUsuarioLogueado = new SpeuPerfilusuario();
    private SmeiMenuitems menuItem = new SmeiMenuitems();
    private List<SitpItemporperfil> listaItemPorPerfil = new ArrayList<SitpItemporperfil>();

    @EJB(lookup = "java:global/IngenioConta/SusiUsuariosistemaFacade!ec.com.ingenio.ingenioconta.remoto.SusiUsuariosistemaFacadeRemoto")
    private SusiUsuariosistemaFacadeRemoto usuarioSistemaRemoto;
    @EJB(lookup = "java:global/IngenioConta/SpeuPerfilusuarioFacade!ec.com.ingenio.ingenioconta.remoto.SpeuPerfilusuarioFacadeRemoto")
    private SpeuPerfilusuarioFacadeRemoto perfilUsuarioRemoto;
    @EJB(lookup = "java:global/IngenioConta/SitpItemporperfilFacade!ec.com.ingenio.ingenioconta.remoto.SitpItemporperfilFacadeRemoto")
    private SitpItemporperfilFacadeRemoto itemPorPerfilRemoto;
    @EJB(lookup = "java:global/IngenioConta/SmeiMenuitemsFacade!ec.com.ingenio.ingenioconta.remoto.SmeiMenuitemsFacadeRemoto")
    private SmeiMenuitemsFacadeRemoto menuItemsRemoto;
    @Inject
    protected UtilBean utilBean;
    @Inject
    EnviaMailBean enviarEmail;
    @Inject
    BuscarPersona buscarPersona;

    // <editor-fold defaultstate="collapsed" desc="Métodos Principales">
    /**
     * Metodo PostConstruct
     */
    @PostConstruct
    public void init() {
//        log = Logger.getLogger(LoginBean.class.getName());
        numeroIntentoRestante = 3;
        numeroIntento = 3;
    }

    /**
     * Método para validar la existencia de Usuario
     *
     */
    public void activaRegistro() {
        setVerRegistro(true);
    }

    /**
     * Método para Cambiar Clave de Usuario
     *
     * @return
     */
    public void cambiarClave() {
        try {
            utilBean.getUsuarioLogueado();
            getUsuarioActual().setUsiPassword(EncriptMD5.encryptMD5(getContrasena()));
            getUsuarioActual().setUsiCambiarclave("N");
            getUsuarioActual().setUsiCambioclave(new Date());
            usuarioSistemaRemoto.edit(getUsuarioActual());
            setUsuario("");
            setContrasena("");
            setContrasenaNueva("");

            setActivaTxtContrasena(true);
            setActivaCambiaContraseña(true);
            utilBean.mostrarMensajes(1, "Exito", "Contraseña enviada con éxito a: " + getUsuarioActual().getPerCodigo().getPerCorreo());
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/administracion/index.html");
            } catch (IOException ex) {
                Logger.getLogger(accesoBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            System.err.println("Error al procesar resetearClave de " + getClass().getName() + ". Causado por: " + e.getMessage());
            //return false;
        }
    }

    /**
     * guarda el log de la sesion del usuario que acaba de acceder
     *
     ***
     */
    public void guardarLogSesion() {
//        logSesionActual = new LogSesion();
//        logSesionActual.setUsuario(usuarioActual);
//        logSesionActual.setLogFechaIngreso(new Date());
//        logSesionActual.setLogNumeroIntento((numeroIntento + 1) - numeroIntentoRestante);  // para que llegue el numero de intento con el que accedio, y no el intento restante 
//        logSesionActual.setLogIpConexion(ipConexion);
//        logSesionServicio.create(logSesionActual);
    }

    /**
     * Método para validar la existencia de Usuario
     *
     */
    public void cargarPerfilUsuario() {
        try {
           
            perfilUsuarioLogueado = perfilUsuarioRemoto.obtienePerfilUsuario(utilBean.getUsuarioActual());
        } catch (Exception e) {
            System.err.println("Error al cargarPerfilUsuario de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para cargar lista de Items por Perfil
     *
     */
    public void cargarItemsPorPerfil() {
        try {
            utilBean.setListaItemPorPerfil(itemPorPerfilRemoto.listarItemPorPerfil(perfilUsuarioLogueado));
            cargarMenuItemsPorPerfil();
        } catch (Exception e) {
            System.err.println("Error al procesar cargarItemsPorPerfil de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para cargar lista de Menu Perfil
     *
     */
    public void cargarMenuItemsPorPerfil() {
        try {
            utilBean.getListaMenuItemsPorPerfil().clear();
            for (SitpItemporperfil item : utilBean.getListaItemPorPerfil()) {
                menuItem = menuItemsRemoto.retornaMenuItem(item.getMeiCodigo());
                utilBean.getListaMenuItemsPorPerfil().add(menuItem);
            }

        } catch (Exception e) {
            System.err.println("Error al procesar cargarItemsPorPerfil de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para validar la existencia de Usuario
     *
     */
    public void validaAcceso() {
        try {
            setListaUsuario(usuarioSistemaRemoto.buscarUsuarioPorDescripcion(getUsuario()));
            if (!listaUsuario.isEmpty()) {
                String encripta = EncriptMD5.encryptMD5(getContrasena());
                setUsuarioActual(getListaUsuario().get(0));
                if (encripta.equals(getUsuarioActual().getUsiPassword())) {
                    guardarLogSesion();
                    utilBean.setUsuarioActual(usuarioActual);
                    utilBean.setUsuarioLogueado(usuarioActual.getUsiCodigo());
                    if (validaEstadoCredenciales()) {
                        utilBean.mostrarMensaje(false, 1, "Advertencia", "Es necesario Cambiar su contraseña");
                        try {
                            FacesContext.getCurrentInstance().getExternalContext().redirect("/administracion/cambiaContrasena.html");
                        } catch (IOException ex) {
                            Logger.getLogger(accesoBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        cargarPerfilUsuario();
                        cargarItemsPorPerfil();
                        utilBean.generaMenu();

                        try {
                            FacesContext.getCurrentInstance().getExternalContext().redirect("private/entornoPrincipal.html");
                        } catch (IOException ex) {
                            Logger.getLogger(accesoBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    setUsuario("");
                    setContrasena("");
                    numeroIntentoRestante--;
                    if (numeroIntentoRestante > 0) {
                        utilBean.mostrarMensaje(false, 0, "Error: ", "Usuario o contraseña inválidos, dispone de " + numeroIntentoRestante + " intentos más");
                    } else { //si ya cumplio su numero de intentos requeridos lo guardamos en usuarios bloqueados
                        utilBean.mostrarMensaje(false, 1, "Error de credenciales", "El sistema estará bloqueado por 30 minutos, consulte con su administrador");
                    }
                }
            } else {
                if (!usuario.isEmpty()) {
                    setUsuario("");
                    utilBean.mostrarMensaje(false, 1, "Advertencia", "Usuario NO REGISTRADO");
                }

            }
        } catch (Exception e) {
            System.err.println("Erros al procesar validaAcceso de " + getClass().getName() + ". Causado por " + e.getMessage());
        }

    }

    /**
     * Método para validar estado de Credeciales
     */
    public boolean validaEstadoCredenciales() {
        try {
            if (usuarioSistemaRemoto.validaEstadoCredenciales(new SusiUsuariosistema(utilBean.getUsuarioLogueado()))) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al procesar validaEstadoCredenciales de " + getClass().getName() + ". Causado por: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para validar estado de Credeciales
     */
    public void validaContrasena() {
        try {
            String encripta = EncriptMD5.encryptMD5(getUsuario());
            setUsuarioActual(usuarioSistemaRemoto.retornaUsuario(utilBean.getUsuarioLogueado()));
            if (encripta.equals(usuarioSistemaRemoto.retornaUsuario(utilBean.getUsuarioLogueado()).getUsiPassword())) {
                setActivaTxtContrasena(false);
                utilBean.mostrarMensaje(false, 2, "Exito", "Contrasena validada");
            } else {
                utilBean.mostrarMensaje(false, 0, "Error", "Contraseña actual no coincide");
                setActivaTxtContrasena(true);
                System.out.println("contraseña Antigua No Conicide");
            }
        } catch (Exception e) {
            System.err.println("Error al procesar validaContrasena de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }

    }

    /**
     * Método para validar estado de Credeciales
     */
    public void validaContraseñasNuevas() {
        try {
            if (getContrasena().equals(getContrasenaNueva())) {
                utilBean.mostrarMensaje(false, 2, "Exito", "Contrasenas coinciden");
                setActivaCambiaContraseña(false);
            } else {
                setContrasena("");
                setContrasenaNueva("");
                setActivaCambiaContraseña(true);
                utilBean.mostrarMensaje(false, 1, "Advertencia", "Contraseñas No Coinciden");
            }
        } catch (Exception e) {
            System.err.println("Error al procesar validaContraseñasNuevas de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    //</editor-fold>   
    // <editor-fold defaultstate="collapsed" desc="Getter and Setter de Variables">   
    /**
     * @return the listaUsuario
     */
    public List<SusiUsuariosistema> getListaUsuario() {
        return listaUsuario;
    }

    /**
     * @param listaUsuario the listaUsuario to set
     */
    public void setListaUsuario(List<SusiUsuariosistema> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    /**
     * @return the usuarioActual
     */
    public SusiUsuariosistema getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * @return the contrasenaNueva
     */
    public String getContrasenaNueva() {
        return contrasenaNueva;
    }

    /**
     * @param contrasenaNueva the contrasenaNueva to set
     */
    public void setContrasenaNueva(String contrasenaNueva) {
        this.contrasenaNueva = contrasenaNueva;
    }

    /**
     * @param usuarioActual the usuarioActual to set
     */
    public void setUsuarioActual(SusiUsuariosistema usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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
     * @return the moduloAcceso
     */
    public String getModuloAcceso() {
        return moduloAcceso;
    }

    /**
     * @param moduloAcceso the moduloAcceso to set
     */
    public void setModuloAcceso(String moduloAcceso) {
        this.moduloAcceso = moduloAcceso;
    }

    /**
     * @return the verDialogCambioContraseña
     */
    public boolean isVerDialogCambioContraseña() {
        return verDialogCambioContraseña;
    }

    /**
     * @param verDialogCambioContraseña the verDialogCambioContraseña to set
     */
    public void setVerDialogCambioContraseña(boolean verDialogCambioContraseña) {
        this.verDialogCambioContraseña = verDialogCambioContraseña;
    }

    /**
     * @return the verRegistro
     */
    public boolean isVerRegistro() {
        return verRegistro;
    }

    /**
     * @param verRegistro the verRegistro to set
     */
    public void setVerRegistro(boolean verRegistro) {
        this.verRegistro = verRegistro;
    }

    /**
     * @return the activaTxtContrasena
     */
    public boolean isActivaTxtContrasena() {
        return activaTxtContrasena;
    }

    /**
     * @param activaTxtContrasena the activaTxtContrasena to set
     */
    public void setActivaTxtContrasena(boolean activaTxtContrasena) {
        this.activaTxtContrasena = activaTxtContrasena;
    }

    /**
     * @return the activaCambiaContraseña
     */
    public boolean isActivaCambiaContraseña() {
        return activaCambiaContraseña;
    }

    /**
     * @param activaCambiaContraseña the activaCambiaContraseña to set
     */
    public void setActivaCambiaContraseña(boolean activaCambiaContraseña) {
        this.activaCambiaContraseña = activaCambiaContraseña;
    }
    //</editor-fold>   

}
