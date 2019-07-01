package models;

import javax.persistence.*;

@Entity
@Table(name = "Fund", schema = "FundReports", catalog = "")
public class FundEntity {
    private int fundId;
    private String fundName;

    @Id
    @Column(name = "fundId")
    public int getFundId() {
        return fundId;
    }

    public void setFundId(int fundId) {
        this.fundId = fundId;
    }

    @Basic
    @Column(name = "fundName")
    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FundEntity that = (FundEntity) o;

        if (fundId != that.fundId) return false;
        if (fundName != null ? !fundName.equals(that.fundName) : that.fundName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fundId;
        result = 31 * result + (fundName != null ? fundName.hashCode() : 0);
        return result;
    }
}
