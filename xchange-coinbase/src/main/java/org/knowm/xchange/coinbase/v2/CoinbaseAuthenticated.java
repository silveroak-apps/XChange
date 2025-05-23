package org.knowm.xchange.coinbase.v2;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.coinbase.v2.dto.CoinbaseException;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseAccountData;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseAccountsData;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseBuyData;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbasePaymentMethodsData;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseSellData;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseTransactionsResponse;
import org.knowm.xchange.coinbase.v2.dto.account.transactions.CoinbaseBuySellResponse;
import si.mazi.rescu.ParamsDigest;

@Path("/v2")
@Produces(MediaType.APPLICATION_JSON)
public interface CoinbaseAuthenticated extends Coinbase {

  /**
   * All API key requests must be signed and contain the following headers.
   *
   * <p>All request bodies should have content type application/json and be valid JSON.
   *
   * <p>The CB-ACCESS-SIGN header is generated by creating a sha256 HMAC using the secret key on the
   * prehash string timestamp + method + requestPath + body (where + represents string
   * concatenation). The timestamp value is the same as the CB-ACCESS-TIMESTAMP header.
   *
   * <p>The body is the request body string or omitted if there is no request body (typically for
   * GET requests).
   *
   * <p>The method should be UPPER CASE.
   *
   * <p><a
   * href="https://developers.coinbase.com/api/v2#api-key">developers.coinbase.com/api/v2#api-key</a>
   */
  String CB_ACCESS_KEY = "CB-ACCESS-KEY";

  String CB_ACCESS_SIGN = "CB-ACCESS-SIGN";
  String CB_ACCESS_TIMESTAMP = "CB-ACCESS-TIMESTAMP";

  String CONTENT_TYPE = "Content-Type";

  @GET
  @Path("accounts/{accountId}/transactions")
  CoinbaseTransactionsResponse getTransactions(
      @HeaderParam(CB_VERSION) String apiVersion,
      @HeaderParam(CB_ACCESS_KEY) String apiKey,
      @HeaderParam(CB_ACCESS_SIGN) ParamsDigest signature,
      @HeaderParam(CB_ACCESS_TIMESTAMP) BigDecimal timestamp,
      @PathParam("accountId") String accountId)
      throws IOException, CoinbaseException;

  @GET
  @Path("accounts/{accountId}/buys")
  CoinbaseBuySellResponse getBuys(
      @HeaderParam(CB_VERSION) String apiVersion,
      @HeaderParam(CB_ACCESS_KEY) String apiKey,
      @HeaderParam(CB_ACCESS_SIGN) CoinbaseV2Digest signature,
      @HeaderParam(CB_ACCESS_TIMESTAMP) BigDecimal timestamp,
      @PathParam("accountId") String accountId,
      @QueryParam("limit") Integer limit,
      @QueryParam("starting_after") String startingAfter)
      throws IOException, CoinbaseException;

  @GET
  @Path("accounts/{accountId}/sells")
  CoinbaseBuySellResponse getSells(
      @HeaderParam(CB_VERSION) String apiVersion,
      @HeaderParam(CB_ACCESS_KEY) String apiKey,
      @HeaderParam(CB_ACCESS_SIGN) CoinbaseV2Digest signature,
      @HeaderParam(CB_ACCESS_TIMESTAMP) BigDecimal timestamp,
      @PathParam("accountId") String accountId,
      @QueryParam("limit") Integer limit,
      @QueryParam("starting_after") String startingAfter)
      throws IOException, CoinbaseException;

  @GET
  @Path("accounts/{accountId}/deposits")
  Map getDeposits(
      @HeaderParam(CB_VERSION) String apiVersion,
      @HeaderParam(CB_ACCESS_KEY) String apiKey,
      @HeaderParam(CB_ACCESS_SIGN) ParamsDigest signature,
      @HeaderParam(CB_ACCESS_TIMESTAMP) BigDecimal timestamp,
      @PathParam("accountId") String accountId)
      throws IOException, CoinbaseException;

  @GET
  @Path("accounts/{accountId}/withdrawals")
  Map getWithdrawals(
      @HeaderParam(CB_VERSION) String apiVersion,
      @HeaderParam(CB_ACCESS_KEY) String apiKey,
      @HeaderParam(CB_ACCESS_SIGN) ParamsDigest signature,
      @HeaderParam(CB_ACCESS_TIMESTAMP) BigDecimal timestamp,
      @PathParam("accountId") String accountId)
      throws IOException, CoinbaseException;

  @GET
  @Path("accounts")
  CoinbaseAccountsData getAccounts(
      @HeaderParam(CB_VERSION) String apiVersion,
      @HeaderParam(CB_ACCESS_KEY) String apiKey,
      @HeaderParam(CB_ACCESS_SIGN) ParamsDigest signature,
      @HeaderParam(CB_ACCESS_TIMESTAMP) BigDecimal timestamp,
      @QueryParam("limit") Integer limit,
      @QueryParam("starting_after") String starting_after)
      throws IOException, CoinbaseException;

  @GET
  @Path("accounts/{currency}")
  CoinbaseAccountData getAccount(
      @HeaderParam(CB_VERSION) String apiVersion,
      @HeaderParam(CB_ACCESS_KEY) String apiKey,
      @HeaderParam(CB_ACCESS_SIGN) CoinbaseV2Digest signature,
      @HeaderParam(CB_ACCESS_TIMESTAMP) BigDecimal timestamp,
      @PathParam("currency") String currency)
      throws IOException, CoinbaseException;

  @POST
  @Path("accounts")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseAccountData createAccount(
      @HeaderParam(CONTENT_TYPE) String contentType,
      @HeaderParam(CB_VERSION) String apiVersion,
      @HeaderParam(CB_ACCESS_KEY) String apiKey,
      @HeaderParam(CB_ACCESS_SIGN) String signature,
      @HeaderParam(CB_ACCESS_TIMESTAMP) BigDecimal timestamp,
      Object payload)
      throws IOException, CoinbaseException;

  @GET
  @Path("payment-methods")
  CoinbasePaymentMethodsData getPaymentMethods(
      @HeaderParam(CB_VERSION) String apiVersion,
      @HeaderParam(CB_ACCESS_KEY) String apiKey,
      @HeaderParam(CB_ACCESS_SIGN) CoinbaseV2Digest signature,
      @HeaderParam(CB_ACCESS_TIMESTAMP) BigDecimal timestamp)
      throws IOException, CoinbaseException;

  @POST
  @Path("accounts/{account}/buys")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseBuyData buy(
      @HeaderParam(CONTENT_TYPE) String contentType,
      @HeaderParam(CB_VERSION) String apiVersion,
      @HeaderParam(CB_ACCESS_KEY) String apiKey,
      @HeaderParam(CB_ACCESS_SIGN) String signature,
      @HeaderParam(CB_ACCESS_TIMESTAMP) BigDecimal timestamp,
      @PathParam("account") String accountId,
      Object payload)
      throws IOException, CoinbaseException;

  @POST
  @Path("accounts/{account}/sells")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseSellData sell(
      @HeaderParam(CONTENT_TYPE) String contentType,
      @HeaderParam(CB_VERSION) String apiVersion,
      @HeaderParam(CB_ACCESS_KEY) String apiKey,
      @HeaderParam(CB_ACCESS_SIGN) String signature,
      @HeaderParam(CB_ACCESS_TIMESTAMP) BigDecimal timestamp,
      @PathParam("account") String accountId,
      Object payload)
      throws IOException, CoinbaseException;
}
