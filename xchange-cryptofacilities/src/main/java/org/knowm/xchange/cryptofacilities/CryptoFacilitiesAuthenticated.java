package org.knowm.xchange.cryptofacilities;

import java.io.IOException;
import java.math.BigDecimal;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.cryptofacilities.dto.account.CryptoFacilitiesAccounts;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesCancel;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesCancelAllOrdersAfter;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesFills;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOpenOrders;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOpenPositions;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOrder;
import org.knowm.xchange.cryptofacilities.dto.trade.BatchOrder;
import org.knowm.xchange.cryptofacilities.dto.trade.BatchOrderResult;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author Jean-Christophe Laruelle */
@Path("/api/v3")
@Produces(MediaType.APPLICATION_JSON)
public interface CryptoFacilitiesAuthenticated extends CryptoFacilities {

  @GET
  @Path("accounts")
  CryptoFacilitiesAccounts accounts(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @POST
  @Path("sendorder")
  CryptoFacilitiesOrder sendOrder(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("orderType") String orderType,
      @QueryParam("symbol") String symbol,
      @QueryParam("side") String side,
      @QueryParam("size") BigDecimal size,
      @QueryParam("limitPrice") BigDecimal limitPrice,
      @QueryParam("stopPrice") BigDecimal stopPrice)
      throws IOException;

  @POST
  @Path("batchorder")
  BatchOrderResult batchOrder(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("json") BatchOrder orderCommands)
      throws IOException;

  @POST
  @Path("cancelorder")
  CryptoFacilitiesCancel cancelOrder(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("order_id") String order_id)
      throws IOException;

  @POST
  @Path("cancelallordersafter")
  CryptoFacilitiesCancelAllOrdersAfter cancelAllOrdersAfter(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("timeout") long timeoutSeconds)
      throws IOException;

  @GET
  @Path("openorders")
  CryptoFacilitiesOpenOrders openOrders(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @GET
  @Path("fills")
  CryptoFacilitiesFills fills(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("lastFillTime") String lastFillTime)
      throws IOException;

  @GET
  @Path("openpositions")
  CryptoFacilitiesOpenPositions openPositions(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;
}
