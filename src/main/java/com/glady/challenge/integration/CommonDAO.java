package com.glady.challenge.integration;

import com.glady.challenge.repository.entity.PaymentEntity;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
@AllArgsConstructor
@Log4j2
public class CommonDAO {

    public static final String COMPANY_COLUMN_NAME = "companyEntity";
    public static final String EMPLOYEE_COLUMN_NAME = "employeeEntity";
    @PersistenceContext
    private EntityManager entityManager;

    public List<PaymentEntity> findPaymentsByCompanyIdAndEmployeeId(Long companyId, Long employeeId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PaymentEntity> query = criteriaBuilder.createQuery(PaymentEntity.class);

        Root<PaymentEntity> c = query.from(PaymentEntity.class);
        query.select(c);
        query.where(
                criteriaBuilder.equal(c.get(COMPANY_COLUMN_NAME), companyId),
                criteriaBuilder.equal(c.get(EMPLOYEE_COLUMN_NAME), employeeId)
        );
        return entityManager.createQuery(query).getResultList();
    }

    public <T> Object find(Class<T> clazz, Long objectId) {
       return entityManager.find(clazz, objectId);
    }

    public void createOrUpdate(Object... objects) {
        for (Object object : objects) {
            entityManager.merge(object);
        }
    }
}
