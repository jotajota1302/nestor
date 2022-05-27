package com.ntt.es.springdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ntt.es.springdata.entity.EntityFour;
import com.ntt.es.springdata.entity.EntityOne;

@Repository
public interface EntityOneRepository extends JpaRepository<EntityOne,Integer> {
	
	  public List<EntityOne> findByEntityThreeField(String field);
	  
	  @Query(value = "from EntityFour")
	  public List<EntityFour> findPorMisSantosCojones();

}
