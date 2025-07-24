package edu.unl.cc.jbrew.bussiness.services;

import edu.unl.cc.jbrew.domain.security.ActionType;
import edu.unl.cc.jbrew.domain.security.Permission;
import edu.unl.cc.jbrew.domain.security.Role;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PermissionRepository {

    private static final Map<Long, Permission> tablePermissionBD;

    static {
        tablePermissionBD = new TreeMap<>();

        tablePermissionBD.put(1L, new Permission(1L, "/security/userList.xhtml", ActionType.ALL));
        tablePermissionBD.put(2L, new Permission(2L, "/security/userList.xhtml", ActionType.READ));
        tablePermissionBD.put(3L, new Permission(3L, "/security/userList.xhtml", ActionType.WRITE));
        tablePermissionBD.put(4L, new Permission(4L, "/security/userList.xhtml", ActionType.DELETE));
        tablePermissionBD.put(5L, new Permission(5L, "/security/userEdit.xhtml", ActionType.READ));

        tablePermissionBD.put(6L, new Permission(6L, "/cashier/orders.xhtml", ActionType.READ));
        tablePermissionBD.put(7L, new Permission(7L, "/cashier/orders.xhtml", ActionType.WRITE));
        tablePermissionBD.put(78L, new Permission(8L, "/cashier/ordersEdit.xhtml", ActionType.READ));

    }

    public static List<Permission> findAll(){
        return List.copyOf(tablePermissionBD.values());
    }

    public static Permission find(Long id){
        return tablePermissionBD.get(id);
    }


}
