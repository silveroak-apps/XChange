package org.knowm.xchange.phemex;

import org.knowm.xchange.phemex.dto.PhemexException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.LinkedHashMap;

/** @author jamespedwards42 */
@Path("exchange/public")
@Produces(MediaType.APPLICATION_JSON)
public interface Phemex {

  @GET
  @Path("/md/v2/kline")
  LinkedHashMap getHistoricalCandles(
          @QueryParam("symbol") String symbol,
          @QueryParam("to") Long to,
          @QueryParam("from") Long from,
          @QueryParam("resolution") Integer resolution)
          throws IOException, PhemexException;

}
