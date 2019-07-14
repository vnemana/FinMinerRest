package models;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Fund", schema = "FundReports")
public class Fund {
    private String fundName;

    @Id
    @Column(name = "fundId", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int fundId;

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

    public Collection getFilings() {
        return filings;
    }

    public void setFilings(Collection filings) {
        this.filings = filings;
    }

    @OneToMany (mappedBy = "fund", targetEntity = Filing.class)
    private Collection filings;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fund that = (Fund) o;

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
