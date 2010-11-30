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
            if ("123".equals(barcode))
                display.setText("$12.50");
            else
                display.setText("$20.00");
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
}
