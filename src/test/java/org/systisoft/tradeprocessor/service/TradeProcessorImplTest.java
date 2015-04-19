package org.systisoft.tradeprocessor.service;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.systisoft.tradeprocessor.dao.TradeDao;
import org.systisoft.tradeprocessor.entity.Trade;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TradeProcessorImplTest {

    @Test
    public void testProcessTrade() throws Exception {
        TradeDao mockDao = Mockito.mock(TradeDao.class);

        TradeProcessorImpl tradeProcessor = new TradeProcessorImpl();
        ReflectionTestUtils.setField(tradeProcessor, "tradeDao", mockDao);

        Trade trade = new Trade();
        trade.setUserId("other");
        trade.setCurrencyFrom("GBP");
        trade.setCurrencyTo("EUR");

        tradeProcessor.processTrade(trade);

        verify(mockDao, times(1)).saveTrade(trade);
    }


    @Test(expected = RuntimeException.class)
    public void testProcessInvalidTrade() throws Exception {
        TradeDao mockDao = Mockito.mock(TradeDao.class);

        TradeProcessorImpl tradeProcessor = new TradeProcessorImpl();
        ReflectionTestUtils.setField(tradeProcessor, "tradeDao", mockDao);

        Trade trade = new Trade();
        trade.setUserId("other");
        trade.setCurrencyFrom("");
        trade.setCurrencyTo("");

        tradeProcessor.processTrade(trade);

    }
}