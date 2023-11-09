package com.protasevich.egor.learn.service;

import com.protasevich.egor.learn.entity.RoleEntity;
import com.protasevich.egor.learn.exceptions.ObjectNotFound;
import com.protasevich.egor.learn.repository.RoleRepository;
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
