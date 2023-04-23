package com.glady.challenge.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Table(name = "companies")
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class CompanyEntity extends BaseEntityCore {

	@ColumnDefault(value="0.00")
	private BigDecimal balance;
	public void setBalance(BigDecimal balance) {
		this.balance = balance.setScale(2, RoundingMode.CEILING);
	}

}
