package ca.jbrains.pos.test;

import static org.junit.Assert.*;

import org.jmock.*;
import org.jmock.integration.junit4.*;
import org.junit.*;
import org.junit.runner.*;

@RunWith(JMock.class)
public class SellOneItemTest {
    public interface Catalog {
        int findPriceByBarcode(String barcode);
    }

    public static class Sale {
        private final Display display;
        private final Catalog catalog;

        public Sale(Catalog catalog, Display display) {
            this.catalog = catalog;
            this.display = display;
        }

        public void onBarcode(String barcode) {
            if ("".equals(barcode)) {
                display.displayEmptyBarcodeMessage();
                return;
            }

            try {
                display.displayPrice(catalog.findPriceByBarcode(barcode));
            }
            catch (RuntimeException handled) {
                display.displayProductNotFoundMessage(handled.getMessage());
            }
        }
    }

    public interface Display {
        void displayPrice(int priceInCents);

        void displayProductNotFoundMessage(String barcode);

        void displayEmptyBarcodeMessage();
    }

    private JUnit4Mockery mockery = new JUnit4Mockery();

    @Test
    public void productFound() throws Exception {
        final Catalog catalog = mockery.mock(Catalog.class);
        final Display display = mockery.mock(Display.class);

        mockery.checking(new Expectations() {
            {
                allowing(catalog).findPriceByBarcode(with(equal("123")));
                will(returnValue(1250));
            }
        });

        mockery.checking(new Expectations() {
            {
                oneOf(display).displayPrice(1250);
            }
        });

        new Sale(catalog, display).onBarcode("123");
    }

    @Test
    public void productNotFound() throws Exception {
        final Catalog catalog = mockery.mock(Catalog.class);
        final Display display = mockery.mock(Display.class);

        mockery.checking(new Expectations() {
            {
                allowing(catalog).findPriceByBarcode(with(equal("123")));
                will(throwException(new RuntimeException("123")));
            }
        });

        mockery.checking(new Expectations() {
            {
                oneOf(display).displayProductNotFoundMessage("123");
            }
        });

        new Sale(catalog, display).onBarcode("123");
    }

    @Test
    public void emptyBarcode() throws Exception {
        final Display display = mockery.mock(Display.class);

        mockery.checking(new Expectations() {
            {
                oneOf(display).displayEmptyBarcodeMessage();
            }
        });

        new Sale(null, display).onBarcode("");
    }
}
