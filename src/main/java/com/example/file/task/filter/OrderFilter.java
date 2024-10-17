package com.example.file.task.filter;

import com.example.file.task.entity.Order;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OrderFilter implements Specification<Order> {

    private  int numberOfPeople;

    public void setNumberOfPeople(int numberOfPeople) {
        if (numberOfPeople == 0) {
            this.numberOfPeople = numberOfPeople;
        }
    }
    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (numberOfPeople > 0) {
            predicates.add(criteriaBuilder.equal(root.get("numberOfPeople"), numberOfPeople));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
