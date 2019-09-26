package com.colsubsidio.salud.auth.controllers;

import com.colsubsidio.salud.auth.dao.interfaces.IRoleDAO;
import com.colsubsidio.salud.auth.models.Role;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "*")
public class RoleController {
    @Autowired
    private IRoleDAO roleDAO;
    
    /**
     * End point that returns the list of Roles
     * @return List Role
     */
    @GetMapping()
    public ResponseEntity<List<Role>> getUses(
    ){
        List<Role> list = roleDAO.getRoles();
        return new ResponseEntity(list, HttpStatus.OK);
    }
}