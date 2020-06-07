package dao.tables;

import com.alibaba.fastjson.JSONObject;
import dao.JSONAble;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "TransactionRecord")
public class TransactionRecord implements JSONAble {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transactionID", updatable = false)
    private Integer transactionID;

    //stall id
    @Column(name = "stall_name", nullable = false, insertable = false, updatable = false)
    private String stallName;

    //recipe's id that have been ordered, a foreign key of recipe table
    @Column(name = "recipeName", insertable = false, updatable = false)
    private String recipeName;

    //date being sold
    @Column(name = "TransactionTime", nullable = false, updatable = false)
    private Timestamp transactionTime = new Timestamp(System.currentTimeMillis());

    //amount of the dish sold, default to be 1
    @Column(name = "numbers", nullable = false, updatable = false)
    private Integer numbers = 1;

    //price of dish
    @Column(name = "TransactionPrice", nullable = false, updatable = false)
    private Float transactionPrice;
    /********************************************************/
    //transaction record foreign key
    @ManyToOne(targetEntity = Recipe.class, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "recipeName", referencedColumnName = "recipeName")
    private Recipe recipe;

    //transaction record foreign key
    @ManyToOne(targetEntity = Stall.class, optional = false, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "stall_name", referencedColumnName = "stall_name")
    private Stall stall;

    public JSONObject getJson() {
        JSONObject json = new JSONObject();
        json.put("transactionID", transactionID);
        json.put("stallName", stallName);
        json.put("recipeName", recipeName);
        json.put("transactionTime", transactionTime.toString());
        json.put("numbers", numbers);
        json.put("transactionPrice", transactionPrice);
        return json;
    }
}
