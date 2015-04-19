package org.systisoft.tradeprocessor.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.systisoft.tradeprocessor.dao.TradeDao;
import org.systisoft.tradeprocessor.entity.Trade;

import java.util.List;

@Service
public class TradeProcessorImpl implements TradeProcessor {

    @Autowired
    private TradeDao tradeDao;

    @Override
    public void processTrade(Trade trade) {
        validateTrade(trade);
        tradeDao.saveTrade(trade);
    }

    @Override
    public List<Trade> getTrades(String userId) {
        return tradeDao.findTrades(userId);
    }

    private void validateTrade(Trade trade) {
        if (StringUtils.isBlank(trade.getCurrencyFrom())) {
            throw new RuntimeException("Illegal from currency: " + trade.getCurrencyFrom());
        }
        // TODO: add more validation
        // TODO: validate currency using reference data
    }
}
