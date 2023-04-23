package com.glady.challenge.repository.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "employees")
@Entity
@Data
public class EmployeeEntity extends BaseEntityCore {
	@ManyToOne(fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name="company_id")
	private CompanyEntity companyEntity;
}
