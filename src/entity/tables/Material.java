package entity.tables;

import entity.enums.MaterialTypes;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "Material")
public class Material implements Serializable {
    private static final long serialVersionUID = 3L;
    //material id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private Integer name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MaterialTypes type;

    //单价
    @Column(name = "unit_price", nullable = false)
    private Float unitPrice;

    @Column(name = "availableAmount")
    private Float availableAmount = 0.0F;

    //fresh period
    @Column(name = "availableTime", nullable = false)
    private Integer availablePeriod;
}
