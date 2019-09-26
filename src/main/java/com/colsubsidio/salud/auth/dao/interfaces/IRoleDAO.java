package com.colsubsidio.salud.auth.dao.interfaces;

import com.colsubsidio.salud.auth.models.Role;
import java.util.List;

public interface IRoleDAO {
    List<Role> getRoles();
    List<Role> getRolesByUser(String id);
}
