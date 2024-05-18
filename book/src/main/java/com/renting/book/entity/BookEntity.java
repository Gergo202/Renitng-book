package com.renting.book.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Data
@Table(name = "books")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "rented")
    @ColumnDefault("false")
    private boolean isRented;

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "name")
    private String name;

    @Column(name = "returnDate")
    private Date returnDate;
}
