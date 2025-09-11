package uz.qodirov.teacher;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import uz.qodirov.constant.Role;
import uz.qodirov.user.UserEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class TeacherSpecification implements Specification<UserEntity> {
    private final Role role;

    public TeacherSpecification(Role role) {
        this.role = role;
    }

    @Override
    public Predicate toPredicate(Root<UserEntity> root, @Nullable CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("role"), role));
    }
}
