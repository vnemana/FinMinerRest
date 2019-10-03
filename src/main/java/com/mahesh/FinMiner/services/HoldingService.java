package com.mahesh.FinMiner.services;

import com.mahesh.FinMiner.models.Holding;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class HoldingService {
    @PersistenceContext
    private
    EntityManager em;

    public List<Holding> getHoldingsForFiling(int filingId) {
        TypedQuery<Holding> query = em.createQuery(
                "select h from Holding h where h.filing.filingId = " +
                        ":filing_id order by h.position desc", Holding.class)
                .setParameter("filing_id", filingId);
        return query.getResultList();
    }
}
