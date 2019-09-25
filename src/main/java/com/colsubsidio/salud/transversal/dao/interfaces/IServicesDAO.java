package com.colsubsidio.salud.transversal.dao.interfaces;

import java.util.List;

import com.colsubsidio.salud.transversal.models.Services;

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
    Services getServicioByName(String nombre);

    /**
     *
     * @return
     */
    List<Services> getServicios();
}
