package com.colsubsidio.salud.transversal.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 *
 * @author Usuario
 */
@Component
public class ProcessChain {

    private HttpServletRequest _request;

    /**
     *
     */
    public ProcessChain() {
    }

    /**
     *
     * @param _request
     */
    public ProcessChain(HttpServletRequest _request) {
        this._request = _request;
    }

    /**
     *
     * @param _request
     */
    public void setRequest(HttpServletRequest _request) {
        this._request = _request;
    }

    /**
     *
     * @param nombre
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getvariable(String nombre) throws UnsupportedEncodingException {
        String variable = "";
        if (!this.Compara(this.notNull(this._request.getParameter(nombre), ""), "")) {
            variable = new String(this.notEmpty(this._request.getParameter(nombre)).getBytes("UTF-8"), "UTF-8");
        }
        return variable;
    }

    /**
     *
     * @param nombre
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getAtributo(String nombre) throws UnsupportedEncodingException {
        String variable = "";
        if (!this.Compara(this.notNull(String.valueOf(this._request.getAttribute(nombre)), ""), "")) {
            variable = new String(this.notEmpty(String.valueOf(this._request.getAttribute(nombre))).getBytes("UTF-8"), "UTF-8");
        }
        return variable;
    }

    /**
     *
     * @param cadena
     * @return
     */
    public String armaCadena(String[] cadena) {
        String retorno = null;
        for (int a = 0; a < cadena.length; a++) {
            retorno = retorno + cadena[a] + ",";
        }
        retorno = retorno.substring(0, retorno.length() - 1);
        return retorno;
    }

    /**
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public String writter(String str) throws UnsupportedEncodingException {
        String stringbuffer = new String();
        if (str != null) {
            ProcessChainSpecials cadenas = new ProcessChainSpecials();
            stringbuffer = cadenas.quitarEspeciales(str);
        } else {
            stringbuffer = "NULO";
        }
        return stringbuffer;
    }

    /**
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public String notEmpty(String str) throws UnsupportedEncodingException {
        String retorno = "";
        if (str == null) {
            retorno = "";
        } else {
            retorno = str;
        }
        return retorno;
    }

    /**
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getParrafo(String str) throws UnsupportedEncodingException {
        String retorno = "";
        if (str == null) {
            retorno = "";
        } else {
            retorno = str;
        }
        retorno = "<p>" + retorno + "</p>";
        return retorno;
    }

    /**
     *
     * @param str
     * @param cambio
     * @return
     * @throws UnsupportedEncodingException
     */
    public String notEmpty(String str, String cambio) throws UnsupportedEncodingException {
        String retorno = "";
        if (str == null) {
            retorno = cambio;
        } else {
            retorno = str;
        }
        return retorno;
    }

    /**
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public String notNull(String str) throws UnsupportedEncodingException {
        String retorno = "";
        if (str == null) {
            retorno = "";
        } else {
            retorno = str;
        }
        return this.writter(retorno);
        //return retorno;
    }

    /**
     *
     * @param str
     * @param cambio
     * @return
     * @throws UnsupportedEncodingException
     */
    public String notNull(String str, String cambio) throws UnsupportedEncodingException {
        String retorno = "";
        if (str == null) {
            retorno = cambio;
        } else {
            retorno = str;
        }
        return this.writter(retorno);
        //return retorno;
    }

    /**
     *
     * @param number
     * @return
     */
    public String getNumber(Object number) {
        String str = String.valueOf(number);
        double value;
        String numberFormat = "###,###,###,###";
        DecimalFormat formatter = new DecimalFormat(numberFormat);
        try {
            value = Double.parseDouble(str);
        } catch (Throwable t) {
            return null;
        }
        return formatter.format(value);
    }

    /**
     *
     * @param number
     * @return
     */
    public String getNumberDecimales(Object number) {
        String str = String.valueOf(number);
        double value;
        String numberFormat = "###,###,###,###.##";
        DecimalFormat formatter = new DecimalFormat(numberFormat);
        try {
            value = Double.parseDouble(str);
        } catch (Throwable t) {
            return null;
        }
        return formatter.format(value);
    }

    /**
     *
     * @param number
     * @return
     */
    public String getDecimales(Object number) {
        String str = String.valueOf(number);
        double value;
        String numberFormat = "###,###,###,###.##";
        DecimalFormat formatter = new DecimalFormat(numberFormat);
        try {
            value = Double.parseDouble(str);
        } catch (Throwable t) {
            return null;
        }
        return formatter.format(value);
    }

    /**
     *
     * @param cadena
     * @return
     */
    public String ConstruirCadena(String[] cadena) {
        String texto = "";
        for (int a = 0; a < cadena.length; a++) {
            texto += cadena[a] + ",";
        }
        texto += "0";
        return texto;
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public boolean Compara(String a, String b) {
        boolean result = false;
        if (a.compareTo(b) == 0) {
            result = true;
        }
        return result;
    }

    /**
     *
     * @param cadena
     * @param longitud
     * @param caracter
     * @return
     */
    public String ArmarCadena(String cadena, int longitud, String caracter) {
        //cadena = quitaEspacios(cadena);
        String retorno = "";
        int i = longitud - cadena.length();
        int j = 0;
        while (j < i) {
            retorno = retorno + caracter;
            j++;
        }
        retorno = cadena + retorno;
        return retorno;
    }

    /**
     *
     * @param cadena
     * @param longitud
     * @param caracter
     * @return
     */
    public String CaracterIzq(String cadena, int longitud, String caracter) {
        //cadena = quitaEspacios(cadena);
        String retorno = "";
        int i = longitud - cadena.length();
        int j = 0;
        while (j < i) {
            retorno = retorno + caracter;
            j++;
        }
        retorno = retorno + cadena;
        return retorno;
    }

    /**
     *
     * @param str
     * @return
     */
    public static String addSlashes(String str) {
        if (str == null) {
            return "";
        }
        StringBuffer s = new StringBuffer((String) str);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\'') {
                s.insert(i++, '\\');
            }
        }
        return s.toString();
    }

    /**
     *
     * @param s
     * @param delimiter
     * @return
     */
    public static String implode(Collection s, String delimiter) {
        StringBuffer buffer = new StringBuffer();
        Iterator iter = s.iterator();
        while (iter.hasNext()) {
            buffer.append(iter.next());
            if (iter.hasNext()) {
                buffer.append(delimiter);
            }
        }
        return buffer.toString();
    }

    /**
     *
     * @param str
     * @param cambio
     * @return
     * @throws UnsupportedEncodingException
     */
    public String html(String str, String cambio) throws UnsupportedEncodingException {
        String retorno = "";
        if (str == null) {
            retorno = cambio;
        } else {
            retorno = str;
        }
        return retorno;
    }

    /**
     *
     * @param cadena
     * @param posicion
     * @param insert
     * @return
     */
    public String replaceString(String cadena, int posicion, String insert) {
        String cadenaAux = "";
        for (int i = 0; i < cadena.length(); i++) {
            if (i == posicion) {
                cadenaAux += insert;
                cadenaAux += cadena.charAt(i);
            } else {
                cadenaAux += cadena.charAt(i);
            }
        }
        return cadenaAux;
    }

    public static StringBuilder readChain(InputStreamReader inputStreamReader) throws IOException {

        BufferedReader br = new BufferedReader(inputStreamReader);
        StringBuilder result = new StringBuilder();
        while (br.ready()) {
            result.append(br.readLine());
        }
        return result;
    }
}
