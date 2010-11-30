package ca.jbrains.pos.test;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

import ca.jbrains.pos.test.SellOneItemTest.Catalog;

public class FindPriceByBarcodeTest {
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

    @Test
    public void productFound() throws Exception {
        InMemoryCatalog catalog = new InMemoryCatalog(Collections.singletonMap(
                "293", 2000));
        assertEquals(2000, catalog.findPriceByBarcode("293"));
    }

    @Test
    public void productNotFound() throws Exception {
        InMemoryCatalog catalog = new InMemoryCatalog(
                Collections.<String, Integer> emptyMap());

        try {
            catalog.findPriceByBarcode("293");
            fail("How the hell did you find a product for a barcode I don't have?!");
        }
        catch (RuntimeException expected) {
            assertEquals("293", expected.getMessage());
        }
    }
}
