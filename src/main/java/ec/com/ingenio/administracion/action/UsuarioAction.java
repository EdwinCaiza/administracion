/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.action;

import ec.com.ingenio.administracion.bean.UsuarioBean;
import ec.com.ingenio.administracion.bean.accesoBean;
import ec.com.ingenio.administracion.util.BuscarPersona;
import ec.com.ingenio.administracion.util.EncriptMD5;
import ec.com.ingenio.administracion.util.EnviaMailBean;
import ec.com.ingenio.administracion.util.UtilBean;
import ec.com.ingenio.ingenioconta.modelo.SusiUsuariosistema;
import ec.com.ingenio.ingenioconta.remoto.GperPersonaFacadeRemoto;
import ec.com.ingenio.ingenioconta.remoto.SusiUsuariosistemaFacadeRemoto;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 **%$-$0
 *
 * @author HP-01
 */
@ViewScoped
@Stateful
@Named
public class UsuarioAction {

    /**
     * DECLARACIÓN DE VARIABLES *
     */
    @EJB(lookup = "java:global/IngenioConta/SusiUsuariosistemaFacade!ec.com.ingenio.ingenioconta.remoto.SusiUsuariosistemaFacadeRemoto")
    private SusiUsuariosistemaFacadeRemoto usuarioSistemaRemoto;

    @EJB(lookup = "java:global/IngenioConta/GperPersonaFacade!ec.com.ingenio.ingenioconta.remoto.GperPersonaFacadeRemoto")
    private GperPersonaFacadeRemoto personaFacade;

    @Inject
    UsuarioBean usuarioBean;
    @Inject
    BuscarPersona buscarPersona;
    @Inject
    UtilBean utilBean;
    @Inject
    EnviaMailBean enviarEmail;
    // <editor-fold defaultstate="collapsed" desc="Métodos Principales">

    /**
     * Método para inicializar Variables de incio
     */
    public void init() {
        listarUsuarios();
    }

