package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;
import java.awt.*;

@Entity
@Data
@Table(name = "ads")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private int price;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", author=" + author +
                '}';
    }
}
