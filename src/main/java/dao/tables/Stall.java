package dao.tables;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Stall")
public class Stall implements Serializable {
    private static final long serialVersionUID = 9L;
    @Id
    @Column(name = "stall_name", length = 30)
    private String stallName;

    @Column(name = "stall_location", nullable = false, unique = true)
    private Integer stallLocation;

    @Column(name = "stall_rent", nullable = false)
    private Float stallRent;

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
    @OneToMany(mappedBy = "stall", cascade = CascadeType.ALL)
    private Set<TransactionRecord> transactionRecords = new HashSet<>();

    @ManyToMany(targetEntity = Recipe.class, cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Recipe_Stall_Association",
            joinColumns = {@JoinColumn(name = "stall_name", referencedColumnName = "stall_name")},
            inverseJoinColumns = {@JoinColumn(name = "recipe_name", referencedColumnName = "recipeName")}
    )
    private Set<Recipe> recipes = new HashSet<>();

    //recipeUsage foreign key
    @OneToMany(mappedBy = "stall", cascade = CascadeType.ALL)
    private Set<MaterialUsage> materialUsages = new HashSet<>();
}
