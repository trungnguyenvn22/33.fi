package com.Store_Service.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String categoryName;
    String code;
    String categoryDescription;
    long parent_id;
    boolean active;

}
