package com.ennov.read.dao;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Product {
    @Getter @Setter
    private String id;
    @Getter @Setter
    private String line;
    @Getter @Setter
    private String  brand;
    @Getter @Setter
    private String generic;
    @Getter @Setter
    private String  price;
    @Getter @Setter
    private String  difference;
}
