package com.khachidze.commissionservice.repository;

import com.khachidze.commissionservice.entity.CommissionEntity;
import com.khachidze.commissionservice.util.EntityManagerProvider;
import com.khachidze.commissionservice.util.TransactionalOperation;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Optional;


@ApplicationScoped
public class CommissionRepository {

    public Optional<CommissionEntity> findCommissionByAmount(BigDecimal amount) {
        return executeInTransaction(entityManager -> {
            Query query = entityManager.createQuery("SELECT c FROM CommissionEntity c WHERE c.minAmount <= :amount AND c.maxAmount >= :amount");
            query.setParameter("amount", amount);
            try {
                CommissionEntity commissionEntity = (CommissionEntity) query.getSingleResult();
                return Optional.of(commissionEntity);
            } catch (NoResultException e) {
                return Optional.empty();
            }
        });
    }

    public CommissionEntity findById(Long id) {
        return executeInTransaction(entityManager -> entityManager.find(CommissionEntity.class, id));
    }

    public void save(CommissionEntity moneyTransfersEntity) {
        executeInTransaction(entityManager -> {
            entityManager.persist(moneyTransfersEntity);
            return null;
        });
    }

    public void update(CommissionEntity commissionEntity) {
        executeInTransaction(entityManager -> {
            entityManager.merge(commissionEntity);
            return null;
        });
    }

    public void delete(CommissionEntity commissionEntity) {
        executeInTransaction(entityManager -> {
            entityManager.remove(commissionEntity);
            return null;
        });
    }

    public void deleteAll() {
        executeInTransaction(entityManager -> {
            Query query = entityManager.createQuery("DELETE FROM CommissionEntity");
            query.executeUpdate();
            return null;
        });
    }

    private <T> T executeInTransaction(TransactionalOperation<T> operation) {
        EntityManager entityManager = EntityManagerProvider.createEntityManager();
        try {
            return operation.executeTransactionally(entityManager);
        } finally {
            entityManager.close();
        }
    }

}
