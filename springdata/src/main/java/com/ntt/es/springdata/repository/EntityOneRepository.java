package com.ntt.es.springdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ntt.es.springdata.entity.EntityOne;

@Repository
public interface EntityOneRepository extends JpaRepository<EntityOne,Integer> {

}
