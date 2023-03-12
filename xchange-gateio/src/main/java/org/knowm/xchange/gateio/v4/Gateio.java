package org.knowm.xchange.gateio.v4;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("api/v4")
@Produces(MediaType.APPLICATION_JSON)
public interface Gateio {

  @GET
  @Path("/spot/candlesticks")
  List<ArrayList<Object>> getKlinesSpot(
          @QueryParam("currency_pair") String tradePair,
          @QueryParam("from") Long from,
          @QueryParam("to") Long to,
          @QueryParam("interval") String interval)
          throws IOException;
}
