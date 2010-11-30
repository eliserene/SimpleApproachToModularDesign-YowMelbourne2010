package ca.jbrains.pos.test

import spock.lang.*;

class SellOneItemSpec extends Specification {
    def "display text when scanning a barcode"() {
        when:
        sale.onBarcode("123")
        
        then:
        assertEquals("$12.50", display.text)
    }
}
