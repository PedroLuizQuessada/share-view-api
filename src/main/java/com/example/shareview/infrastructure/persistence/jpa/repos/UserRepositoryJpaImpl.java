package com.example.shareview.infrastructure.persistence.jpa.repos;

import com.example.shareview.datasources.UserDataSource;
import com.example.shareview.infrastructure.persistence.jpa.mappers.UserJpaDtoMapper;
import com.example.shareview.infrastructure.persistence.jpa.models.UserJpa;
import dtos.UserDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Profile("jpa")
public class UserRepositoryJpaImpl implements UserDataSource {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserJpaDtoMapper userJpaDtoMapper;

    @Override
    public Long countByEmail(String email) {
        Query query = entityManager.createQuery("SELECT count(*) FROM UserJpa user WHERE user.email = :email");
        query.setParameter("email", email);
        return (Long) query.getSingleResult();
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        UserJpa userJpa = userJpaDtoMapper.toUserJpa(userDto);
        userJpa = entityManager.merge(userJpa);
        return userJpaDtoMapper.toUserDto(userJpa);
    }

    @Override
    public Optional<UserDto> findUserByEmail(String email) {
        Query query = entityManager.createQuery("SELECT user FROM UserJpa user WHERE user.email = :email");
        query.setParameter("email", email);
        try {
            UserJpa userJpa = (UserJpa) query.getSingleResult();
            return Optional.ofNullable(userJpaDtoMapper.toUserDto(userJpa));
        }
        catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserDto> findUserById(Long id) {
        Optional<UserJpa> optionalUserJpa = Optional.ofNullable(entityManager.find(UserJpa.class, id));
        return optionalUserJpa.map(userJpaDtoMapper::toUserDto);
    }

    @Override
    @Transactional
    public void updateUserEmailById(Long id, String email) {
        Query query = entityManager.createQuery("UPDATE UserJpa user SET user.email = :email WHERE user.id = :id");
        query.setParameter("id", id);
        query.setParameter("email", email);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void updateUserPasswordById(Long id, String password) {
        Query query = entityManager.createQuery("UPDATE UserJpa user SET user.password = :password WHERE user.id = :id");
        query.setParameter("id", id);
        query.setParameter("password", password);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void deleteUser(UserDto userDto) {
        Query query = entityManager.createQuery("DELETE FROM UserJpa user WHERE user.id = :id");
        query.setParameter("id", userDto.id());
        query.executeUpdate();
    }
}
