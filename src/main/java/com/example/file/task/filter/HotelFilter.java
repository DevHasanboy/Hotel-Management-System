package com.example.file.task.filter;


import com.example.file.task.entity.Hotel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class HotelFilter implements Specification<Hotel> {

    private String name;
    private String location;
    private String address;


    public void setName(String name){
        if(name != null && !name.isEmpty()){
            this.name = name;
        }
    }

    public void setLocation(String location){
        if(location != null && !location.isEmpty()){
            this.location = location;
        }
    }
    public void setAddress(String address){
        if(address != null && !address.isEmpty()){
            this.address = address;
        }
    }

    @Override
    public Predicate toPredicate(Root<Hotel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
       List<Predicate> predicates = new ArrayList<>();
       if(name != null && !name.isEmpty()){
           predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%"+name.toLowerCase()+"%"));
       }
       if(location != null && !location.isEmpty()){

           predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("location")), "%"+location.toLowerCase()+"%"));
       }
       if(address != null && !address.isEmpty()){
           predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%"+address.toLowerCase()+"%"));
       }
       return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
