package org.knowm.xchange.bybit.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import jakarta.ws.rs.core.Response.Status;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitV5Response;
import org.knowm.xchange.bybit.dto.marketdata.BybitV5Result;
import org.knowm.xchange.bybit.dto.marketdata.KlineInterval;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;


public class BybitMarketDataServiceTest extends BaseWiremockTest {

  @Test
  public void testGetTicker() throws Exception {
    Exchange bybitExchange = createExchange();
    MarketDataService marketDataService = bybitExchange.getMarketDataService();

    stubFor(
        get(urlPathEqualTo("/v2/public/ticker"))
            .willReturn(
                aResponse()
                    .withStatus(Status.OK.getStatusCode())
                    .withHeader("Content-Type", "application/json")
                    .withBody(IOUtils.resourceToString("/getTicker.json5", StandardCharsets.UTF_8))
            )
    );

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USDT);

    assertThat(ticker.getInstrument().toString()).isEqualTo("BTC/USDT");
    assertThat(ticker.getOpen()).isEqualTo(new BigDecimal("21670.00"));
    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("21333.00"));
    assertThat(ticker.getBid()).isEqualTo(new BigDecimal("21323"));
    assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("21334"));
    assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("22024.50"));
    assertThat(ticker.getLow()).isEqualTo(new BigDecimal("21120.00"));
    assertThat(ticker.getVwap()).isNull();
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("10028.87"));
    assertThat(ticker.getQuoteVolume()).isEqualTo(new BigDecimal("216158761.48"));
    assertThat(ticker.getTimestamp()).isEqualTo(Instant.parse("2022-07-10T09:09:11.611Z"));
    assertThat(ticker.getBidSize()).isNull();
    assertThat(ticker.getAskSize()).isNull();
    assertThat(ticker.getPercentageChange()).isEqualTo(new BigDecimal("-0.015551"));

  }

  @Test
  public void testGetCandleStickData() throws Exception {
    Exchange bybitExchange = createExchange();
    BybitMarketDataService marketDataService = (BybitMarketDataService) bybitExchange.getMarketDataService();

    stubFor(
            get(urlPathEqualTo("/v5/market/kline"))
                    .willReturn(
                            aResponse()
                                    .withStatus(Status.OK.getStatusCode())
                                    .withHeader("Content-Type", "application/json")
                                    .withBody(IOUtils.resourceToString("/getCandleStickData.json5", StandardCharsets.UTF_8))
                    )

    );
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.HOUR, -4);
    Date oneHourBack = cal.getTime();
    BybitV5Response<BybitV5Result> klines = marketDataService.getKlines((CurrencyPair.BTC_USDT), KlineInterval.h1, "spot", oneHourBack.getTime(), new Date().getTime());
    assertThat(klines.getResult().getBybitOHLCVS().size()).isEqualTo(2);

  }

}