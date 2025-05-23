package org.knowm.xchange.bittrex;

import java.io.IOException;
import java.util.List;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.bittrex.dto.BittrexException;
import org.knowm.xchange.bittrex.dto.marketdata.*;

@Path("v3")
@Produces(MediaType.APPLICATION_JSON)
public interface Bittrex {

  @GET
  @Path("markets/{marketSymbol}/orderbook")
  BittrexDepth getOrderBook(
      @PathParam("marketSymbol") String marketSymbol, @QueryParam("depth") int depth)
      throws IOException, BittrexException;

  @GET
  @Path("markets")
  List<BittrexSymbol> getMarkets() throws IOException, BittrexException;

  @GET
  @Path("currencies")
  List<BittrexCurrency> getCurrencies() throws IOException, BittrexException;

  @GET
  @Path("markets/{marketSymbol}/summary")
  BittrexMarketSummary getMarketSummary(@PathParam("marketSymbol") String marketSymbol)
      throws IOException, BittrexException;

  @GET
  @Path("markets/summaries")
  List<BittrexMarketSummary> getMarketSummaries() throws IOException, BittrexException;

  @GET
  @Path("markets/tickers")
  List<BittrexTicker> getTickers() throws IOException, BittrexException;

  @GET
  @Path("markets/{marketSymbol}/trades")
  List<BittrexTrade> getTrades(@PathParam("marketSymbol") String marketSymbol)
      throws IOException, BittrexException;

  @GET
  @Path("markets/{marketSymbol}/ticker")
  BittrexTicker getTicker(@PathParam("marketSymbol") String marketSymbol)
      throws IOException, BittrexException;

  @GET
  @Path("markets/{marketSymbol}/candles/{candleInterval}/recent")
  List<BittrexCandle> getCandles(
          @PathParam("marketSymbol") String marketSymbol,
          @PathParam("candleInterval") String candleInterval)
      throws IOException, BittrexException;
}
