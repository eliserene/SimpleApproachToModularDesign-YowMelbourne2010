package ca.jbrains.pos.test;

import static org.junit.Assert.*;

import org.junit.*;

import ca.jbrains.pos.test.SellOneItemTest.Catalog;

public abstract class FindPriceByBarcodeContract {
    protected abstract Catalog createCatalogWithout(
            @SuppressWarnings("unused") String barcode);

    protected abstract Catalog createCatalogWith(String barcode,
            int priceInCents);

    @Test
    public void productFound() throws Exception {
        Catalog catalog = createCatalogWith("293", 2000);
        assertEquals(2000, catalog.findPriceByBarcode("293"));
    }

    @Test
    public void productNotFound() throws Exception {
        Catalog catalog = createCatalogWithout("293");

        try {
            catalog.findPriceByBarcode("293");
            fail("How the hell did you find a product for a barcode I don't have?!");
        }
        catch (RuntimeException expected) {
            assertEquals("293", expected.getMessage());
        }
    }

}