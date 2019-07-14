package models;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Filing", schema = "FundReports")
public class Filing {
    @Id
    @Column(name = "filingId")
    private int filingId;

    private Date filingDate;
    private String filingType;
    private Date reportDate;


    public int getFilingId() {
        return filingId;
    }

    public void setFilingId(int filingId) {
        this.filingId = filingId;
    }

    @Basic
    @Column(name = "filingDate")
    public Date getFilingDate() {
        return filingDate;
    }

    public void setFilingDate(Date filingDate) {
        this.filingDate = filingDate;
    }

    @Basic
    @Column(name = "filingType")
    public String getFilingType() {
        return filingType;
    }

    public void setFilingType(String filingType) {
        this.filingType = filingType;
    }

    @Basic
    @Column(name = "reportDate")
    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    @ManyToOne (optional=false)
    @JoinColumn(name = "fundId", referencedColumnName = "fundId")
    private Fund fund;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Filing that = (Filing) o;

        if (filingId != that.filingId) return false;
        if (filingDate != null ? !filingDate.equals(that.filingDate) : that.filingDate != null)
            return false;
        if (filingType != null ? !filingType.equals(that.filingType) : that.filingType != null)
            return false;
        if (reportDate != null ? !reportDate.equals(that.reportDate) : that.reportDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = filingId;
        result = 31 * result + (filingDate != null ? filingDate.hashCode() : 0);
        result = 31 * result + (filingType != null ? filingType.hashCode() : 0);
        result = 31 * result + (reportDate != null ? reportDate.hashCode() : 0);
        return result;
    }

//    private Fund getFundEntity() {
//        return fundEntity;
//    }
//
//    public void setFundEntity(Fund fundEntity) {
//        this.fundEntity = fundEntity;
//    }

}
