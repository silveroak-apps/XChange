package org.knowm.xchange.hitbtc.v2;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcAddress;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcBalance;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcException;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcInternalTransferResponse;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOrder;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOwnTrade;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTransaction;
import org.knowm.xchange.hitbtc.v2.service.HitbtcOrderType;
import org.knowm.xchange.hitbtc.v2.service.HitbtcTimeInForce;
import si.mazi.rescu.HttpStatusIOException;

/** Version 2 of HitBtc API. See https://api.hitbtc.com/api/2/explore/ */
@Path("/api/")
public interface HitbtcAuthenticated extends HitbtcV3 {

  /** ************************* Account APIs ***************************** */
  @GET
  @Path("2/account/balance")
  List<HitbtcBalance> getMainBalance() throws IOException, HitbtcException;

  @GET
  @Path("2/account/crypto/address/{currency}")
  HitbtcAddress getHitbtcDepositAddress(@PathParam("currency") String currency)
      throws IOException, HitbtcException;

  @GET
  @Path("2/account/transactions")
  List<HitbtcTransaction> transactions(
      @QueryParam("currency") String currency,
      @QueryParam("sort") String sort,
      @QueryParam("by") String by,
      @QueryParam("from") String from,
      @QueryParam("till") String till,
      @QueryParam("limit") Integer limit,
      @QueryParam("offset") Integer offset)
      throws HitbtcException, HttpStatusIOException;

  @POST
  @Path("2/account/transfer")
  HitbtcInternalTransferResponse transferToTrading(
      @FormParam("amount") BigDecimal amount,
      @FormParam("currency") String currency,
      @FormParam("type") String type)
      throws IOException, HitbtcException;

  @POST
  @Path("2/account/crypto/withdraw")
  Map payout(
      @FormParam("amount") BigDecimal amount,
      @FormParam("currency") String currency,
      @FormParam("address") String address,
      @FormParam("paymentId") String paymentId,
      @FormParam("includeFee") Boolean includeFee)
      throws HitbtcException, HttpStatusIOException;

  /** ********************** Tradding & Order APIs *********************** */

  // TODO add query params
  @GET
  @Path("2/order")
  List<HitbtcOrder> getHitbtcActiveOrders() throws IOException, HitbtcException;

  @POST
  @Path("2/order")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  HitbtcOrder postHitbtcNewOrder(
      @FormParam("clientOrderId") String clientOrderId,
      @FormParam("symbol") String symbol,
      @FormParam("side") String side,
      @FormParam("price") BigDecimal price,
      @FormParam("quantity") BigDecimal quantity,
      @FormParam("type") HitbtcOrderType type,
      @FormParam("timeInForce") HitbtcTimeInForce timeInForce)
      throws IOException, HitbtcException;

  @PATCH
  @Path("2/order/{clientOrderId}")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  HitbtcOrder updateHitbtcOrder(
      @PathParam("clientOrderId") String clientOrderId,
      @FormParam("quantity") BigDecimal quantity,
      @FormParam("requestClientId") String requestClientId,
      @FormParam("price") BigDecimal price)
      throws IOException, HitbtcException;

  @DELETE
  @Path("2/order")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  List<HitbtcOrder> cancelAllOrders(@FormParam("symbol") String symbol)
      throws IOException, HitbtcException;

  @DELETE
  @Path("2/order/{clientOrderId}")
  HitbtcOrder cancelSingleOrder(@PathParam("clientOrderId") String clientOrderId)
      throws IOException, HitbtcException;

  @GET
  @Path("2/trading/balance")
  List<HitbtcBalance> getTradingBalance() throws IOException, HitbtcException;

  /** ******************* Trading History APIs ***************************** */
  @GET
  @Path("2/history/trades")
  List<HitbtcOwnTrade> getHitbtcTrades(
      @QueryParam("symbol") String symbol,
      @QueryParam("sort") String sort,
      @QueryParam("by") String sortBy,
      @QueryParam("from") String from,
      @QueryParam("till") String till,
      @QueryParam("limit") Integer limit,
      @QueryParam("offset") long offset)
      throws IOException, HitbtcException;

  // TODO add query params

  /**
   * Get historical orders
   *
   * @return
   * @throws IOException
   * @throws HitbtcException
   */
  @GET
  @Path("2/history/order")
  List<HitbtcOrder> getHitbtcRecentOrders() throws IOException, HitbtcException;

  /**
   * Get an old order. The returning collection contains, at most, 1 element.
   *
   * @param symbol symbol
   * @param clientOrderId client order id
   * @return list of orders
   * @throws IOException throw in case IO problems
   * @throws HitbtcException throw in case internal HITBTC problems
   */
  @GET
  @Path("2/history/order")
  List<HitbtcOrder> getHitbtcOrder(
      @QueryParam("symbol") String symbol, @QueryParam("clientOrderId") String clientOrderId)
      throws IOException, HitbtcException;

  @GET
  @Path("2/history/order/{id}/trades")
  List<HitbtcOwnTrade> getHistorialTradesByOrder(@PathParam("id") String orderId)
      throws IOException, HitbtcException;
}
