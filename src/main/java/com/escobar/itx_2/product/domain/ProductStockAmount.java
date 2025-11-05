package com.escobar.itx_2.product.domain;

public class ProductStockAmount {

    Integer units;

    private ProductStockAmount(Integer units){this.units = units;}

    public static ProductStockAmount zero(){return new ProductStockAmount(0);}

    public static ProductStockAmount fromPrimitive(Integer units){return new ProductStockAmount(units);}

    Integer getValue(){return this.units;}

    public boolean hasStock() {return units != null && units > 0;}

}
