package com.ntt.es.springdata.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entityOne")
public class EntityOne {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "campo")
	private String field;
	
	@Column(name = "atributo")
	private String attribute;
	
	@ManyToOne(targetEntity = EntityTwo.class)
	private List<EntityTwo> entityTwo;

	@OneToOne(targetEntity = EntityThree.class)
	private EntityThree entityThree;
}
