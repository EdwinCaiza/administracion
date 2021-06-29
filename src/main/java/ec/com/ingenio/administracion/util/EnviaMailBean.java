/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.util;

import java.util.Date;
import java.util.Properties;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author hacker
 */
@Named
@Stateful
@SessionScoped
public class EnviaMailBean {

    /**
     * DECLARACION DE VARIABLES
     *
     */
    private String servidorSMTP;//Servidor SMTP para obtener conexion con el mail
    private String correoRem;//Usuario del correo 
    private String contrasennaRem;//El password de la cuenta de usuario
    private String puerto;//El puerto que utiliza el correo email

    /**
     * DESTINATARIO
     */
    private String correoDestino;
    private String asunto;
    private String mensaje;
// <editor-fold defaultstate="collapsed" desc="Métodos Principales">

    public boolean enviarCorreo(String correoDestino, String persona, String usuario, String dato) {
        try {
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", "smtp.gmail.com");
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", "587");
            propiedades.setProperty("mail.smtp.auth", "true");

            /* PREPARAR LA SESION -> ENVIO PROPIEDADES*/
            Session session = Session.getDefaultInstance(propiedades);

            setCorreoRem("muebleingenio@gmail.com");
            setContrasennaRem("miaLEGRIAS.01");
            setCorreoDestino(correoDestino);
            setAsunto("Correo enviado JAVA");
            setMensaje("<h2 style=\"text-align: center;\"><span style=\"color: #0000ff;\">Bienvenido a INGENIO.COM</span></h2>\n"
                    + "<p><strong><span style=\"text-decoration: underline; color: #00ff00;\"><span style=\"color: #3366ff; text-decoration: underline;\">Correo generado automaticamente por: " + persona + " </span> </span></strong></p>\n"
                    + "<p><strong><span style=\"color: #3366ff;\">Hora local: " + new Date() + "</span></strong></p>\n"
                    + "<p>Su solicitud de Generaci&oacute;n de Contrase&ntilde;a temporal se ejecuto correctamente:</p>\n"
                    + "<p>Utilice esta clave para acceder a su cuenta en nuestra pagina:</p>\n"
                    + "<p><strong>Usuario: " + usuario + "</strong></p>\n"
                    + "<p><strong>Contrase&ntilde;a: " + dato + "</strong></p>");

            MimeMessage mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(getCorreoRem()));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(getCorreoDestino()));
            mensaje.addRecipient(Message.RecipientType.BCC, new InternetAddress("edwinfc20@yahoo.com"));
            //mensaje.addRecipient(Message.RecipientType.BCC, new InternetAddress(getCorreoDestino())); // c ENVIO CON COPIA SIN QUE EL RESTO DESTRINATARIOS LO VEA
            mensaje.setSubject(getAsunto());
            mensaje.setText(getMensaje(), "ISO-8859-1", "html");

            //*envio del correo*/
            Transport transport = session.getTransport("smtp");
            transport.connect(getCorreoRem(), getContrasennaRem());
            transport.sendMessage(mensaje, mensaje.getRecipients(Message.RecipientType.TO));
            //transport.sendMessage(mensaje, mensaje.getRecipients(Message.RecipientType.BCC));
            transport.close();

            return true;
        } catch (MessagingException e) {
            System.err.println("Error al procesar enviarCorreo de " + getClass().getName() + ". Causado por: " + e.getMessage());
            return false;
        }
    }
//</editor-fold> 
// <editor-fold defaultstate="collapsed" desc="Getter and setter">

    /**
     * @return the correoDestino
     */
    public String getCorreoDestino() {
        return correoDestino;
    }

    /**
     * @param correoDestino the correoDestino to set
     */
    public void setCorreoDestino(String correoDestino) {
        this.correoDestino = correoDestino;
    }

    /**
     * @return the servidorSMTP
     */
    public String getServidorSMTP() {
        return servidorSMTP;
    }

    /**
     * @param servidorSMTP the servidorSMTP to set
     */
    public void setServidorSMTP(String servidorSMTP) {
        this.servidorSMTP = servidorSMTP;
    }

    /**
     * @return the puerto
     */
    public String getPuerto() {
        return puerto;
    }

    /**
     * @param puerto the puerto to set
     */
    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    /**
     * @return the contrasennaRem
     */
    public String getContrasennaRem() {
        return contrasennaRem;
    }

    /**
     * @param contrasennaRem the contrasennaRem to set
     */
    public void setContrasennaRem(String contrasennaRem) {
        this.contrasennaRem = contrasennaRem;
    }

    /**
     * @return the asunto
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * @param asunto the asunto to set
     */
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the correoRem
     */
    public String getCorreoRem() {
        return correoRem;
    }

    /**
     * @param correoRem the correoRem to set
     */
    public void setCorreoRem(String correoRem) {
        this.correoRem = correoRem;
    }
//</editor-fold> 

}
