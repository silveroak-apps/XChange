package org.knowm.xchange.bitstamp.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import javax.annotation.Nullable;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.BitstampV2;
import org.knowm.xchange.bitstamp.dto.BitstampException;
import org.knowm.xchange.bitstamp.dto.marketdata.*;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.currency.CurrencyPair;

/** @author gnandiga */
public class BitstampMarketDataServiceRaw extends BitstampBaseService {

  private final BitstampV2 bitstampV2;

  public BitstampMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.bitstampV2 =
        ExchangeRestProxyBuilder.forInterface(BitstampV2.class, exchange.getExchangeSpecification())
            .build();
  }

  public BitstampTicker getBitstampTicker(CurrencyPair pair) throws IOException {
    try {
      return bitstampV2.getTicker(new BitstampV2.Pair(pair));
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampTicker getBitstampTickerHourly(CurrencyPair pair) throws IOException {
    try {
      return bitstampV2.getTickerHour(new BitstampV2.Pair(pair));
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampOrderBook getBitstampOrderBook(CurrencyPair pair) throws IOException {

    try {
      return bitstampV2.getOrderBook(new BitstampV2.Pair(pair));
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  //start and end Time are in seconds
  public LinkedHashMap getOhlcData(CurrencyPair pair, long start, long end, int interval, int limit) throws IOException {
    try {
      return bitstampV2.getOhlcs(new BitstampV2.Pair(pair), start /1000, end / 1000, interval, limit);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampTransaction[] getTransactions(CurrencyPair pair, @Nullable BitstampTime time)
      throws IOException {

    try {
      return bitstampV2.getTransactions(new BitstampV2.Pair(pair), time);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampPairInfo[] getTradingPairsInfo() throws IOException {
    try {
      return bitstampV2.getTradingPairsInfo();
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public enum BitstampTime {
    DAY,
    HOUR,
    MINUTE;

    @Override
    public String toString() {
      return super.toString().toLowerCase();
    }
  }
}
