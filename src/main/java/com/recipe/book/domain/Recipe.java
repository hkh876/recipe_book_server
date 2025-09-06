package com.recipe.book.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Objects;

@Entity
@Table(name = "recipe")
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe extends BaseEntity {
    // 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    @Column(name = "title", length = 30, nullable = false)
    private String title;

    // 식재료
    @Lob
    @Column(name = "ingredients", nullable = false, columnDefinition = "LONGTEXT")
    private String ingredients;

    // 레시피
    @Lob
    @Column(name = "contents", nullable = false, columnDefinition = "LONGTEXT")
    private String contents;

    // 팁
    @Lob
    @Column(name = "tip", columnDefinition = "LONGTEXT")
    private String tip;

    // 참조 링크
    @Column(name = "reference", length = 100)
    private String reference;

    @OneToOne(mappedBy = "recipe", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Picture picture;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Builder
    public Recipe(Long id, String title, String ingredients, String contents,
                  String tip, String reference, Category category) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.contents = contents;
        this.tip = tip;
        this.reference = reference;
        this.category = category;
    }

    public void update(String title, String ingredients, String contents, String tip, String reference, Category category) {
        if (!Objects.equals(this.title, title)) {
            this.title = title;
        }

        if (!Objects.equals(this.ingredients, title)) {
            this.ingredients = ingredients;
        }

        if (!Objects.equals(this.contents, contents)) {
            this.contents = contents;
        }

        if (!Objects.equals(this.tip, tip)) {
            this.tip = tip;
        }

        if (!Objects.equals(this.reference, reference)) {
            this.reference = reference;
        }

        if (!Objects.equals(this.category, category)) {
            this.category = category;
        }
    }
}
