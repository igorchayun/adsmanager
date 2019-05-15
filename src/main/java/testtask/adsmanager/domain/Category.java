package testtask.adsmanager.domain;

import org.hibernate.validator.constraints.Length;
import javax.persistence.*;

@Entity
@Table
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Length(max = 255, message = "Category name too long")
    @Length(min = 1, message = "Category name must contain more than 1 character")
    private String name;

    @Length(max = 255, message = "Request name too long")
    @Length(min = 1, message = "Request name must contain more than 1 character")
    private String reqName;

    private boolean deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReqName() {
        return reqName;
    }

    public void setReqName(String reqName) {
        this.reqName = reqName;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
