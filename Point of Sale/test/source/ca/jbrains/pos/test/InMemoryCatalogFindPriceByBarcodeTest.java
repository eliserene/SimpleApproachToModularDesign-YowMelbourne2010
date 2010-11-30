package ca.jbrains.pos.test;

import java.util.*;

import ca.jbrains.pos.test.SellOneItemTest.Catalog;

public class InMemoryCatalogFindPriceByBarcodeTest extends
        FindPriceByBarcodeContract {
    public static class InMemoryCatalog implements Catalog {
        private final Map<String, Integer> pricesByBarcode;

        public InMemoryCatalog(Map<String, Integer> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public int findPriceByBarcode(String barcode) {
            if (pricesByBarcode.containsKey(barcode))
                return pricesByBarcode.get(barcode);
            else
                throw new RuntimeException(barcode);
        }
    }

    @Override
    protected Catalog createCatalogWith(String barcode, int priceInCents) {
        return new InMemoryCatalog(Collections.singletonMap(barcode,
                priceInCents));
    }

    @Override
    protected Catalog createCatalogWithout(
            @SuppressWarnings("unused") String barcode) {
        return new InMemoryCatalog(Collections.<String, Integer> emptyMap());
    }
}
