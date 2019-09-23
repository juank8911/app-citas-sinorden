package com.colsubsidio.salud.portal.dao.interfaces;

/**
 *
 * @author Usuario
 */
public interface ILogsDAO {

    /**
     *
     * @param action
     * @param message
     * @return
     */
    int createLog(String action, String message);
}
