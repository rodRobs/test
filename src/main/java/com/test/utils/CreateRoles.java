package com.test.utils;

import com.test.roles.entity.Rol;
import com.test.roles.enums.NombreRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.test.roles.dto.RolDTO;

@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    GenericCrudService<RolDTO, Integer> rolService;

    @Override
    public void run(String... args) throws Exception {

        RolDTO rolUser = new RolDTO(NombreRol.ROLE_USER);
        rolService.save(rolUser);

    }

}