package org.knowm.xchange.btcmarkets;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsOrderBook;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsTicker;
import org.knowm.xchange.btcmarkets.dto.v3.marketdata.BTCCandleStick;
import org.knowm.xchange.btcmarkets.dto.v3.marketdata.BTCMarketsTrade;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BTCMarkets {

  @GET
  @Path("/market/{instrument}/{currency}/tick")
  BTCMarketsTicker getTicker(
      @PathParam("instrument") String instrument, @PathParam("currency") String currency)
      throws IOException;

  @GET
  @Path("/market/{instrument}/{currency}/orderbook")
  BTCMarketsOrderBook getOrderBook(
      @PathParam("instrument") String instrument, @PathParam("currency") String currency)
      throws IOException;

  @GET
  @Path("/v3/markets/{marketId}/trades")
  List<BTCMarketsTrade> getTrades(@PathParam("marketId") String marketId) throws IOException;

  @GET
  @Path("/v3/markets/{marketId}/trades")
  List<BTCMarketsTrade> getTrades(
      @QueryParam("before") Long before,
      @QueryParam("after") Long after,
      @QueryParam("limit") Integer limit,
      @PathParam("marketId") String marketId)
      throws IOException;

  /*
  timeWindow
  string
  Example: timeWindow=1h
  values can be 1m, 3m, 5m, 15m, 30m, 1h, 2h, 3h, 4h, 6h, 1d, 1w and 1mo representing 1 minute, 3 minutes, 5 minutes, 15 minutes, 30 minutes, 1 hour, 2 hours, 3 hours, 4 hours, 6 hours, 1 day, 1 week and 1 month. Default value is 1d if not specified.

  from
  string
  allows retrieving market candles from a specific time. The value is of the type timestamp and must be in ISO 8601 format. e.g. 2018-08-20T06:22:11.000000Z.

  to
  string
  allows retrieving market candles up to a specific time. The value is of the type timestamp and must be in ISO 8601 format. e.g. 2019-08-20T06:22:11.000000Z.

  before
  integer <int64>
  Example: before=78234976
  This is part of the pagination parameters.

  after
  integer <int64>
  Example: after=78234876
  This is part of the pagination parameters.

  limit
  integer <int32>
  Example: limit=10
  This is part of the pagination parameters.
   */
  @GET
  @Path("/v3/markets/{marketId}/candles")
  List<BTCCandleStick> getCandles(
          @PathParam("marketId") String marketId,
          @QueryParam("timeWindow") String timeWindow,
//          API has a problem using these two parameters
//          @QueryParam("from") String from,
//          @QueryParam("to") String to,
          @QueryParam("limit") Integer limit
            )
          throws IOException;
}
