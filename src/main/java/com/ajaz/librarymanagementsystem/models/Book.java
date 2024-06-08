package com.ajaz.librarymanagementsystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Book extends BaseModel{
    private String title;
    private String author;
    private int price;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User borrowedBy;

    @Enumerated(EnumType.ORDINAL)
    private BookStatus bookStatus = BookStatus.NOT_BOOKED;
}