    /**
     * Método para crear borrado Logico Usuario
     *
     * @param usuario
     */
    public void borrarUsuario(SusiUsuariosistema usuario) {
        try {
            utilBean.mostrarMensaje(false, 1, "Exito", "Usuario borrado correctamente");
            usuarioBean.setUsuarioActual(new SusiUsuariosistema());
            usuarioBean.setVerDialog(false);
        } catch (Exception e) {
            System.err.println("Error al procesar borrarUsuario de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }

    }

    /**
     * Método para Cancelar Edición / Nuevo
     */
    public void cancelar() {
        usuarioBean.setVerDialog(false);
        usuarioBean.setUsuarioActual(new SusiUsuariosistema());
    }

    /**
     * Método para crear Nuevo Usuario
     */
    public void crearNuevo() {
        usuarioBean.setUsuarioActual(new SusiUsuariosistema());
        usuarioBean.setVerDialog(true);
    }

    /**
     * Método para activar Dialogo Crear Usuario
     */
    public void crearUsiario() {
        buscarPersona.setVerDialogBuscarPersona(false);
        usuarioBean.setVerDialog(true);
    }

    /**
     * Método para crear Editar Usuario
     *
     * @param usuario
     */
    public void editarUsuario(SusiUsuariosistema usuario) {
        usuarioBean.setUsuarioActual(usuario);
        usuarioBean.setVerDialog(true);
    }

    /**
     * Método para Guardar Información
     *
     */
    public void guardarDatos() {
        try {
            if (usuarioBean.getUsuarioActual().getUsiCodigo() == null) {
                usuarioBean.getUsuarioActual().setUsiBorradologico(true);
                usuarioBean.getUsuarioActual().setUsiEstado("A");
                String datoTemp = utilBean.generaContraseñaSegura();
                usuarioBean.getUsuarioActual().setUsiPassword(EncriptMD5.encryptMD5(datoTemp));
                if (enviarEmail.enviarCorreo(usuarioBean.getUsuarioActual().getPerCodigo().getPerCorreo(), buscarPersona.retornaApellidoNombre(usuarioBean.getUsuarioActual().getPerCodigo().getPerCodigo()), usuarioBean.getUsuarioActual().getUsiUsuario(), datoTemp)) {
                    usuarioBean.getUsuarioActual().setUsiCambiarclave("S");
                    usuarioBean.getUsuarioActual().setUsiCambioclave(new Date());
                    utilBean.mostrarMensaje(false, 2, "Exito", "Contraseña enviada con éxito a: " + usuarioBean.getUsuarioActual().getPerCodigo().getPerCorreo());
                    usuarioSistemaRemoto.create(usuarioBean.getUsuarioActual());
                    utilBean.mostrarMensaje(false, 2, "Exito", "Información almacenada correctamente");
                } else {
                    utilBean.mostrarMensaje(false, 1, "Advertencia", "Correo no enviado, Usuario NO creado");
                }

            } else {

                usuarioSistemaRemoto.edit(usuarioBean.getUsuarioActual());
                utilBean.mostrarMensaje(false, 2, "Exito", "Información modificada correctamente");
            }

            usuarioBean.setVerDialog(false);
            init();
        } catch (Exception e) {
            System.err.println("Error al procesar guardarDatos de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para cargar lista de Usuarios
     */
    public void listarUsuarios() {
        try {
            usuarioBean.setListaUsuarios(usuarioSistemaRemoto.listarUsuarios());
        } catch (Exception e) {
            System.err.println("Error al procesar listarUsuarios de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para cargar persona selccionada en entidad Usuario
     *
     * @param p
     */
    public void seleccionaPersona(String p) {

        usuarioBean.getUsuarioActual().setPerCodigo(personaFacade.retornaPersona(Integer.parseInt(p)));
        buscarPersona.getListaPersonas().clear();
        buscarPersona.setDatoBuscar("");
        buscarPersona.setVerDialogBuscarPersona(false);
    }

    /**
     * Método para Validar Usuario / Correo
     *
     */
    public void validaUsuCorreo() {
        try {
            if (usuarioBean.getCorreo() == null) {
                usuarioBean.setHabilitaBtnReset(true);
            } else {
                Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                Matcher mather = pattern.matcher(usuarioBean.getCorreo());
                if (mather.find()) {
                    if (usuarioSistemaRemoto.validaUsuPorCorreo(usuarioBean.getCorreo()) != null) {
                        usuarioBean.setUsuarioActual(usuarioSistemaRemoto.retornaUsuario(usuarioSistemaRemoto.validaUsuPorCorreo(usuarioBean.getCorreo())[0].hashCode()));
                        usuarioBean.setHabilitaBtnReset(false);
                    } else {
                        usuarioBean.setHabilitaBtnReset(true);
                        utilBean.mostrarMensaje(false, 1, "Advertencia", "Correo NO registrado");
                    }
                } else {
                    if (usuarioSistemaRemoto.validaUsuPorUsuario(usuarioBean.getCorreo()) != null) {
                        usuarioBean.setUsuarioActual(usuarioSistemaRemoto.retornaUsuario(usuarioSistemaRemoto.validaUsuPorUsuario(usuarioBean.getCorreo())[0].hashCode()));
                        usuarioBean.setHabilitaBtnReset(false);
                    } else {
                        usuarioBean.setHabilitaBtnReset(true);
                        utilBean.mostrarMensaje(false, 1, "Advertencia", "Usuario NO registrado");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al procesar validaUsuCorreo de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para Resetear Clave de Usuario
     *
     * @return
     */
    public boolean resetearClave() {
        try {
            usuarioBean.setHabilitaBtnReset(true);
            String datoTemp = utilBean.generaContraseñaSegura();
            usuarioBean.getUsuarioActual().setUsiPassword(EncriptMD5.encryptMD5(datoTemp));
            if (enviarEmail.enviarCorreo(usuarioBean.getUsuarioActual().getPerCodigo().getPerCorreo(), buscarPersona.retornaApellidoNombre(usuarioBean.getUsuarioActual().getPerCodigo().getPerCodigo()), usuarioBean.getUsuarioActual().getUsiUsuario(), datoTemp)) {
                usuarioBean.getUsuarioActual().setUsiCambiarclave("S");
                usuarioBean.getUsuarioActual().setUsiCambioclave(new Date());
                usuarioSistemaRemoto.edit(usuarioBean.getUsuarioActual());
                usuarioBean.setCorreo("");
                utilBean.mostrarMensaje(false, 1, "Exito", "Contraseña enviada con éxito a: " + usuarioBean.getUsuarioActual().getPerCodigo().getPerCorreo());
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/administracion/index.html");
                } catch (IOException ex) {
                    Logger.getLogger(accesoBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                utilBean.mostrarMensajes(1, "Advertencia", "Correo no enviado, Usuario NO creado");
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error al procesar resetearClave de " + getClass().getName() + ". Causado por: " + e.getMessage());
            return false;
        }
    }

    //</editor-fold> 
}
