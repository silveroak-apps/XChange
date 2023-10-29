package org.knowm.xchange.hitbtc.v2;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcCandle;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcCurrency;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOrderBook;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSymbol;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTicker;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTrade;

/** Version 2 of HitBtc API. See https://api.hitbtc.com/api/2/explore/ */
@Path("/api/")
public interface Hitbtc {

  @GET
  @Path("2/public/symbol")
  List<HitbtcSymbol> getSymbols() throws IOException;

  @GET
  @Path("2/public/currency")
  List<HitbtcCurrency> getCurrencies() throws IOException;

  @GET
  @Path("2/public/currency/{currency}")
  HitbtcCurrency getCurrency(@PathParam("currency") String currency) throws IOException;

  @GET
  @Path("2/public/ticker")
  List<HitbtcTicker> getTickers() throws IOException;

  @GET
  @Path("2/public/ticker/{symbol}")
  HitbtcTicker getTicker(@PathParam("symbol") String symbol) throws IOException;

  @GET
  @Path("2/public/orderbook/{symbol}")
  HitbtcOrderBook getOrderBook(
      @PathParam("symbol") String symbol, @QueryParam("limit") Integer limit) throws IOException;

  @GET
  @Path("2/public/trades/{symbol}")
  List<HitbtcTrade> getTrades(
      @PathParam("symbol") String symbol,
      @QueryParam("limit") long limit,
      @QueryParam("offset") long offset)
      throws IOException;

  @GET
  @Path("2/public/trades/{symbol}")
  List<HitbtcTrade> getTrades(
      @PathParam("symbol") String symbol,
      @QueryParam("sort") String sortDirection,
      @QueryParam("by") String sortBy,
      @QueryParam("from") String from,
      @QueryParam("limit") long limit)
      throws IOException;

  @GET
  @Path("2/public/ticker")
  List<HitbtcTicker> getHitbtcTickers() throws IOException;


}
