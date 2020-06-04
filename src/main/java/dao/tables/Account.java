package dao.tables;

import com.alibaba.fastjson.JSONObject;
import dao.JSONAble;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "Account")
public class Account implements JSONAble {
    //primary key
    @Id
    @Column(name = "staffId")
    private Integer staffID;

    //foreign key
    @Column(name = "position", length = 20, insertable = false, updatable = false)
    private String position;

    @Column(name = "accountName", length = 20, nullable = false, unique = true)
    private String accountName;

    @Column(name = "password", length = 20, nullable = false)
    private String passwordHashValue;
    /********************************************************/
    //account foreign key
    @OneToOne(targetEntity = Staff.class, optional = false, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "staffId", referencedColumnName = "staff_id")
    private Staff staff;

    //account foreign key
    @ManyToOne(targetEntity = AccessInfo.class, optional = false, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "position", referencedColumnName = "position")
    private AccessInfo accessInfo;

    public JSONObject getJson() {
        JSONObject json = new JSONObject();
        json.put("staffID", staffID);
        json.put("position", position);
        json.put("accountName", accountName);
        return json;
    }
}
