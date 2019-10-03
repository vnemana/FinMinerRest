package com.mahesh.FinMiner.services;

import com.mahesh.FinMiner.controllers.FundFilter;
import com.mahesh.FinMiner.models.Filing;
import com.mahesh.FinMiner.models.Fund;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Stateless
public class FundService {
    @PersistenceContext
    private EntityManager em;

    public List<Fund> getAllFunds() {
        TypedQuery<Fund> query = em.createQuery("select f from Fund f", Fund
                .class);
        return query.getResultList();
    }

    public List<Filing> getFilingsForFund(int fund) {
        if (fund == 0) fund = 3;
        Fund f = em.find(Fund.class, fund);
        return (List<Filing>) f.getFilings();
    }

    public List<Filing> getFilingsForFund(FundFilter fundFilter) {
        String whereClause = "";
        if (fundFilter.getFundId() > 0) {
            whereClause += " where ";
            whereClause += " fi.fund.fundId = " + fundFilter.getFundId() + " ";
        }

        whereClause = addWhereClauseForDate (
                whereClause, fundFilter.getStartDate(), " fi.filingDate > '");

        whereClause = addWhereClauseForDate (
                whereClause, fundFilter.getEndDate(), " fi.filingDate < '");

        TypedQuery<Filing> query = em.createQuery("select fi from Filing fi " +
                whereClause + " order by fi.filingDate desc",
                Filing.class);
        //query.setParameter("fundId", fundNum);
        if (fundFilter.getMaxResults() != -1)
            query.setMaxResults(fundFilter.getMaxResults());
        return query.getResultList();
    }

    private String addWhereClauseForDate(String whereClause, Date date,
                                         String condition) {
        if (date != null &&
                !date.toString().isEmpty()) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            if (whereClause.equals("")) whereClause += " where ";
            else whereClause += " and ";
            whereClause += condition + cal.get(Calendar.YEAR) + "-"
                    + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar
                    .DAY_OF_WEEK) +
                    "' ";
        }
        return whereClause;
    }
}
