package ca.jbrains.pos.test;

import static org.junit.Assert.*;

import org.junit.*;

public class SellOneItemTest {
    public static class Sale {
        private Display display;

        public Sale(Display display) {
            this.display = display;
        }

        public void onBarcode(String barcode) {
            if ("".equals(barcode))
                display.setText("Scanning error: empty barcode");
            else if ("123".equals(barcode))
                display.setText("$12.50");
            else if ("456".equals(barcode))
                display.setText("$20.00");
            else
                display.setText("No product with barcode " + barcode);
        }

    }

    public static class Display {
        private String text;

        public void setText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    @Test
    public void productFound() throws Exception {
        Display display = new Display();
        Sale sale = new Sale(display);

        sale.onBarcode("123");

        assertEquals("$12.50", display.getText());
    }

    @Test
    public void anotherProductFound() throws Exception {
        Display display = new Display();
        Sale sale = new Sale(display);

        sale.onBarcode("456");

        assertEquals("$20.00", display.getText());
    }

    @Test
    public void productNotFound() throws Exception {
        Display display = new Display();
        Sale sale = new Sale(display);

        sale.onBarcode("999");

        assertEquals("No product with barcode 999", display.getText());
    }

    @Test
    public void emptyBarcode() throws Exception {
        Display display = new Display();
        Sale sale = new Sale(display);

        sale.onBarcode("");

        assertEquals("Scanning error: empty barcode", display.getText());
    }
}
