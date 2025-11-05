package com.escobar.itx_2.product.domain;

public class ProductName {

    String name;

    private ProductName(String name) {this.name = name;}

    public static ProductName create(String name){return new ProductName(name);}

    public static ProductName fromPrimitive(String name){return new ProductName(name);}

    String getValue(){return this.name;}
}
