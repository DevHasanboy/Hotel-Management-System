package com.example.file.task.filter;


import com.example.file.task.entity.Room;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class RoomFilter implements Specification<Room> {

    private int rowNumber;

    public void setRowNumber(int rowNumber) {
        if (rowNumber!= 0){
            this.rowNumber = rowNumber;
        }
    }

    @Override
    public Predicate toPredicate(Root<Room> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (rowNumber > 0){
            predicates.add(criteriaBuilder.equal(root.get("rowNumber"), rowNumber));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
