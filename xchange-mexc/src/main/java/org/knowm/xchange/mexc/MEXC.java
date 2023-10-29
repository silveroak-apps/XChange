package org.knowm.xchange.mexc;

import org.knowm.xchange.mexc.dto.MEXCResult;
import org.knowm.xchange.mexc.dto.account.MEXCBalance;
import org.knowm.xchange.mexc.dto.marketdata.MEXCCandleStick;
import org.knowm.xchange.mexc.dto.trade.MEXCOrder;
import org.knowm.xchange.mexc.dto.trade.MEXCOrderRequestPayload;
import org.knowm.xchange.mexc.service.MEXCException;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Path("/api/v3/")
@Produces(MediaType.APPLICATION_JSON)
public interface MEXC {
    /**
     * symbol	string	YES
     * interval	ENUM	YES	ENUM: Kline Interval
     * startTime	long	NO
     * endTime	long	NO
     * limit	integer	NO	Default 500; max 1000.
     * @param marketId
     * @param timeWindow
     * @param startTime
     * @param endTime
     * @param limit
     * @return
     * @throws IOException
     */

    @GET
    @Path("klines")
    List<MEXCCandleStick> getCandles(
            @QueryParam("symbol") String marketId,
            @QueryParam("interval") String timeWindow,
            @QueryParam("startTime") Long startTime,
            @QueryParam("endTime") Long endTime,
            @QueryParam("limit") Integer limit
    )
            throws IOException;

}
