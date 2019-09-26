package com.colsubsidio.salud.auth.dao.interfaces;

import com.colsubsidio.salud.auth.models.Service;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface IServicesDAO {

    /**
     *
     * @param nombre
     * @return
     */
    Service getServicioByName(String nombre);

    /**
     *
     * @return
     */
    List<Service> getServicios();
}
