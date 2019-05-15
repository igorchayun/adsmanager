package testtask.adsmanager.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Length(max = 255, message = "Banner name too long")
    @Length(min = 1, message = "Banner name must contain more than 1 character")
    private String name;

    @NotNull
    @Min(0)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Length(max = 21844, message = "Banner content too long")
    @Length(min = 1, message = "Banner content must contain more than 1 character")
    private String content;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
