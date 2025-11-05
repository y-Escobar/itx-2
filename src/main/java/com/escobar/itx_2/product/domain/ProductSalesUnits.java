package com.escobar.itx_2.product.domain;

public class ProductSalesUnits {

    Integer salesUnits;

    private ProductSalesUnits(Integer salesUnits) {this.salesUnits = salesUnits;}

    public static ProductSalesUnits zero(){return new ProductSalesUnits(0);}

    public static ProductSalesUnits fromPrimitive(Integer salesUnits){return new ProductSalesUnits(salesUnits);}

    Integer getValue(){return this.salesUnits;}
}
