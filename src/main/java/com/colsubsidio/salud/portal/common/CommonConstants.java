package com.colsubsidio.salud.portal.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 */
public interface CommonConstants {

    /**
     *
     */
    public static Logger LOG = LoggerFactory.getLogger("logApplication");

    public static final String ERROR_SERVICE_PROCESS_CODE = "error_service_process_code";

    public static final String SUCCESSFUL_PROCESS = "success.process";
    public static final String ERRROR_PROCESS = "service_error_dataaccess_general";
    public static final String ERROR_SERVICE_PROCESS = "error_service_dataaccess_general";

    public static final String SUCCESSFUL_PROCESS_CODE = "100";
    public static final String ERROR_PROCESS_CODE = "900";

    public static final String STATUS_ERROR_CANCEL = "Error en el proceso";
    public static final String PROCESO_EXITOSO = "Proceso terminado con exito";
    public static final String PROCESO_ERROR = "No se encontraron resultados para su busqueda";
    public static final String CREACION_EXITOSA = " Se creo el registro correctamente";
    public static final String CREACION_ERROR = "Se genero un problema en la creacion del registro";
    public static final String CALIFICACION_EXITOSA = "La calificacion a su pedido se envio exitosamente";
    public static final String CANCELACION_EXITOSA = "El pedido se cancelo exitosamente";
    public static final String MODIFICACION_EXITOSA = "Se modifico exitosamente";
    public static final String UPDATE_ERROR = "Se presento un error enviando la calificacion";
    public static final String CANCEL_ERROR = "Se presento un error cancelando";
    public static final String UPDATE_ERROR_GENERAL = "Se presento un error modificando";
    public static final String INFORMATION_ORDER_ERROR = "Falta informacion para realizar el proceso";

    public static final String SERVICE_WEB = "service.web";
    public static final String SERVICE_WEB_CONTENT_TYPE = "service.web.contenttype";
    public static final String SERVICE_WEB_CONTENT_TYPE_FORM_VALUE = "service.web.contenttype.form.value";
    public static final String SERVICE_WEB_CONTENT_TYPE_JSON_VALUE = "service.web.contenttype.json.value";
    public static final String SERVICE_WEB_AUTHORIZATION = "service.web.authorization";
    public static final String SERVICE_WEB_TOKEN_BEARER = "service.web.token.bearer";
    public static final String SERVICE_WEB_ACCEPT = "service.web.accept";
    public static final String SERVICE_WEB_ACCEPT_VALUE = "service.web.accept.value";
    public static final String SERVICE_WEB_UTF8 = "service.web.utf8";
    public static final String SERVICE_WEB_XUSERNAME = "service.web.xusername";
    public static final String SERVICE_WEB_XTOKENID = "service.web.xtokenid";
    public static final String SERVICE_WEB_XPASSWORD = "service.web.xpassword";
    public static final String SERVICE_WEB_IPLANEDIRECTORYPRO = "service.web.iplanetDirectoryPro";
    public static final String EXPIRATION_AUTHORIZATION = "jwt.expiration.authorization";
    public static final String EXPIRATION_REFRESH_TOKEN = "jwt.expiration.refreshToken";
    
    public static final String SERVICE_LABORATORIE_IMAGEN_RIGHT = "service.laboratories.imagen.right";
    public static final String SERVICE_LABORATORIE_IMAGEN_LEFT = "service.laboratories.imagen.left";
    public static final String SERVICE_LABORATORIE_IMAGEN_RIGHTDOWN = "service.laboratories.imagen.rightdown";

}
