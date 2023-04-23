package com.glady.challenge.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name="payments")
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class PaymentEntity extends BaseEntityCore {

	@ManyToOne(fetch=FetchType.LAZY)
	@Cascade({org.hibernate.annotations.CascadeType.REMOVE, org.hibernate.annotations.CascadeType.PERSIST})
	@JoinColumn(name="company_id")
	private CompanyEntity companyEntity;
	@ManyToOne(fetch=FetchType.LAZY)
	@Cascade(value= org.hibernate.annotations.CascadeType.ALL)
	@JoinColumn(name="employee_id")
	private EmployeeEntity employeeEntity;
	@Column(nullable = false)
	private String depositType;
	@Column(nullable = false)
	private BigDecimal amount;

	public void setAmount(BigDecimal amount) {
		this.amount = amount.setScale(2, RoundingMode.FLOOR);
	}
}
