package com.khachidze.commissionservice.util;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EntityManagerProvider {
    public static EntityManager createEntityManager() {
        return Persistence.createEntityManagerFactory("QuickStart").createEntityManager();
    }
}