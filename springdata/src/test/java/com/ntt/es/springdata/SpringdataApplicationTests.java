package com.ntt.es.springdata;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ntt.es.springdata.entity.EntityFive;
import com.ntt.es.springdata.entity.EntityFour;
import com.ntt.es.springdata.entity.EntityOne;
import com.ntt.es.springdata.entity.EntityThree;
import com.ntt.es.springdata.repository.EntityFiveRepository;
import com.ntt.es.springdata.repository.EntityFourRepository;
import com.ntt.es.springdata.repository.EntityOneRepository;
import com.ntt.es.springdata.repository.EntityThreeRepository;

@SpringBootTest
class SpringdataApplicationTests {

	@Autowired
	EntityOneRepository repositoryEntityOne;
	
	@Autowired
	EntityThreeRepository repositoryEntityThree;
	
	@Autowired
	EntityFourRepository repositoryEntityFour;
	
	@Autowired
	EntityFiveRepository repositoryEntityFive;
	
	@Test
	void test() {
		
		repositoryEntityOne.save(EntityOne.builder().field("valor 1").build());
	
		List<EntityOne> result = repositoryEntityOne.findAll();
		
		System.out.println(result);
	}
	
	@Test
	@Transactional
	void testFind() {
		
		EntityOne result = repositoryEntityOne.getById(1);
		
		List<EntityOne> valor = repositoryEntityOne.findByEntityThreeField("valor 3");
		
		System.out.println(result.getField());
		System.out.println(valor);
	}

	@Test
	void testOneToOne() {
		
		System.out.println("--testOneToOne--");
		
		EntityThree entityThree = repositoryEntityThree.save(EntityThree.builder().field("oneToOne_EntityThree").build());
		EntityOne entityOne = repositoryEntityOne.save(EntityOne.builder().field("oneToOne_EntityOne").entityThree(entityThree).build());
		
		/*	Error lazy initialization proxy
		EntityOne entityOneRep = repositoryEntityOne.getById(entityOne.getId());
		EntityThree entityThreeRep = repositoryEntityThree.getById(entityThree.getId());
		*/
		
		EntityOne entityOneRep = repositoryEntityOne.findById(entityOne.getId()).get();
		EntityThree entityThreeRep = repositoryEntityThree.findById(entityThree.getId()).get();
		
		System.out.println("EntityOne standalone:" + (entityOne.getEntityThree() == null));
		System.out.println("EntityThree standalone:" + (entityThree.getEntityOne() == null));
		System.out.println("EntityOne repository:" + (entityOneRep.getEntityThree() == null));
		System.out.println("EntityThree repository:" + (entityThreeRep.getEntityOne() == null));
		System.out.println("");
		
		entityThree.setEntityOne(entityOne);
		
		System.out.println("EntityOne standalone:" + (entityOne.getEntityThree() == null));
		System.out.println("EntityThree standalone:" + (entityThree.getEntityOne() == null));
		System.out.println("EntityOne repository:" + (entityOneRep.getEntityThree() == null));
		System.out.println("EntityThree repository:" + (entityThreeRep.getEntityOne() == null));
		System.out.println("");
		
		entityOne.setEntityThree(null);
		repositoryEntityOne.save(entityOne);
		entityOneRep = repositoryEntityOne.findById(entityOne.getId()).get();
		entityThreeRep = repositoryEntityThree.findById(entityThree.getId()).get();
		
		System.out.println("EntityOne standalone:" + (entityOne.getEntityThree() == null));
		System.out.println("EntityThree standalone:" + (entityThree.getEntityOne() == null));
		System.out.println("EntityOne repository:" + (entityOneRep.getEntityThree() == null));
		System.out.println("EntityThree repository:" + (entityThreeRep.getEntityOne() == null));
		System.out.println("");
		
	}
	
	@Test
	void testManyToMany() {	
		System.out.println("--testManyToMany--");
		
		EntityFour entityFour1 = repositoryEntityFour.save(EntityFour.builder().field("manyToMany_EntityFour1").build());
		EntityFour entityFour2 = repositoryEntityFour.save(EntityFour.builder().field("manyToMany_EntityFour2").build());
		EntityFour entityFour3 = repositoryEntityFour.save(EntityFour.builder().field("manyToMany_EntityFour3").build());
		
		List<EntityFour> listEntityFour1 = Arrays.asList(entityFour1);
		List<EntityFour> listEntityFour2 = Arrays.asList(entityFour2, entityFour3);
		List<EntityFour> listEntityFour3 = Arrays.asList(entityFour1, entityFour3);
		
		EntityFive entityFive1 = repositoryEntityFive.save(EntityFive.builder().field("manyToMany_EntityFive1").entityFour(listEntityFour1).build());
		EntityFive entityFive2 = repositoryEntityFive.save(EntityFive.builder().field("manyToMany_EntityFive2").entityFour(listEntityFour2).build());
		EntityFive entityFive3 = repositoryEntityFive.save(EntityFive.builder().field("manyToMany_EntityFive3").entityFour(listEntityFour3).build());
		
		List<EntityFive> listEntityFive1 = repositoryEntityFour.findById(entityFour1.getId()).get().getEntityFive();
		List<EntityFive> listEntityFive2 = repositoryEntityFour.findById(entityFour2.getId()).get().getEntityFive();
		List<EntityFive> listEntityFive3 = repositoryEntityFour.findById(entityFour3.getId()).get().getEntityFive();
		
		System.out.println("EntityFour1 standalone:" + ((entityFour1.getEntityFive() == null) ? "null" : entityFour1.getEntityFive().size()));
		System.out.println("EntityFour2 standalone:" + ((entityFour2.getEntityFive() == null) ? "null" : entityFour2.getEntityFive().size()));
		System.out.println("EntityFour3 standalone:" + ((entityFour3.getEntityFive() == null) ? "null" : entityFour3.getEntityFive().size()));
		System.out.println("EntityFour1 repository:" + ((listEntityFive1 == null) ? "null" : listEntityFive1.size()));
		System.out.println("EntityFour2 repository:" + ((listEntityFive2 == null) ? "null" : listEntityFive2.size()));
		System.out.println("EntityFour3 repository:" + ((listEntityFive3 == null) ? "null" : listEntityFive3.size()));
		System.out.println("");
		
	}
}
