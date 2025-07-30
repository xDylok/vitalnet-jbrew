package edu.unl.cc.jbrew.bussiness;

import edu.unl.cc.jbrew.bussiness.services.RoleRepository;
import edu.unl.cc.jbrew.bussiness.services.UserRepository;
import edu.unl.cc.jbrew.domain.security.Role;
import edu.unl.cc.jbrew.domain.security.User;
import edu.unl.cc.jbrew.exception.CredentialInvalidException;
import edu.unl.cc.jbrew.exception.EncryptorException;
import edu.unl.cc.jbrew.util.EncryptorManager;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import edu.unl.cc.jbrew.exception.EntityNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Stateless
public class SecurityFacade implements java.io.Serializable{
    Logger logger = Logger.getLogger(SecurityFacade.class.getName());
//agrupa lógica relacionada con seguridad, autenticación/autorización.
    @Inject
    private UserRepository userRepository;

    @Inject
    private RoleRepository roleRepository;

    public User findUserWithRoleAndPermissions(Long userId) {
        return userRepository.buscarRolUsuarioConPermisos(userId);
    }

    public void deleteUser(User user) throws Exception {
        try {
            userRepository.delete(user);
            logger.info("Usuario eliminado: " + user.getName());
        } catch (Exception e) {
            logger.severe("Error eliminando usuario: " + e.getMessage());
            throw new Exception("No se pudo eliminar el usuario");
        }
    }

    public User create(User user) throws Exception {
        String pwdEncrypted = EncryptorManager.encrypt(user.getPassword());
        user.setPassword(pwdEncrypted);
        try {
            userRepository.find(user.getName());
        } catch (EntityNotFoundException e){
            User userPersisted = userRepository.save(user);
            return userPersisted;
        }
        throw new Exception("Ya existe un usuario con ese nombre");
    }
    public Role getRoleById(Long id) {
        return roleRepository.buscarPorId(id);
    }
    public User update(User user) throws Exception {
        if (user.getId() == null){
            return create(user);
        }
        String pwdEncrypted = EncryptorManager.encrypt(user.getPassword());
        user.setPassword(pwdEncrypted);
        try {
            User userFound = userRepository.find(user.getName());
            if  (!userFound.getId().equals(user.getId())){
                throw new Exception("Ya existe otro usuario con ese nombre");
            }
        } catch (EntityNotFoundException ignored) {
        }
        return userRepository.save(user);
    }

    public User find(Long id) throws EntityNotFoundException {
        return userRepository.find(id);
    }

    public User authenticate(String name, String password) throws CredentialInvalidException, EncryptorException {
        try{
            User userFound = userRepository.find(name);
            String pwdEncrypted = EncryptorManager.encrypt(password);
            if (pwdEncrypted.equals(userFound.getPassword())){
                return userFound;
            }
            throw new CredentialInvalidException();
        } catch (EntityNotFoundException e){
            throw new CredentialInvalidException();
        }
    }

    public List<User> findUsers(String criteria) throws EntityNotFoundException {
        return userRepository.buscarPorCriterio(criteria);
    }

    public Set<Role> findAllRolesWithPermission()  {
        return roleRepository.buscarTodosConPermisos();
    }


}
