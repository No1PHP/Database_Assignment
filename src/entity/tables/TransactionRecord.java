package entity.tables;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "TransactionRecord")
public class TransactionRecord implements Serializable {
    private static final long serialVersionUID = 10L;
    //id, auto primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionID;

    //stall id
    @Column(name = "stall_id", nullable = false)
    private Integer stallID;

    //recipe's id that have been ordered, a foreign key of recipe table
    @Column(name = "recipeID", nullable = false)
    private Integer recipeID;

    //date being sold
    @Column(name = "TransactionTime", nullable = false)
    private Date transactionTime;

    //amount of the dish sold, default to be 1
    @Column(name = "numbers", nullable = false)
    private Integer numbers = 1;

    //price of dish
    @Column(name = "TransactionPrice", nullable = false)
    private Float transactionPrice;
}
