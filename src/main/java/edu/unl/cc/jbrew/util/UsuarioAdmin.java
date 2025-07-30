package edu.unl.cc.jbrew.util;

import edu.unl.cc.jbrew.bussiness.services.RoleRepository;
import edu.unl.cc.jbrew.bussiness.services.UserRepository;
import edu.unl.cc.jbrew.domain.common.Person;
import edu.unl.cc.jbrew.domain.security.Role;
import edu.unl.cc.jbrew.domain.security.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@Named("usuarioAdmin")
public class UsuarioAdmin {

    private static final Logger LOGGER = Logger.getLogger(UsuarioAdmin.class.getName());

    @Inject
    private UserRepository userRepository;

    @Inject
    private RoleRepository roleRepository;

    private boolean adminCreated = false;

    public String createAdminIfNotExistsAndRedirect() {
        createAdminIfNotExists();
        return "login.xhtml?faces-redirect=true";
    }

    public void createAdminIfNotExists() {
        if (adminCreated) {
            return; // ya creado
        }

        try {
            User existing = userRepository.findByName("admin");
            if (existing == null) {
                Role adminRole = roleRepository.findByName("ADMIN");
                if (adminRole == null) {
                    adminRole = new Role();
                    adminRole.setName("ADMIN");
                    roleRepository.save(adminRole);
                    LOGGER.info("Rol ADMIN creado.");
                }

                User admin = new User();
                admin.setName("admin");

                // Encriptar contraseña
                String plainPassword = "admin1234"; // tu password en claro
                String encryptedPassword = EncryptorManager.encrypt(plainPassword);
                admin.setPassword(encryptedPassword);

                admin.setRole(adminRole);

                // Crear y asignar Person con campos obligatorios
                Person adminPerson = new Person();
                adminPerson.setNombres("ADMIN");
                adminPerson.setApellidos("ISTRADOR");
                adminPerson.setEmail("admin@vitalnet.com"); // opcional

                admin.setPersona(adminPerson);

                userRepository.save(admin);
                LOGGER.info("Administrador creado");
                LOGGER.info("Usuario: 'admin'; Contraseña en claro: '" + plainPassword + "'");
            } else {
                LOGGER.info("Usuario admin ya existe");
            }
            adminCreated = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al crear admin", e);
            // No lances excepción para no interrumpir flujo, solo loguea
        }
    }

}

