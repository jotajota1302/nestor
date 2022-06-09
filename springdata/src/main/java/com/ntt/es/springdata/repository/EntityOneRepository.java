package com.ntt.es.springdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ntt.es.springdata.entity.EntityFour;
import com.ntt.es.springdata.entity.EntityOne;
import com.ntt.es.springdata.entity.EntityThree;

@Repository
public interface EntityOneRepository extends JpaRepository<EntityOne,Integer> {
	  
	  @Query
	  public List<EntityOne> findByFieldIgnoreCaseAndAttributeIgnoreCase(String field, String attribute);
	  
	  @Query
	  public List<EntityOne> findByEntityThreeField(String field);
	  
	  /*
	  @Query(
			  value = 
			  "SELECT one, three "
			  + "FROM EntityOne one INNER JOIN EntityThree three ON one.entityThree_id = three.id "
			  + "WHERE three.field = ?1"
			  )
	  public List<Object[]> findByEntityThreeFieldCustom(String field);
	  */
}
