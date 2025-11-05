package com.escobar.itx_2.product.domain;

public class ProductId {

    String id;

    private ProductId(String id){this.id = id;}

    public static ProductId create(String id){return new ProductId(id);}

    public static ProductId fromPrimitive(String id){
        return new ProductId(id);
    }

    String getValue(){return this.id;}

}
