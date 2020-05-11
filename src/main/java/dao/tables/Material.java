package dao.tables;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "Material")
public class Material implements Serializable {
    private static final long serialVersionUID = 3L;
    //material id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private Byte type;

    @Column(name = "unit_price", nullable = false)
    private Float unitPrice;

    @Column(name = "availableAmount")
    private Float availableAmount = 0.0F;

    //fresh period
    @Column(name = "availableTime", nullable = false)
    private Integer availablePeriod;
    /********************************************************/
    //material order foreign key
    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    private Set<MaterialOrder> materialOrders = new HashSet<>();

    @ManyToMany(mappedBy = "materials", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Set<Recipe> recipes = new HashSet<>();

    //recipeUsage foreign key
    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    private Set<MaterialUsage> materialUsages = new HashSet<>();
}
