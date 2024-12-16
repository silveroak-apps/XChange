package org.knowm.xchange.coinbase.v2.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinbase.v2.CoinbaseExchange;
import org.knowm.xchange.coinbase.v2.dto.marketdata.CoinbaseTimeData.CoinbaseTime;
import org.knowm.xchange.coinbase.v2.service.CoinbaseBaseService;

public class BaseServiceIntegration {

  static CoinbaseExchange exchange;
  static CoinbaseBaseService baseService;

  @BeforeClass
  public static void beforeClass() {
    exchange = (CoinbaseExchange) ExchangeFactory.INSTANCE.createExchange(CoinbaseExchange.class);
    baseService = (CoinbaseBaseService) exchange.getMarketDataService();
  }

  @Test
  public void currencyFetchTest() throws Exception {

    CoinbaseTime coinbaseTime = baseService.getCoinbaseTime();
    ZonedDateTime dateTime = Instant.ofEpochMilli(new Date().getTime())
            .atZone(ZoneId.of("UTC"));
    String today = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
    assertThat(coinbaseTime.getIso()).startsWith(today);
    assertThat(coinbaseTime.getEpoch()).isNotNull();
  }
}
