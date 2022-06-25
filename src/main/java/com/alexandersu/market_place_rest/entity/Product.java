package com.alexandersu.market_place_rest.entity;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "product")
@Builder
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "city")
    private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    private List<Image> images = new ArrayList<>();

    private Long previewImageId;

    private LocalDateTime createDate;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @PrePersist
    protected void init() {
        createDate = LocalDateTime.now();
    }

    public Product(Long id, String title, String description, int price, String city) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.city = city;
    }
}
