package org.knowm.xchange.hitbtc.v2;

import org.knowm.xchange.hitbtc.v2.dto.HitbtcCandle;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Path("/api/")
public interface HitbtcV3 extends  Hitbtc {

    @GET
    @Path("3/public/candles/{symbol}")
    List<HitbtcCandle> getHitbtcOHLC(
            @PathParam("symbol") String symbol,
            @QueryParam("limit") int limit,
            @QueryParam("period") String period)
            throws IOException;

    @GET
    @Path("3/public/candles/{symbol}")
    List<HitbtcCandle> getHitbtcOHLC(
            @PathParam("symbol") String symbol,
            @QueryParam("limit") int limit,
            @QueryParam("period") String period,
            @QueryParam("sort") String sort)
            throws IOException;

    @GET
    @Path("3/public/candles/{symbol}")
    List<HitbtcCandle> getHitbtcOHLC(
            @PathParam("symbol") String symbol,
            @QueryParam("limit") int limit,
            @QueryParam("period") String period,
            @QueryParam("from") String from,
            @QueryParam("till") String till,
            @QueryParam("sort") String sort)
            throws IOException;

    @GET
    @Path("3/public/candles/{symbol}")
    List<HitbtcCandle> getHitbtcOHLC(
            @PathParam("symbol") String symbol,
            @QueryParam("limit") int limit,
            @QueryParam("period") String period,
            @QueryParam("offset") int offset,
            @QueryParam("sort") String sort)
            throws IOException;
}
