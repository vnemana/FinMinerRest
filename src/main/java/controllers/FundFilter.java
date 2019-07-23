package controllers;

import java.util.Date;

public class FundFilter {
    private int fundId;
    private Date startDate;
    private Date endDate;
    private int maxResults;

    public FundFilter() {
        maxResults = -1;
    }

    public int getFundId() {
        return fundId;
    }

    public void setFundId(int fundId) {
        this.fundId = fundId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getMaxResults() { return maxResults; }

    public void setMaxResults(int maxResults) { this.maxResults = maxResults; }

}
