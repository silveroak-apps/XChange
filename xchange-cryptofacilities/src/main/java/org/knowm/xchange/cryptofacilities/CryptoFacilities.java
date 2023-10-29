package org.knowm.xchange.cryptofacilities;

import java.io.IOException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesInstruments;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOrderBook;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesPublicFills;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesTickers;

/** @author Jean-Christophe Laruelle */
@Path("/api/v3")
@Produces(MediaType.APPLICATION_JSON)
public interface CryptoFacilities {

  @GET
  @Path("/tickers")
  CryptoFacilitiesTickers getTickers() throws IOException;

  @GET
  @Path("/orderbook")
  CryptoFacilitiesOrderBook getOrderBook(@QueryParam("symbol") String symbol) throws IOException;

  @GET
  @Path("/instruments")
  CryptoFacilitiesInstruments getInstruments() throws IOException;

  @GET
  @Path("/history")
  CryptoFacilitiesPublicFills getHistory(@QueryParam("symbol") String symbol) throws IOException;
}
