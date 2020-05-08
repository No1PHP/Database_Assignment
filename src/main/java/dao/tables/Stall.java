package dao.tables;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Stall")
public class Stall implements Serializable {
    private static final long serialVersionUID = 9L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stall_id", updatable = false)
    private Integer stallID;

    @Column(name = "stall_name", length = 40, nullable = false, unique = true)
    private String stallName;

    @Column(name = "stall_location", nullable = false, unique = true)
    private Integer stallLocation;

    @Column(name = "stall_rent", nullable = false)
    private Float stallRent;

    @Type(type = "json")
    @Column(name = "availableRecipe", nullable = false, columnDefinition = "json")
    private List<String> availableRecipe;

    @Column(name = "oper_cost_last_month")
    private Float costLastMonth = 0.0F;

    @Column(name = "oper_time", nullable = false)
    private Integer manageTimeSoFar = 0;

    @Column(name = "Aver_sales_amount")
    private Float aveMonthlySalesAmount = 0.0F;

    @Column(name = "Aver_mon_sales")
    private Float aveSalesIncome = 0.0F;
    /********************************************************/
    //transaction record foreign key
    @OneToMany(targetEntity = TransactionRecord.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "stall_id", referencedColumnName = "stall_id")
    private Set<TransactionRecord> transactionRecords = new HashSet<>();

    /********************************************************/
    public Integer getStallID() {
        return stallID;
    }

    public void setStallID(Integer stallID) {
        this.stallID = stallID;
    }

    public String getStallName() {
        return stallName;
    }

    public void setStallName(String stallName) {
        this.stallName = stallName;
    }

    public Integer getStallLocation() {
        return stallLocation;
    }

    public void setStallLocation(Integer stallLocation) {
        this.stallLocation = stallLocation;
    }

    public Float getStallRent() {
        return stallRent;
    }

    public void setStallRent(Float stallRent) {
        this.stallRent = stallRent;
    }

    public List<String> getAvailableRecipe() {
        return availableRecipe;
    }

    public void setAvailableRecipe(List<String> availableRecipe) {
        this.availableRecipe = availableRecipe;
    }

    public Float getCostLastMonth() {
        return costLastMonth;
    }

    public void setCostLastMonth(Float costLastMonth) {
        this.costLastMonth = costLastMonth;
    }

    public Integer getManageTimeSoFar() {
        return manageTimeSoFar;
    }

    public void setManageTimeSoFar(Integer manageTimeSoFar) {
        this.manageTimeSoFar = manageTimeSoFar;
    }

    public Float getAveMonthlySalesAmount() {
        return aveMonthlySalesAmount;
    }

    public void setAveMonthlySalesAmount(Float aveMonthlySalesAmount) {
        this.aveMonthlySalesAmount = aveMonthlySalesAmount;
    }

    public Float getAveSalesIncome() {
        return aveSalesIncome;
    }

    public void setAveSalesIncome(Float aveSalesIncome) {
        this.aveSalesIncome = aveSalesIncome;
    }

    public Set<TransactionRecord> getTransactionRecords() {
        return transactionRecords;
    }

    public void setTransactionRecords(Set<TransactionRecord> transactionRecords) {
        this.transactionRecords = transactionRecords;
    }

    @Override
    public String toString() {
        return "Stall{" +
                "stallID=" + stallID +
                ", stallName='" + stallName + '\'' +
                ", stallLocation='" + stallLocation + '\'' +
                ", stallRent=" + stallRent +
                ", costLastMonth=" + costLastMonth +
                ", manageTimeSoFar=" + manageTimeSoFar +
                ", aveMonthlySalesAmount=" + aveMonthlySalesAmount +
                ", aveSalesIncome=" + aveSalesIncome +
                '}';
    }
}
