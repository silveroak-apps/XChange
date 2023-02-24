package org.knowm.xchange.bybit;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.BybitV5Response;
import org.knowm.xchange.bybit.dto.marketdata.BybitSymbol;
import org.knowm.xchange.bybit.dto.marketdata.BybitTicker;
import org.knowm.xchange.bybit.dto.marketdata.BybitV5Result;
import org.knowm.xchange.bybit.service.BybitException;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Bybit {

  /**
   * @apiSpec <a href="https://bybit-exchange.github.io/docs/inverse/#t-latestsymbolinfo">API</a>
   */
  @GET
  @Path("/v2/public/ticker")
  BybitResult<List<BybitTicker>> getTicker24h(@QueryParam("symbol") String symbol) throws IOException, BybitException;

  /**
   * @apiSpec <a href="https://bybit-exchange.github.io/docs/inverse/#t-querysymbol">API</a>
   */
  @GET
  @Path("/v2/public/symbols")
  BybitResult<List<BybitSymbol>> getSymbols() throws IOException, BybitException;

  @GET
  @Path("/v5/market/kline")
  BybitV5Response<BybitV5Result> getKlines(@QueryParam("category") String category, @QueryParam("symbol") String symbol, @QueryParam("interval") String interval) throws IOException, BybitException;

}
