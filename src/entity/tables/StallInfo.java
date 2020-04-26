package entity.tables;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "StallInfo")
public class StallInfo implements Serializable {
    private static final long serialVersionUID = 9L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stallID;

    @Column(name = "stall_name", length = 40, nullable = false, unique = true)
    private String stallName;

    @Column(name = "stall_location", nullable = false, unique = true)
    private String stallLocation;

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
}
