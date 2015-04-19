package org.systisoft.tradeprocessor.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.systisoft.tradeprocessor.entity.Trade;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/spring-test-mongo.xml", "classpath:/spring/spring-test.xml"})
public class MongoTradeDaoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoTradeDao mongoTradeDao;

    @Before
    public void init() {
        mongoTemplate.dropCollection(Trade.class);
    }

    @Test
    public void testSaveTrade() throws Exception {
        List<Trade> tradesBefore = mongoTradeDao.findTrades("test");
        assertEquals(0, tradesBefore.size());
        Trade trade = new Trade();
        trade.setUserId("test");
        trade.setAmountBuy(BigDecimal.TEN);
        trade.setAmountSell(BigDecimal.TEN);
        // TODO: fill most attributes
        mongoTradeDao.saveTrade(trade);
        List<Trade> tradesAfter = mongoTradeDao.findTrades("test");
        assertEquals(1, tradesAfter.size());
    }


}