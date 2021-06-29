/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.action;

import ec.com.ingenio.administracion.bean.PersonaBean;
import ec.com.ingenio.administracion.bean.TipoPersonaBean;
import ec.com.ingenio.administracion.bean.UsuarioBean;
import ec.com.ingenio.administracion.bean.accesoBean;
import ec.com.ingenio.administracion.util.BuscarPersona;
import ec.com.ingenio.administracion.util.EncriptMD5;
import ec.com.ingenio.administracion.util.EnviaMailBean;
import ec.com.ingenio.administracion.util.UtilBean;
import ec.com.ingenio.ingenioconta.modelo.GperPersona;
import ec.com.ingenio.ingenioconta.modelo.SusiUsuariosistema;
import ec.com.ingenio.ingenioconta.remoto.GperPersonaFacadeRemoto;
import ec.com.ingenio.ingenioconta.remoto.GtipTipopersonaFacadeRemoto;
import ec.com.ingenio.ingenioconta.remoto.SusiUsuariosistemaFacadeRemoto;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.context.FacesContext;
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
public class PersonaAction {

    /**
     * DECLARACION DE VARIABLES
     */
    @EJB(lookup = "java:global/IngenioConta/GperPersonaFacade!ec.com.ingenio.ingenioconta.remoto.GperPersonaFacadeRemoto")
    private GperPersonaFacadeRemoto personaFacade;
    @EJB(lookup = "java:global/IngenioConta/GtipTipopersonaFacade!ec.com.ingenio.ingenioconta.remoto.GtipTipopersonaFacadeRemoto")
    private GtipTipopersonaFacadeRemoto tipoPersonaFacade;
    @EJB(lookup = "java:global/IngenioConta/SusiUsuariosistemaFacade!ec.com.ingenio.ingenioconta.remoto.SusiUsuariosistemaFacadeRemoto")
    private SusiUsuariosistemaFacadeRemoto usuarioSistemaRemoto;

    @Inject
    PersonaBean personaBean;
    @Inject
    BuscarPersona buscarPersona;
    @Inject
    TipoPersonaBean tipoPersonaBean;
    @Inject
    UtilBean utilBean;
    @Inject
    UsuarioBean usuarioBean;
    @Inject
    EnviaMailBean enviarEmail;

    // <editor-fold defaultstate="collapsed" desc="Métodos Principales">
    /**
     * Método para carga Inicial de variables
     */
    public void init() {
        listarPersonas();
    }

    /**
     * Método para Activar dialogo de nueva persona
     */
    public void crearNuevo() {
        personaBean.setPersonaActual(new GperPersona());
        listarTiposDePersona();
        personaBean.setVerDialog(true);
    }

    /**
     * Método para Desactivar dialogo de nueva persona
     */
    public void cancelar() {
        tipoPersonaBean.getListaTipoPersona().clear();
        personaBean.setVerDialog(false);
    }

