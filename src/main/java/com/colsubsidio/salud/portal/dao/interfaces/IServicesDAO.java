package com.colsubsidio.salud.portal.dao.interfaces;

import com.colsubsidio.salud.portal.models.Services;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface IServicesDAO {

    Services getServicioByName(String nombre);

}
