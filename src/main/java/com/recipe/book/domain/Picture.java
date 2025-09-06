package com.recipe.book.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "picture")
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Picture extends BaseEntity {
    // 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 파일 경로
    @Column(name = "file_path", length = 50, nullable = false)
    private String filePath;

    // 파일 명
    @Column(name = "file_name", length = 200, nullable = false)
    private String fileName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Builder
    public Picture(Long id, String filePath, String fileName, Recipe recipe) {
        this.id = id;
        this.filePath = filePath;
        this.fileName = fileName;
        this.recipe = recipe;
    }
}
