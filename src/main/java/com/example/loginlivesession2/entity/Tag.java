package com.example.loginlivesession2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Tag extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 11)
    private String tagName;

    @Column
    private Integer count;

    public Tag(String tagName) {
        this.tagName = tagName;
        this.count = 1;
    }
    public void plusTag(){
        this.count += 1;
    }

    public void minusTag(){
        this.count -= 1;
    }
}
