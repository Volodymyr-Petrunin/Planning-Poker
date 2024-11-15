package planing.poker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import planing.poker.domain.RoomUserRole;
import planing.poker.repository.RoomUserRoleRepository;

import java.util.List;

@Service
@Transactional
public class RoomUserRoleService {

    private final RoomUserRoleRepository repository;

    @Autowired
    public RoomUserRoleService(final RoomUserRoleRepository repository) {
        this.repository = repository;
    }

    public List<RoomUserRole> createSeveralRoles(final List<RoomUserRole> roles) {
        return repository.saveAll(roles);
    }

}
