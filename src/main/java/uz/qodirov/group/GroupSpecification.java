package uz.qodirov.group;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class GroupSpecification implements Specification<GroupEntity> {
    private final String teacherId;

    public GroupSpecification(String teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public Predicate toPredicate(Root<GroupEntity> root, @Nullable CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(criteriaBuilder.equal(root.get("teacherId"), teacherId));
    }

}
