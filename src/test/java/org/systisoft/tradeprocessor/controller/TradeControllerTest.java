package org.systisoft.tradeprocessor.controller;

import org.apache.commons.net.util.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.systisoft.tradeprocessor.entity.Trade;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/spring-test-mongo.xml", "classpath:/spring/spring-test.xml"})
public class TradeControllerTest {

    // TODO: there's a lot to reuse here

    @Test
    public void testProcessTradeOk() throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        String authString = "134256" + ":" + "T0psecre!";
        String authStringEncoded = new String(Base64.encodeBase64(authString.getBytes()));

        System.out.println("auth: '" + authStringEncoded + "'");


        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Basic " + authStringEncoded);

        Trade trade = new Trade();
        trade.setUserId("134256");
        trade.setCurrencyFrom("GBP");
        trade.setCurrencyTo("EUR");

        HttpEntity<Trade> tradeHttpEntity = new HttpEntity<>(trade, headers);


        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8080/api/trade", HttpMethod.POST, tradeHttpEntity, String.class);

        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertEquals("success", exchange.getBody());

    }

    @Test
    public void testGetTrades() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        String authString = "134256" + ":" + "T0psecre!";
        String authStringEncoded = new String(Base64.encodeBase64(authString.getBytes()));

        System.out.println("auth: '" + authStringEncoded + "'");


        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Basic " + authStringEncoded);

        HttpEntity<?> tradeHttpEntity = new HttpEntity<>(headers);


        ResponseEntity<List> exchange = restTemplate.exchange("http://localhost:8080/api/trade", HttpMethod.GET, tradeHttpEntity, List.class);

        assertEquals(HttpStatus.OK, exchange.getStatusCode());

        List<Trade> trades = exchange.getBody();

        System.out.println(trades.size());

    }


    @Test
    public void testProcessTradeWrongUserOrPassword() throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        String authString = "other" + ":" + "typo";
        String authStringEncoded = new String(Base64.encodeBase64(authString.getBytes()));

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Basic " + authStringEncoded);

        Trade trade = new Trade();
        trade.setUserId("other");
        trade.setCurrencyFrom("GBP");
        trade.setCurrencyTo("EUR");

        HttpEntity<Trade> tradeHttpEntity = new HttpEntity<>(trade, headers);

        try {
            restTemplate.exchange("http://localhost:8080/api/trade", HttpMethod.POST, tradeHttpEntity, String.class);
            fail("Should have failed");
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.UNAUTHORIZED, e.getStatusCode());
        }

    }

    /**
     * Test when the authenticated user and the user id in trade are different
     *
     * @throws Exception
     */
    @Test
    public void testProcessTradeIllegalUserId() throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        String authString = "134256" + ":" + "T0psecre!";
        String authStringEncoded = new String(Base64.encodeBase64(authString.getBytes()));

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Basic " + authStringEncoded);

        Trade trade = new Trade();
        trade.setUserId("other");
        trade.setCurrencyFrom("GBP");
        trade.setCurrencyTo("EUR");

        HttpEntity<Trade> tradeHttpEntity = new HttpEntity<>(trade, headers);

        try {
            restTemplate.exchange("http://localhost:8080/api/trade", HttpMethod.POST, tradeHttpEntity, String.class);
            fail("Should have failed");
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.FORBIDDEN, e.getStatusCode());
        }
    }


}