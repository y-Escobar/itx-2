package com.escobar.itx_2.product.domain;

public enum ProductSortingCriteria {

    SALES_UNITS {
        @Override
        public double calculateScore(Product product) {
            return product.productSalesUnits.salesUnits;
        }
    },

    STOCK_RATIO {
        @Override
        public double calculateScore(Product product) {
            var stock = product.productStock.stock;
            if (stock == null || stock.isEmpty()) {
                return 0.0;
            }
            long sizesWithStock = stock.values().stream().filter(ProductStockAmount::hasStock).count();
            return (double) sizesWithStock / stock.size();
        }
    };

    public abstract double calculateScore(Product product);

}
