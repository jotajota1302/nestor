package com.ntt.es.springdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ntt.es.springdata.entity.EntityOne;
import com.ntt.es.springdata.entity.EntityThree;
import com.ntt.es.springdata.repository.EntityFiveRepository;
import com.ntt.es.springdata.repository.EntityFourRepository;
import com.ntt.es.springdata.repository.EntityOneRepository;
import com.ntt.es.springdata.repository.EntityThreeRepository;

@SpringBootTest
public class QueryMethodTest {
	@Autowired
	EntityOneRepository repositoryEntityOne;
	
	@Autowired
	EntityThreeRepository repositoryEntityThree;
	
	@Autowired
	EntityFourRepository repositoryEntityFour;
	
	@Autowired
	EntityFiveRepository repositoryEntityFive;
	
	@Test
	@Transactional
	void testAndSingleTable() {
		repositoryEntityOne.save(EntityOne.builder().field("valor 1").attribute("atributo 1").build());
		repositoryEntityOne.save(EntityOne.builder().field("valor 2").attribute("atributo 2").build());
		repositoryEntityOne.save(EntityOne.builder().field("valor 3").attribute("atributo 3").build());
		
		List<EntityOne> result = repositoryEntityOne.findByFieldIgnoreCaseAndAttributeIgnoreCase("VALOR 2", "ATRIBUTO 2");
		
		assertThat(result.size() == 1);
	}
	
	@Test
	@Transactional
	void testMultipleTable() {
		EntityThree entityThree1 = repositoryEntityThree.save(EntityThree.builder().field("valor 1").build());
		EntityThree entityThree2 = repositoryEntityThree.save(EntityThree.builder().field("valor 2").build());
		
		repositoryEntityOne.save(EntityOne.builder().field("valor 1").attribute("atributo 1").entityThree(entityThree1).build());
		repositoryEntityOne.save(EntityOne.builder().field("valor 2").attribute("atributo 2").entityThree(entityThree1).build());
		repositoryEntityOne.save(EntityOne.builder().field("valor 3").attribute("atributo 3").entityThree(entityThree2).build());
		
		List<EntityOne> result = repositoryEntityOne.findByEntityThreeField("valor 1");
		System.out.println("size:" + result.size());
		System.out.println("position 0 EntityOne id: " +  result.get(0).getId() + " EntityThree id:" + result.get(0).getEntityThree().getId());
		System.out.println("position 1 EntityOne id: " +  result.get(1).getId() + " EntityThree id:" + result.get(1).getEntityThree().getId());
	}
	
	@Test
	@Transactional
	void testMultipleTableCustom() {
		EntityThree entityThree1 = repositoryEntityThree.save(EntityThree.builder().field("valor 1").build());
		EntityThree entityThree2 = repositoryEntityThree.save(EntityThree.builder().field("valor 2").build());
		
		repositoryEntityOne.save(EntityOne.builder().field("valor 1").attribute("atributo 1").entityThree(entityThree1).build());
		repositoryEntityOne.save(EntityOne.builder().field("valor 2").attribute("atributo 2").entityThree(entityThree1).build());
		repositoryEntityOne.save(EntityOne.builder().field("valor 3").attribute("atributo 3").entityThree(entityThree2).build());
		
		List<Object[]> result = repositoryEntityOne.findByEntityThreeFieldCustom("valor 1");
		
		System.out.println(result.size());
		
		for(Object[] row : result) {
			EntityOne eOne = (EntityOne)row[0];
			EntityThree eThree = (EntityThree)row[1];
			
			System.out.println("eOne id: " + eOne.getId());
			System.out.println("eThree id: " + eThree.getId());
		}
	}
}
