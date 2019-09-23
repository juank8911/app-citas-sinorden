package com.colsubsidio.salud.portal.common;

import java.util.logging.Logger;

public abstract interface CommonConstantsDB {

    public static final String PR_COLSSALUD_I_AUTH = "sp.db.insert.auth";
    public static final String PR_COLSSALUD_I_AUTH_HABEASDATA = "sp.db.insert.auth.habeasdata";
    
    
    public static final String PR_COLSSALUD_S_CITIES_DEPARTMENTS = "sp.db.select.cities.departments";
    public static final String PR_COLSSALUD_S_DEPARTMENTS = "sp.db.select.departments";
    public static final String PR_COLSSALUD_S_DOCUMENTS_TYPE = "sp.db.select.documents.type";

    public static final String PR_COLSSALUD_D_OPENAM_USERS = "sp.db.delete.openam.users";
    
    public static final String PR_COLSSALUD_S_CITIES_LABORATORIES = "sp.db.select.cities.laboratories";

    public static final String PR_COLSSALUD_SCHEDULE_I_RESERVATION = "sp.db.insert.schedule";
    public static final String PR_COLSSALUD_SCHEDULE_U_RESERVATION = "sp.db.update.schedule";
    public static final String PR_COLSSALUD_SCHEDULE_S_RESERVATION = "sp.db.select.schedule";
}
