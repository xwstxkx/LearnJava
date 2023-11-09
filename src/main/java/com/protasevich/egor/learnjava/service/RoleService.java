package com.protasevich.egor.learnjava.service;

import com.protasevich.egor.learnjava.entity.RoleEntity;
import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import com.protasevich.egor.learnjava.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public RoleEntity getUserRole() throws ObjectNotFound {
        return repository.findByName("ROLE_USER").orElseThrow(ObjectNotFound::new);
    }
}
