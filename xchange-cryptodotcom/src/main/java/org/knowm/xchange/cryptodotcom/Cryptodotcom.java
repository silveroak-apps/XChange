package org.knowm.xchange.cryptodotcom;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.LinkedHashMap;

/** @author jamespedwards42 */
@Path("v2")
@Produces(MediaType.APPLICATION_JSON)
public interface Cryptodotcom {

  @GET
  @Path("public/get-candlestick")
  LinkedHashMap getHistoricalCandles(
          @QueryParam("instrument_name") String instrumentName,
          @QueryParam("timeframe") String timeframe)
          throws IOException, org.knowm.xchange.cryptodotcom.dto.CryptodotcomException;

}
