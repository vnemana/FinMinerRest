package com.mahesh.FinMiner.services;

import com.mahesh.FinMiner.models.Filing;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class FilingService {
    @PersistenceContext
    private EntityManager em;

    public List<Filing> getFilingsForWhale(int whale) {
        if (whale == 0)
            whale = 3;
        TypedQuery<Filing> query = em.createQuery(
                "select f from Filing f where f.fund.fundId = " +
                        ":fundId" , Filing.class)
                .setParameter("fundId", whale)
                .setMaxResults(10);
        return query.getResultList();
    }
}
