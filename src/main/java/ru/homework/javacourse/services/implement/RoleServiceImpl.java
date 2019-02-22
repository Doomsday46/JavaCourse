package ru.homework.javacourse.services.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.homework.javacourse.data.RoleRepository;
import ru.homework.javacourse.data.UserRepository;
import ru.homework.javacourse.models.Role;
import ru.homework.javacourse.models.User;
import ru.homework.javacourse.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public void createRole(String name) {
        if (roleRepository.findByName(name) == null) {
            Role role = new Role();
            role.setName(name);
            roleRepository.save(role);
        }
    }

    @Override
    public void deleteRole(String name) {
        Role role = roleRepository.findByName(name);
        if (role != null) {
            roleRepository.delete(role);
        }
    }

    @Override
    public void addRoleToUser(Role role, User user) {
        user.getRoles().add(role);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void removeRoleFromUser(Role role, User user) {
        user.getRoles().remove(role);
        userRepository.saveAndFlush(user);
    }
}
