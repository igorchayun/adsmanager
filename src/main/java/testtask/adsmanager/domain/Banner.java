package testtask.adsmanager.domain;

import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Length(max = 255, message = "Banner name too long")
    @Length(min = 1, message = "Banner name must contain more than 1 character")
    private String name;

    @NotNull(message = "Price must be set")
    @Digits(integer=6, fraction=2, message="Maximum 6 of integral digits " +
            "and maximum 2 of fractional digits accepted for this number")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0.01")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull(message = "Category must be set")
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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
