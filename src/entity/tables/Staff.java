package entity.tables;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Staff")
public class Staff implements Serializable {
    private static final long serialVersionUID = 8L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer staffID;

    @Column(name = "staff_name", length = 30, nullable = false)
    private String staffName;

    @Column(name = "staff_category")
    @Enumerated(EnumType.STRING)
    private String staffCategory;

    //different from mysql datetime type, need considering the parsing!!!
    @Column(name = "Effe_work_time_starts")
    private String timeStartWorking;

    //different from mysql datetime!!!
    @Column(name = "Effe_work_time_end")
    private String timeEndWorking;
}
