package org.systisoft.tradeprocessor.service;

import org.systisoft.tradeprocessor.entity.Trade;

import java.util.List;

public interface TradeProcessor {

    void processTrade(Trade trade);

    List<Trade> getTrades(String userId);
}
