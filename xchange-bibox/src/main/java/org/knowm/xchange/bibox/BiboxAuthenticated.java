package org.knowm.xchange.bibox;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.bibox.dto.BiboxMultipleResponses;
import org.knowm.xchange.bibox.dto.BiboxPagedResponses;
import org.knowm.xchange.bibox.dto.BiboxSingleResponse;
import org.knowm.xchange.bibox.dto.account.BiboxAssetsResult;
import org.knowm.xchange.bibox.dto.account.BiboxDeposit;
import org.knowm.xchange.bibox.dto.account.BiboxWithdrawal;
import org.knowm.xchange.bibox.dto.trade.BiboxOrders;
import si.mazi.rescu.ParamsDigest;

/** @author odrotleff */
@Path("v1")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface BiboxAuthenticated extends Bibox {

  /**
   * Retrieve balances of the account
   *
   * @return list of coins
   */
  @POST
  @Path("transfer")
  BiboxSingleResponse<BiboxAssetsResult> assets(
      @FormParam(FORM_CMDS) String cmds,
      @FormParam(FORM_APIKEY) String apiKey,
      @FormParam(FORM_SIGNATURE) ParamsDigest signature);

  /**
   * Retrieve deposits
   *
   * @return list of deposits
   */
  @POST
  @Path("transfer")
  BiboxPagedResponses<BiboxDeposit> transferInList(
      @FormParam(FORM_CMDS) String cmds,
      @FormParam(FORM_APIKEY) String apiKey,
      @FormParam(FORM_SIGNATURE) ParamsDigest signature);

  /**
   * Retrieve withdrawals
   *
   * @return list of withdrawals
   */
  @POST
  @Path("transfer")
  BiboxPagedResponses<BiboxWithdrawal> transferOutList(
      @FormParam(FORM_CMDS) String cmds,
      @FormParam(FORM_APIKEY) String apiKey,
      @FormParam(FORM_SIGNATURE) ParamsDigest signature);

  /**
   * Execute withdrawals
   *
   * @return withdrawal id
   */
  @POST
  @Path("transfer")
  BiboxSingleResponse<String> transfer(
      @FormParam(FORM_CMDS) String cmds,
      @FormParam(FORM_APIKEY) String apiKey,
      @FormParam(FORM_SIGNATURE) ParamsDigest signature);

  /**
   * Get deposit address for coin
   *
   * @return list of coins
   */
  @POST
  @Path("transfer")
  BiboxSingleResponse<String> depositAddress(
      @FormParam(FORM_CMDS) String cmds,
      @FormParam(FORM_APIKEY) String apiKey,
      @FormParam(FORM_SIGNATURE) ParamsDigest signature);

  /**
   * Create an order (market/limit)
   *
   * @return order id
   */
  @POST
  @Path("orderpending")
  BiboxSingleResponse<String> trade(
      @FormParam(FORM_CMDS) String cmds,
      @FormParam(FORM_APIKEY) String apiKey,
      @FormParam(FORM_SIGNATURE) ParamsDigest signature);

  /**
   * Cancel an order
   *
   * @return chinese for "cancelled", useless
   */
  @POST
  @Path("orderpending")
  BiboxSingleResponse<String> cancelTrade(
      @FormParam(FORM_CMDS) String cmds,
      @FormParam(FORM_APIKEY) String apiKey,
      @FormParam(FORM_SIGNATURE) ParamsDigest signature);

  /**
   * Obtain open order list / order history
   *
   * @return open orders
   */
  @POST
  @Path("orderpending")
  BiboxSingleResponse<BiboxOrders> orderPendingList(
      @FormParam(FORM_CMDS) String cmds,
      @FormParam(FORM_APIKEY) String apiKey,
      @FormParam(FORM_SIGNATURE) ParamsDigest signature);

  /**
   * Cancel multiple orders
   *
   * @return multiple times chinese for "cancelled", useless
   */
  @POST
  @Path("orderpending")
  BiboxMultipleResponses<String> cancelTrades(
      @FormParam(FORM_CMDS) String cmds,
      @FormParam(FORM_APIKEY) String apiKey,
      @FormParam(FORM_SIGNATURE) ParamsDigest signature);
}
