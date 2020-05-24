package dao.tables;

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
@Table(name = "AccessInfo")
public class AccessInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    //primary key
    @Id
    @Column(name = "position")
    private String position;

    //access authority to the order related tables
    @Column(name = "AccessToMaterial", nullable = false)
    private Boolean accessToMaterial;

    //access authority to the Staff related tables
    @Column(name = "AccessToStaff", nullable = false)
    private Boolean accessToStaff;

    //access authority to the stock(materials?) related tables
    @Column(name = "AccessToStall", nullable = false)
    private Boolean accessToStall;
    /********************************************************/
    //account foreign key
    @OneToMany(mappedBy = "accessInfo", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();
}
