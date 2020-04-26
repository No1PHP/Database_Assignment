package entity.tables;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "AccessInfo")
public class AccessInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    //primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String position;

    //access authority to the order related tables
    @Column(name = "AccessToOrder", nullable = false)
    private Boolean AccessToOrder;

    //access authority to the Staff related tables
    @Column(name = "AccessToStaff", nullable = false)
    private Boolean AccessToStaff;

    //access authority to the stock(materials?) related tables
    @Column(name = "AccessToStock", nullable = false)
    private Boolean AccessToStock;
}