    /**
     * Método para Eliminar elemento
     *
     * @param persona
     */
    public void eliminarPersona(GperPersona persona) {
        try {
            if (persona.getPerCodigo() != null) {
                if (personaFacade.eliminaRegistro(persona)) {
                    listarPersonas();
                    utilBean.mostrarMensaje(false, 2, "Exito", "Información eliminada con éxito");
                } else {
                    utilBean.mostrarMensaje(false, 1, "Advertencia", "Registro no Eliminado");
                }

            }

        } catch (Exception e) {
            System.err.println("Error al procesar eliminarPersona de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para Envio de Usuario Y clave a correo
     *
     * @return
     */
    public void enviaEmail() {
        try {
            String datoTemp = utilBean.generaContraseñaSegura();
            if (personaBean.getPersonaActual().getPerCorreo() != null) {
                usuarioBean.getUsuarioActual().setPerCodigo(personaBean.getPersonaActual());
                usuarioBean.getUsuarioActual().setUsiBorradologico(true);
                usuarioBean.getUsuarioActual().setUsiEstado("A");
                usuarioBean.getUsuarioActual().setUsiFecharegistro(new Date());
                usuarioBean.getUsuarioActual().setUsiPassword(EncriptMD5.encryptMD5(datoTemp));
                if (enviarEmail.enviarCorreo(personaBean.getPersonaActual().getPerCorreo(), buscarPersona.retornaApellidoNombre(personaBean.getPersonaActual().getPerCodigo()), usuarioBean.getUsuarioActual().getUsiUsuario(), datoTemp)) {
                    //utilBean.mostrarMensaje(true, 2, "Exito", "Contraseña enviada con éxito a: " + usuarioBean.getUsuarioActual().getPerCodigo().getPerCorreo());
                    usuarioSistemaRemoto.create(usuarioBean.getUsuarioActual());
                    //utilBean.mostrarMensaje(true, 2, "Exito", "Información almacenada correctamente");
                } else {
                    System.out.println("************error al envia correo");
                    utilBean.mostrarMensaje(false, 1, "Advertencia", "Correo no enviado, Usuario NO creado");
                }

            }
        } catch (Exception e) {
            System.err.println("Error al procesar enviaEmail de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para Generar Usuario a partir de Nombre y apellido
     *
     * @return
     */
    public String generaUsuario() {
        try {
            String usuAux = "";
            if (personaBean.getPersonaActual().getPerNombres() != null && personaBean.getPersonaActual().getPerApellidos() != null) {
                usuAux = personaBean.getPersonaActual().getPerNombres() + personaBean.getPersonaActual().getPerApellidos();
            } else if (personaBean.getPersonaActual().getPerNombres() == null) {
                usuAux = personaBean.getPersonaActual().getPerApellidos();
            } else if (personaBean.getPersonaActual().getPerApellidos() == null) {
                usuAux = personaBean.getPersonaActual().getPerNombres();
            }

            usuarioBean.getUsuarioActual().setUsiUsuario(usuAux);
            return usuAux;
        } catch (Exception e) {
            System.err.println("Error al procesar generaUsuario de " + getClass().getName() + ". Causado por: " + e.getMessage());
            return null;
        }
    }

    /**
     * Método para Guardar Informació
     */
    public void guardarPersona() {
        try {
            if (personaBean.getPersonaActual().getPerCodigo() == null) {
                personaBean.getPersonaActual().setPerEstado("A");
                personaBean.getPersonaActual().setPerBorradologico(true);
                personaFacade.create(personaBean.getPersonaActual());
                utilBean.mostrarMensaje(false, 2, "Exito", "Información almacenada correctamente");
            } else {
                personaFacade.edit(personaBean.getPersonaActual());
                utilBean.mostrarMensaje(false, 2, "Exito", "Información modificada correctamente");
            }
            listarPersonas();
            tipoPersonaBean.getListaTipoPersona().clear();
            personaBean.setPersonaActual(new GperPersona());
            personaBean.setVerDialog(false);

        } catch (Exception e) {
            System.err.println("Error al procesar guardarPersona de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }

    }

    /**
     * Método para Guardar persona y generar Usuario
     */
    public void guardaPersonaGeneraUsuario() {
        try {
            System.out.println("************legando");
            obtenerTipoPersona("USU");
            if (personaBean.getPersonaActual().getPerCodigo() == null && personaBean.getPersonaActual().getPerApellidos() != null) {
                personaBean.getPersonaActual().setPerEstado("A");
                personaBean.getPersonaActual().setPerBorradologico(true);
                personaBean.getPersonaActual().setTipCodigo(tipoPersonaBean.getTipoPersonaActual());
                personaFacade.create(personaBean.getPersonaActual());
                enviaEmail();
                personaBean.setPersonaActual(new GperPersona());
                usuarioBean.setUsuarioActual(new SusiUsuariosistema());
            }
//            try {
//                FacesContext.getCurrentInstance().getExternalContext().redirect("/administracion/index.html");
//            } catch (IOException ex) {
//                Logger.getLogger(accesoBean.class.getName()).log(Level.SEVERE, null, ex);
//            }

        } catch (Exception e) {
            System.err.println("Error al procesar guardaPersonaGeneraUsuario de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para carga lista de personas asignadas
     */
    public void listarPersonas() {
        try {
            personaBean.setListaPersonas(personaFacade.retornaListarPersonas());
        } catch (Exception e) {
            System.err.println("Error al procesar listarPersonas de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para carga lista de tipos de personas
     */
    public void listarTiposDePersona() {
        try {
            tipoPersonaBean.setListaTipoPersona(tipoPersonaFacade.listarTipoPersona());
        } catch (Exception e) {
            System.err.println("Error al procesar listarTiposDePersona de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para Obtener Tipo persona
     *
     * @param Identificador
     */
    public void obtenerTipoPersona(String Identificador) {
        try {
            tipoPersonaBean.setTipoPersonaActual(tipoPersonaFacade.retornaTipoPerPorIdentificador(Identificador));
        } catch (Exception e) {
            System.err.println("Error al procesar obtenerTipoPersona de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }
    /**
     * Método para Asignar el Item seleccionado a la entidad Principal Activa
     *
     * @param persona
     */
    public void verItem(GperPersona persona) {
        listarTiposDePersona();
        personaBean.setPersonaActual(persona);
        personaBean.setVerDialog(true);
    }

    //</editor-fold> 
}
