/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.action;

import ec.com.ingenio.administracion.bean.PerfilBean;
import ec.com.ingenio.administracion.bean.PerfilUsuarioBean;
import ec.com.ingenio.administracion.bean.UsuarioBean;
import ec.com.ingenio.administracion.util.UtilBean;
import ec.com.ingenio.ingenioconta.modelo.SpeuPerfilusuario;
import ec.com.ingenio.ingenioconta.remoto.SpesPerfilFacadeRemoto;
import ec.com.ingenio.ingenioconta.remoto.SpeuPerfilusuarioFacadeRemoto;
import ec.com.ingenio.ingenioconta.remoto.SusiUsuariosistemaFacadeRemoto;
import java.util.Date;
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
public class PerfilUsuarioAction {

    /**
     * DECLARACIÓN DE VARIABLES
     */
    @EJB(lookup = "java:global/IngenioConta/SpeuPerfilusuarioFacade!ec.com.ingenio.ingenioconta.remoto.SpeuPerfilusuarioFacadeRemoto")
    private SpeuPerfilusuarioFacadeRemoto perfilUsuarioRemoto;
    @EJB(lookup = "java:global/IngenioConta/SusiUsuariosistemaFacade!ec.com.ingenio.ingenioconta.remoto.SusiUsuariosistemaFacadeRemoto")
    private SusiUsuariosistemaFacadeRemoto usuarioSistemaRemoto;
    @EJB(lookup = "java:global/IngenioConta/SpesPerfilFacade!ec.com.ingenio.ingenioconta.remoto.SpesPerfilFacadeRemoto")
    private SpesPerfilFacadeRemoto perfilFacade;
    @Inject
    PerfilUsuarioBean perfilUsuarioBean;
    @Inject
    PerfilBean perfilBean;
    @Inject
    UsuarioBean usuarioSistemaBean;
    @Inject
    UtilBean utilBean;

// <editor-fold defaultstate="collapsed" desc="Métodos Principales">
    /**
     * Método para Inicializar variables
     */
    public void init() {
        listarPerfilUsuario();
        listarPerfiles();
        /**
         * ojo mejorar este metodo por motivo que carga todos los Usuarios en
         * una sola lista, optimizar para realizar busqueda por Persona
         */
        listarUsuarios();
    }

    /**
     * Método para borrar registro
     * @param perfilUsuario
     */
    public void borrarRegistro(SpeuPerfilusuario perfilUsuario){
        try {
            perfilUsuarioRemoto.remove(perfilUsuario);
            utilBean.mostrarMensaje(false, 2, "Exito", "Registro borrado correctamente");
            init();
        } catch (Exception e) {
             System.err.println("Error al procesar borrarRegistro de " + getClass().getName() + ". Causado por: " + e.getMessage());
             utilBean.mostrarMensaje(false, 1, "Advertencia", "Registro No de pudo borrar");
        }
    }
    /**
     * Método para cancelar Acción
     */
    public void cancelar() {
        perfilUsuarioBean.setVerDialog(false);
    }

    /**
     * Método para crear Nuevo Itrem
     */
    public void crearNuevo() {
        perfilUsuarioBean.setPerfilUsuarioActial(new SpeuPerfilusuario());
        perfilUsuarioBean.setVerDialog(true);

    }

    /**
     * Método para Guardar Información
     */
    public void guardarInformacion() {
        try {
            if (perfilUsuarioBean.getPerfilUsuarioActial().getPeuCodigo() == null) {
                perfilUsuarioBean.getPerfilUsuarioActial().setPeuFechaasigna(new Date());
                perfilUsuarioBean.getPerfilUsuarioActial().setPeuEstado('A');
                perfilUsuarioBean.getPerfilUsuarioActial().setPeuBorradologico(true);
                perfilUsuarioRemoto.create(perfilUsuarioBean.getPerfilUsuarioActial());
                utilBean.mostrarMensaje(false, 2,"Exito", "Información registrada con exito");
            } else {
                perfilUsuarioRemoto.edit(perfilUsuarioBean.getPerfilUsuarioActial());
                utilBean.mostrarMensaje(false, 2,"Exito", "Información modificada con exito");
            }
            perfilUsuarioBean.setPerfilUsuarioActial(new SpeuPerfilusuario());
            init();
            perfilUsuarioBean.setVerDialog(false);
        } catch (Exception e) {
            System.err.println("Error al procesar guardarInformacion de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para cargar lista de Perfiles de Usuario
     */
    public void listarPerfiles() {
        perfilBean.setListaPerfiles(perfilFacade.listarPerfiles());
    }

    /**
     * Método para cargar lista de Perfiles de Usuario
     */
    public void listarPerfilUsuario() {
        try {
            perfilUsuarioBean.setListaPerfilUsuario(perfilUsuarioRemoto.listarPerfilUsuario());
        } catch (Exception e) {
            System.err.println("Error al procesar listarPerfilUsuario de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para cargar lista de Usuarios
     */
    public void listarUsuarios() {
        try {
            usuarioSistemaBean.setListaUsuarios(usuarioSistemaRemoto.listarUsuarios());
        } catch (Exception e) {
            System.err.println("Error al procesar listarUsuarios de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

    /**
     * Método para cargar seleccionar Pefil Usuario
     */
    public void deSelecionaPerfilUsuario() {
        perfilUsuarioBean.setVerDialog(false);
    }

    /**
     * Método para cargar seleccionar Pefil Usuario
     */
    public void selecionaPerfilUsuario() {
        try {
            listarPerfiles();
            perfilUsuarioBean.setVerDialog(true);
        } catch (Exception e) {
            System.err.println("Error al procesar selecionaPerfilUsuario de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }

    }

    /**
     * Método para validar existencia de Usario ya asignado
     */
    public void validaUsiarioAsignado() {
        try {
            perfilUsuarioBean.setActivaGuardar(true);
            if (perfilUsuarioRemoto.retornaPerfilUsuario(perfilUsuarioBean.getPerfilUsuarioActial().getUsiCodigo().getUsiCodigo()) == null) {
                perfilUsuarioBean.setActivaGuardar(false);
            } else {
                utilBean.mostrarMensaje(false, 1, "Advertencia", "Usuario ya tiene perfil asignado");
            }
        } catch (Exception e) {
            System.err.println("Error al procesar validaUsiarioAsignado de " + getClass().getName() + ". Causado por: " + e.getMessage());
        }
    }

//</editor-fold> 
}
