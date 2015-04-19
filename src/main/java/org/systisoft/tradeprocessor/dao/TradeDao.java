package org.systisoft.tradeprocessor.dao;

import org.systisoft.tradeprocessor.entity.Trade;

import java.util.List;

public interface TradeDao {

    /**
     * Saves a trade to some datasource
     *
     * @param trade
     */
    void saveTrade(Trade trade);

    /**
     * Find trades
     *
     * @param userId
     * @return
     */
    List<Trade> findTrades(String userId);
}
