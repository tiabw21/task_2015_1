package org.systisoft.tradeprocessor.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TradeSerializationTest {

    private static final String TEST_MESSAGE = "{\"userId\": \"134256\", \"currencyFrom\": \"EUR\", \"currencyTo\": \"GBP\", \"amountSell\": 1000, \"amountBuy\": 747.10, \"rate\": 0.7471, \"timePlaced\" : \"24-JAN-15 10:27:44\", \"originatingCountry\" : \"FR\"}";

    @Test
    public void testDeserialize() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Trade trade = objectMapper.readValue(TEST_MESSAGE, Trade.class);
        assertNotNull(trade);
        assertEquals(new BigDecimal(1000), trade.getAmountSell());
        assertEquals(new BigDecimal("747.10"), trade.getAmountBuy());
        assertEquals(new BigDecimal("0.7471"), trade.getRate());
        assertEquals("134256", trade.getUserId());
        assertEquals("EUR", trade.getCurrencyFrom());
        assertEquals("GBP", trade.getCurrencyTo());
        assertEquals("24-JAN-15 10:27:44", trade.getTimePlaced());
        assertEquals("FR", trade.getOriginatingCountry());


    }

}