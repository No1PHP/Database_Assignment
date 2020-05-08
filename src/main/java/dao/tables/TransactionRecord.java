package dao.tables;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TransactionRecord")
public class TransactionRecord implements Serializable {
    private static final long serialVersionUID = 10L;
    //id, auto primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionID", updatable = false)
    private Integer transactionID;

    //stall id
    @Column(name = "stall_id", nullable = false)
    private Integer stallID;

    //recipe's id that have been ordered, a foreign key of recipe table
    @Column(name = "recipeID")
    private Integer recipeID;

    //date being sold
    @CreatedDate
    @Column(name = "TransactionTime", nullable = false, updatable = false)
    private Date transactionTime;

    //amount of the dish sold, default to be 1
    @Column(name = "numbers", nullable = false, updatable = false)
    private Integer numbers = 1;

    //price of dish
    @Column(name = "TransactionPrice", nullable = false, updatable = false)
    private Float transactionPrice;
    /********************************************************/
    //transaction record foreign key
    @ManyToOne(targetEntity = Recipe.class, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "recipeID", referencedColumnName = "recipeID")
    private Recipe recipe;

    //transaction record foreign key
    @ManyToOne(targetEntity = Stall.class, optional = false, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "stall_id", referencedColumnName = "stall_id")
    private Stall stall;

    /********************************************************/
    public Integer getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Integer transactionID) {
        this.transactionID = transactionID;
    }

    public Integer getStallID() {
        return stallID;
    }

    public void setStallID(Integer stallID) {
        this.stallID = stallID;
    }

    public Integer getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(Integer recipeID) {
        this.recipeID = recipeID;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Integer getNumbers() {
        return numbers;
    }

    public void setNumbers(Integer numbers) {
        this.numbers = numbers;
    }

    public Float getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(Float transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Stall getStall() {
        return stall;
    }

    public void setStall(Stall stall) {
        this.stall = stall;
    }

    @Override
    public String toString() {
        return "TransactionRecord{" +
                "transactionID=" + transactionID +
                ", stallID=" + stallID +
                ", recipeID=" + recipeID +
                ", transactionTime=" + transactionTime +
                ", numbers=" + numbers +
                ", transactionPrice=" + transactionPrice +
                '}';
    }
}
