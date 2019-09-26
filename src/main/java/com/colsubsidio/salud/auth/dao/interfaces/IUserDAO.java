package com.colsubsidio.salud.auth.dao.interfaces;

import com.colsubsidio.salud.auth.models.User;
import java.util.List;

public interface IUserDAO {
    User getUserByUserName(String username);
}
