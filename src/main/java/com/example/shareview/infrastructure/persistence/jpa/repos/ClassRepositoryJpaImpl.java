package com.example.shareview.infrastructure.persistence.jpa.repos;

import com.example.shareview.datasources.ClassDataSource;
import com.example.shareview.infrastructure.exceptions.ClassNotFoundJpaException;
import com.example.shareview.infrastructure.persistence.jpa.mappers.ClassJpaDtoMapper;
import com.example.shareview.infrastructure.persistence.jpa.mappers.UserJpaDtoMapper;
import com.example.shareview.infrastructure.persistence.jpa.models.ClassJpa;
import dtos.ClassDto;
import dtos.CourseDto;
import dtos.UserDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("jpa")
public class ClassRepositoryJpaImpl implements ClassDataSource {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ClassJpaDtoMapper classJpaDtoMapper;

    @Autowired
    private UserJpaDtoMapper userJpaDtoMapper;

    @Override
    @Transactional
    public ClassDto createClass(ClassDto classDto) {
        ClassJpa classJpa = classJpaDtoMapper.toClassJpa(classDto);
        classJpa = entityManager.merge(classJpa);
        return classJpaDtoMapper.toClassDto(classJpa);
    }

    @Override
    public Optional<ClassDto> findClassById(Long id) {
        Optional<ClassJpa> optionalClassJpa = Optional.ofNullable(entityManager.find(ClassJpa.class, id));
        return optionalClassJpa.map(classJpaDtoMapper::toClassDto);
    }

    @Override
    @Transactional
    public void addTeacher(ClassDto classDto, UserDto userDto) {
        ClassJpa classJpa = entityManager.find(ClassJpa.class, classDto.id());
        if (classJpa != null) {
            classJpa.addTeacher(userJpaDtoMapper.toUserJpa(userDto));
            entityManager.merge(classJpa);
        }
        else {
            throw new ClassNotFoundJpaException(classDto.id());
        }
    }

    @Override
    @Transactional
    public void addStudent(ClassDto classDto, UserDto userDto) {
        ClassJpa classJpa = entityManager.find(ClassJpa.class, classDto.id());
        if (classJpa != null) {
            classJpa.addStudent(userJpaDtoMapper.toUserJpa(userDto));
            entityManager.merge(classJpa);
        }
        else {
            throw new ClassNotFoundJpaException(classDto.id());
        }
    }

    @Override
    @Transactional
    public void removeTeacher(ClassDto classDto, UserDto userDto) {
        ClassJpa classJpa = entityManager.find(ClassJpa.class, classDto.id());
        if (classJpa != null) {
            classJpa.removeTeacher(userJpaDtoMapper.toUserJpa(userDto));
            entityManager.merge(classJpa);
        }
        else {
            throw new ClassNotFoundJpaException(classDto.id());
        }
    }

    @Override
    @Transactional
    public void removeStudent(ClassDto classDto, UserDto userDto) {
        ClassJpa classJpa = entityManager.find(ClassJpa.class, classDto.id());
        if (classJpa != null) {
            classJpa.removeStudent(userJpaDtoMapper.toUserJpa(userDto));
            entityManager.merge(classJpa);
        }
        else {
            throw new ClassNotFoundJpaException(classDto.id());
        }
    }

    @Override
    public List<ClassDto> findClassesFromStudent(UserDto userDto) {
        Query query = entityManager.createQuery("SELECT DISTINCT class " +
                "FROM ClassJpa class JOIN class.studentsJpa student " +
                "WHERE student.id = :studentId", ClassJpa.class);
        query.setParameter("studentId", userDto.id());
        List<ClassJpa> classJpaList = query.getResultList();
        return classJpaList.stream().map(classJpaDtoMapper::toClassDto).collect(Collectors.toList());
    }

    @Override
    public List<ClassDto> findClassesFromTeacher(UserDto userDto) {
        Query query = entityManager.createQuery("SELECT DISTINCT class " +
                                                    "FROM ClassJpa class JOIN class.teachersJpa teacher " +
                                                    "WHERE teacher.id = :teacherId", ClassJpa.class);
        query.setParameter("teacherId", userDto.id());
        List<ClassJpa> classJpaList = query.getResultList();
        return classJpaList.stream().map(classJpaDtoMapper::toClassDto).collect(Collectors.toList());
    }

    @Override
    public Long countClassesFromCourse(CourseDto courseDto) {
        Query query = entityManager.createQuery("SELECT count(*) FROM ClassJpa class WHERE class.courseJpa.id = :courseId");
        query.setParameter("courseId", courseDto.id());
        return (Long) query.getSingleResult();
    }

    @Override
    @Transactional
    public void deleteClass(ClassDto classDto) {
        Query query = entityManager.createQuery("DELETE FROM ClassJpa class WHERE class.id = :id");
        query.setParameter("id", classDto.id());
        query.executeUpdate();
    }
}
