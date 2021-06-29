/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.util;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;

/**
 *
 * @author E.C
 */
public class EncriptMD5 {

    /**
     * Método para encriptar MD5
     *
     * @param code tipo contraseña
     * @return una contraseña encriptada
     */
    public static String encryptMD5(String code) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] input = code.getBytes(); //"UTF8");
            input = md.digest(input);
            code = toHexadecimal(input); //new String(input,"UTF8");
            return code;
        } catch (Exception e) {
            System.err.println("Error al procesar " + e.getMessage() );
            return code;
        }

    }

    /**
     * Metodo para convertir un arreglo de bytes en un numero hexadeciman
     *
     */
    private static String toHexadecimal(byte[] datos) {
        String resultado = "";
        ByteArrayInputStream input = new ByteArrayInputStream(datos);
        String cadAux;
        boolean ult0 = false;
        int leido = input.read();
        while (leido != -1) {
            cadAux = Integer.toHexString(leido);
            if (cadAux.length() < 2) { //Hay que aÒadir un 0
                resultado += "0";
                if (cadAux.length() == 0) {
                    ult0 = true;
                }
            } else {
                ult0 = false;
            }
            resultado += cadAux;
            leido = input.read();
        }
        if (ult0)//quitamos el 0 si es un caracter aislado
        {
            resultado
                    = resultado.substring(0, resultado.length() - 2) + resultado.charAt(resultado.length() - 1);
        }
        return resultado;
    }
}
