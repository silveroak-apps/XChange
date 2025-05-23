/*
 * The MIT License
 *
 * Copyright 2015-2016 Coinmate.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.knowm.xchange.coinmate;

import java.io.IOException;
import java.math.BigDecimal;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.coinmate.dto.account.CoinmateBalance;
import org.knowm.xchange.coinmate.dto.account.CoinmateDepositAddresses;
import org.knowm.xchange.coinmate.dto.account.CoinmateTradingFeesResponse;
import org.knowm.xchange.coinmate.dto.trade.*;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author Martin Stachon */
@Path("api")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface CoinmateAuthenticated extends Coinmate {

  // acount info
  @POST
  @Path("balances")
  CoinmateBalance getBalances(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @POST
  @Path("traderFees")
  CoinmateTradingFeesResponse traderFees(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("currencyPair") String currencyPair)
      throws IOException;

  // trade
  @POST
  @Path("transactionHistory")
  CoinmateTransactionHistory getTransactionHistory(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("offset") int offset,
      @FormParam("limit") Integer limit,
      @FormParam("sort") String sort,
      @FormParam("timestampFrom") Long timestampFrom,
      @FormParam("timestampTo") Long timestampTo,
      @FormParam("orderId") String orderId)
      throws IOException;

  @POST
  @Path("openOrders")
  CoinmateOpenOrders getOpenOrders(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("currencyPair") String currencyPair)
      throws IOException;

  @POST
  @Path("orderById")
  CoinmateOrder getOrderById(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("orderId") String orderId)
      throws IOException;

  @POST
  @Path("order")
  CoinmateOrders getOrderByClientOrderId(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("clientOrderId") String clientOrderId)
      throws IOException;

  @POST
  @Path("cancelOrder")
  CoinmateCancelOrderResponse cancelOder(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("orderId") String orderId)
      throws IOException;

  // new in version 1.3
  @POST
  @Path("cancelOrderWithInfo")
  CoinmateCancelOrderWithInfoResponse cancelOderWithInfo(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("orderId") String orderId)
      throws IOException;

  @POST
  @Path("buyLimit")
  CoinmateTradeResponse buyLimit(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price,
      @FormParam("currencyPair") String currencyPair,
      @FormParam("stopPrice") BigDecimal stopPrice,
      @FormParam("hidden") Integer hidden,
      @FormParam("postOnly") Integer postOnly,
      @FormParam("immediateOrCancel") Integer immediateOrCancel,
      @FormParam("trailing") Integer trailing,
      @FormParam("clientOrderId") String clientOrderId)
      throws IOException;

  @POST
  @Path("sellLimit")
  CoinmateTradeResponse sellLimit(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price,
      @FormParam("currencyPair") String currencyPair,
      @FormParam("stopPrice") BigDecimal stopPrice,
      @FormParam("hidden") Integer hidden,
      @FormParam("postOnly") Integer postOnly,
      @FormParam("immediateOrCancel") Integer immediateOrCancel,
      @FormParam("trailing") Integer trailing,
      @FormParam("clientOrderId") String clientOrderId)
      throws IOException;

  @POST
  @Path("buyInstant")
  CoinmateTradeResponse buyInstant(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("total") BigDecimal total,
      @FormParam("currencyPair") String currencyPair,
      @FormParam("clientOrderId") String clientOrderId)
      throws IOException;

  @POST
  @Path("sellInstant")
  CoinmateTradeResponse sellInstant(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("currencyPair") String currencyPair,
      @FormParam("clientOrderId") String clientOrderId)
      throws IOException;

  @POST
  @Path("buyQuick")
  CoinmateTradeResponse buyQuick(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("clientOrderId") String clientOrderId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("total") BigDecimal total,
      @FormParam("currencyPair") String currencyPair)
      throws IOException;

  @POST
  @Path("sellQuick")
  CoinmateTradeResponse sellQuick(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("clientOrderId") String clientOrderId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("currencyPair") String currencyPair)
      throws IOException;

  @POST
  @Path("buyQuickFixRate")
  CoinmateBuyFixRateResponse buyQuickFixRate(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("total") BigDecimal total,
      @FormParam("amountReceived") BigDecimal amountReceived,
      @FormParam("currencyPair") String currencyPair)
      throws IOException;

  @POST
  @Path("sellQuickFixRate")
  CoinmateSellFixRateResponse sellQuickFixRate(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("totalReceived") BigDecimal totalReceived,
      @FormParam("currencyPair") String currencyPair)
      throws IOException;

  @POST
  @Path("buyQuickFix")
  CoinmateTradeResponse buyQuickFix(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("clientOrderId") String clientOrderId,
      @FormParam("rateId") String rateId)
      throws IOException;

  @POST
  @Path("sellQuickFix")
  CoinmateTradeResponse sellQuickFix(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("clientOrderId") String clientOrderId,
      @FormParam("rateId") String rateId)
      throws IOException;

  // withdrawal and deposits
  // bitcoin
  @POST
  @Path("bitcoinWithdrawal")
  CoinmateTradeResponse bitcoinWithdrawal(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws IOException;

  @POST
  @Path("bitcoinDepositAddresses")
  CoinmateDepositAddresses bitcoinDepositAddresses(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  // litecoin
  @POST
  @Path("litecoinWithdrawal")
  CoinmateTradeResponse litecoinWithdrawal(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws IOException;

  @POST
  @Path("litecoinDepositAddresses")
  CoinmateDepositAddresses litecoinDepositAddresses(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  // ethereum

  @POST
  @Path("ethereumWithdrawal")
  CoinmateTradeResponse ethereumWithdrawal(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws IOException;

  @POST
  @Path("ethereumDepositAddresses")
  CoinmateDepositAddresses ethereumDepositAddresses(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  // ripple

  @POST
  @Path("rippleWithdrawal")
  CoinmateTradeResponse rippleWithdrawal(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws IOException;

  @POST
  @Path("rippleDepositAddresses")
  CoinmateDepositAddresses rippleDepositAddresses(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  // dash

  @POST
  @Path("dashWithdrawal")
  CoinmateTradeResponse dashWithdrawal(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws IOException;

  @POST
  @Path("dashDepositAddresses")
  CoinmateDepositAddresses dashDepositAddresses(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @POST
  @Path("adaWithdrawal")
  CoinmateTradeResponse adaWithdrawal(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws IOException;

  @POST
  @Path("adaDepositAddresses")
  CoinmateDepositAddresses adaDepositAddresses(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @POST
  @Path("solWithdrawal")
  CoinmateTradeResponse solWithdrawal(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws IOException;

  @POST
  @Path("solDepositAddresses")
  CoinmateDepositAddresses solDepositAddresses(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  // new in version 1.5
  // trade
  @POST
  @Path("orderHistory")
  CoinmateOrderHistory getOrderHistory(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("currencyPair") String currencyPair,
      @FormParam("limit") Integer limit)
      throws IOException;

  @POST
  @Path("tradeHistory")
  CoinmateTradeHistory getTradeHistory(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("limit") Integer limit,
      @FormParam("lastId") String lastId,
      @FormParam("sort") String sort,
      @FormParam("timestampFrom") Long timestampFrom,
      @FormParam("timestampTo") Long timestampTo,
      @FormParam("currencyPair") String currencyPair,
      @FormParam("orderId") String orderId)
      throws IOException;

  @POST
  @Path("transferHistory")
  CoinmateTransferHistory getTransferHistory(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("limit") Integer limit,
      @FormParam("lastId") Integer lastId,
      @FormParam("sort") String sort,
      @FormParam("timestampFrom") Long timestampFrom,
      @FormParam("timestampTo") Long timestampTo,
      @FormParam("currency") String currency)
      throws IOException;

  @POST
  @Path("replaceByBuyLimit")
  CoinmateReplaceResponse replaceByBuyLimit(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price,
      @FormParam("currencyPair") String currencyPair,
      @FormParam("orderIdToBeReplaced") String orderIdToBeReplaced,
      @FormParam("stopPrice") BigDecimal stopPrice,
      @FormParam("hidden") Integer hidden,
      @FormParam("postOnly") Integer postOnly,
      @FormParam("immediateOrCancel") Integer immediateOrCancel,
      @FormParam("trailing") Integer trailing,
      @FormParam("clientOrderId") String clientOrderId)
      throws IOException;

  @POST
  @Path("replaceBySellLimit")
  CoinmateReplaceResponse replaceBySellLimit(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price,
      @FormParam("currencyPair") String currencyPair,
      @FormParam("orderIdToBeReplaced") String orderIdToBeReplaced,
      @FormParam("stopPrice") BigDecimal stopPrice,
      @FormParam("hidden") Integer hidden,
      @FormParam("postOnly") Integer postOnly,
      @FormParam("immediateOrCancel") Integer immediateOrCancel,
      @FormParam("trailing") Integer trailing,
      @FormParam("clientOrderId") String clientOrderId)
      throws IOException;

  @POST
  @Path("replaceByBuyInstant")
  CoinmateReplaceResponse replaceByBuyInstant(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("total") BigDecimal total,
      @FormParam("currencyPair") String currencyPair,
      @FormParam("orderIdToBeReplaced") String orderIdToBeReplaced,
      @FormParam("clientOrderId") String clientOrderId)
      throws IOException;

  @POST
  @Path("replaceBySellInstant")
  CoinmateReplaceResponse replaceBySellInstant(
      @FormParam("publicKey") String publicKey,
      @FormParam("clientId") String clientId,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("currencyPair") String currencyPair,
      @FormParam("orderIdToBeReplaced") String orderIdToBeReplaced,
      @FormParam("clientOrderId") String clientOrderId)
      throws IOException;
}
