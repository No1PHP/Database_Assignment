package entity.tables;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MaterialOrder")
public class MaterialOrder implements Serializable {
    private static final long serialVersionUID = 4L;
     //primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Integer orderID;

     //corresponds to the operationRecord table, foreign key
    @Column(name = "op_OrderID", nullable = false)
     private Integer operationOrderID;

     //??
     @Column(name = "op_storageID", nullable = false)
     private Integer operationStorageID;

     //dictation index
     @Column(name = "materialID", nullable = false)
     private Integer materialID;

    @Column(name = "amount", nullable = false)
    private Float materialAmount;
}
