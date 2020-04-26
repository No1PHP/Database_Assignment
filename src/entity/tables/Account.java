package entity.tables;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Account")
public class Account implements Serializable {//这个类是否实现接口还需商议
    private static final long serialVersionUID = 2L;
    //primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer staffID;

    //foreign key
    @Column(name = "position", length = 20)
    private String position;

    @Column(name = "accountName", length = 20, nullable = false)
    private String accountName;

    @Column(name = "password", length = 20)
    private String passwordHashValue;
}
