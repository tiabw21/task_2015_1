package org.systisoft.tradeprocessor.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.systisoft.tradeprocessor.entity.Trade;

import java.util.List;

/**
 * Trade DAO using MongoDB
 */
@Repository
public class MongoTradeDao implements TradeDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveTrade(Trade trade) {
        mongoTemplate.save(trade);
    }

    @Override
    public List<Trade> findTrades(String userId) {
        return mongoTemplate.find(Query.query(Criteria.where("userId").is(userId)), Trade.class);
    }
}
