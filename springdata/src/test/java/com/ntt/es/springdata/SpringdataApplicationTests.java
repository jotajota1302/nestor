package com.ntt.es.springdata;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ntt.es.springdata.entity.EntityOne;
import com.ntt.es.springdata.repository.EntityOneRepository;

@SpringBootTest
class SpringdataApplicationTests {

	@Autowired
	EntityOneRepository repository;
	
	@Test
	void test() {
		
		repository.save(EntityOne.builder().field("valor 1").build());
	
		List<EntityOne> result = repository.findAll();
		
		System.out.println(result);
	}

}
