package ru.homework.javacourse.services.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.homework.javacourse.data.RoleRepository;
import ru.homework.javacourse.models.Role;
import ru.homework.javacourse.models.RoleName;
import ru.homework.javacourse.models.User;
import ru.homework.javacourse.services.SecurityService;
import ru.homework.javacourse.services.TemplateHelper;

@Service
public class TemplateHelperImpl implements TemplateHelper {

    private final SecurityService securityService;
    private final Role adminRole;

    @Autowired
    public TemplateHelperImpl(SecurityService securityService, RoleRepository roleRepository) {
        this.securityService = securityService;
        adminRole = roleRepository.findByName(RoleName.ADMIN.name);
    }

    @Override
    public boolean isLoggedIn() {
        return securityService.findLoggedInUsername() != null;
    }

    @Override
    public boolean isAdmin() {
        User loggedInUser = securityService.findLoggedInUser();
        if (loggedInUser != null && loggedInUser.getRoles() != null) {
            return loggedInUser.getRoles().contains(adminRole);
        } else {
            return false;
        }
    }

    @Override
    public boolean isAdmin(User user) {
        return user.getRoles().contains(adminRole);
    }

    @Override
    public String getUsername() {
        return securityService.findLoggedInUsername();
    }

    @Override
    public String removeSpaces(String fieldName) {
        return fieldName.replaceAll("\\s+","");
    }
}
