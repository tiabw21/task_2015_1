package org.systisoft.tradeprocessor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.systisoft.tradeprocessor.entity.Trade;
import org.systisoft.tradeprocessor.service.TradeProcessor;

import java.util.List;

/**
 *
 */
@Controller
public class TradeController {

    private Logger logger = LoggerFactory.getLogger(TradeController.class);

    @Autowired
    private TradeProcessor tradeProcessor;

    @RequestMapping(value = {"/api/trade"}, method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<String> processTrade(@RequestBody Trade trade) {
        if (!validateUserId(trade)) {
            return new ResponseEntity<>("authorization error", HttpStatus.FORBIDDEN);
        }
        try {
            tradeProcessor.processTrade(trade);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = {"/api/trade"}, method = RequestMethod.GET)
    @ResponseBody
    public List<Trade> getTrades() {
        return tradeProcessor.getTrades(getUserId());
    }

    // TODO: this should be in some UserService class/interface
    private String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            return null;
        } else {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                User user = (User) principal;
                return user.getUsername();
            } else {
                return null;
            }
        }
    }


    private boolean validateUserId(Trade trade) {
        String userId = getUserId();
        if (userId == null) {
            return false;
        } else {
            if (!userId.equals(trade.getUserId())) {
                logger.warn("Illegal activity detected from authenticated user {}, but trying to submit as {}", userId, trade.getUserId());
                return false;
            } else {
                return true;
            }
        }
    }


}
