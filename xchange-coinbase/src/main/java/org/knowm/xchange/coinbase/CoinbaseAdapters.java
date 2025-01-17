package org.knowm.xchange.coinbase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.coinbase.dto.account.CoinbaseUser;
import org.knowm.xchange.coinbase.dto.marketdata.*;
import org.knowm.xchange.coinbase.dto.trade.CoinbaseTransfer;
import org.knowm.xchange.coinbase.dto.trade.CoinbaseTransferType;
import org.knowm.xchange.coinbase.dto.trade.CoinbaseTransfers;
import org.knowm.xchange.coinbase.v2.dto.account.transactions.CoinbaseBuySell;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.CandleStick;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;

/** jamespedwards42 */
public final class CoinbaseAdapters {

  private static final int TWENTY_FOUR_HOURS_IN_MILLIS = 1000 * 60 * 60 * 24;
  private static final int PRICE_SCALE = 10;

  private CoinbaseAdapters() {
  }

  public static AccountInfo adaptAccountInfo(CoinbaseUser user) {

    final String username = user.getEmail();
    final CoinbaseMoney money = user.getBalance();
    final Balance balance =
            new Balance(Currency.getInstance(money.getCurrency()), money.getAmount());

    final AccountInfo accountInfoTemporaryName =
            new AccountInfo(username, Wallet.Builder.from(Arrays.asList(balance)).build());
    return accountInfoTemporaryName;
  }

  public static UserTrades adaptTrades(List<CoinbaseBuySell> transactions, OrderType orderType) {
    final List<UserTrade> trades = new ArrayList<>();

    for (CoinbaseBuySell transaction : transactions) {
      trades.add(adaptTrade(transaction, orderType));
    }

    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  private static UserTrade adaptTrade(CoinbaseBuySell transaction, OrderType orderType) {
    return new UserTrade.Builder()
            .type(orderType)
            .originalAmount(transaction.getAmount().getAmount())
            .currencyPair(
                    new CurrencyPair(
                            transaction.getAmount().getCurrency(), transaction.getTotal().getCurrency()))
            .price(
                    transaction
                            .getSubTotal()
                            .getAmount()
                            .divide(transaction.getAmount().getAmount(), PRICE_SCALE, RoundingMode.HALF_UP))
            .timestamp(Date.from(transaction.getCreatedAt().toInstant()))
            .id(transaction.getId())
            .orderId(transaction.getTransaction().getId())
            .feeAmount(transaction.getFee().getAmount())
            .feeCurrency(Currency.getInstance(transaction.getFee().getCurrency()))
            .build();
  }

  public static UserTrades adaptTrades(CoinbaseTransfers transfers) {

    final List<UserTrade> trades = new ArrayList<>();
    for (CoinbaseTransfer transfer : transfers.getTransfers()) {
      trades.add(adaptTrade(transfer));
    }

    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  public static UserTrade adaptTrade(CoinbaseTransfer transfer) {

    final OrderType orderType = adaptOrderType(transfer.getType());
    final CoinbaseMoney btcAmount = transfer.getBtcAmount();
    final BigDecimal originalAmount = btcAmount.getAmount();
    final String tradableIdentifier = btcAmount.getCurrency();
    final CoinbaseMoney subTotal = transfer.getSubtotal();
    final String transactionCurrency = subTotal.getCurrency();
    final BigDecimal price = subTotal.getAmount().divide(originalAmount, RoundingMode.HALF_EVEN);
    final Date timestamp = transfer.getCreatedAt();
    final String id = transfer.getTransactionId();
    final String transferId = transfer.getId();
    final BigDecimal feeAmount = transfer.getCoinbaseFee().getAmount();
    final String feeCurrency = transfer.getCoinbaseFee().getCurrency();

    return new UserTrade.Builder()
            .type(orderType)
            .originalAmount(originalAmount)
            .currencyPair(new CurrencyPair(tradableIdentifier, transactionCurrency))
            .price(price)
            .timestamp(timestamp)
            .id(id)
            .orderId(transferId)
            .feeAmount(feeAmount)
            .feeCurrency(Currency.getInstance(feeCurrency))
            .build();
  }

  public static OrderType adaptOrderType(CoinbaseTransferType transferType) {

    switch (transferType) {
      case BUY:
        return OrderType.BID;
      case SELL:
        return OrderType.ASK;
    }
    return null;
  }

  public static Ticker adaptTicker(
          CurrencyPair currencyPair,
          final CoinbasePrice buyPrice,
          final CoinbasePrice sellPrice,
          final CoinbaseMoney spotRate,
          final CoinbaseSpotPriceHistory coinbaseSpotPriceHistory) {

    final Ticker.Builder tickerBuilder =
            new Ticker.Builder()
                    .currencyPair(currencyPair)
                    .ask(buyPrice.getSubTotal().getAmount())
                    .bid(sellPrice.getSubTotal().getAmount())
                    .last(spotRate.getAmount());

    // Get the 24 hour high and low spot price if the history is provided.
    if (coinbaseSpotPriceHistory != null) {
      BigDecimal observedHigh = spotRate.getAmount();
      BigDecimal observedLow = spotRate.getAmount();
      Date twentyFourHoursAgo = null;
      // The spot price history list is sorted in descending order by timestamp when deserialized.
      for (CoinbaseHistoricalSpotPrice historicalSpotPrice :
              coinbaseSpotPriceHistory.getSpotPriceHistory()) {

        if (twentyFourHoursAgo == null) {
          twentyFourHoursAgo =
                  new Date(historicalSpotPrice.getTimestamp().getTime() - TWENTY_FOUR_HOURS_IN_MILLIS);
        } else if (historicalSpotPrice.getTimestamp().before(twentyFourHoursAgo)) {
          break;
        }

        final BigDecimal spotPriceAmount = historicalSpotPrice.getSpotRate();
        if (spotPriceAmount.compareTo(observedLow) < 0) {
          observedLow = spotPriceAmount;
        } else if (spotPriceAmount.compareTo(observedHigh) > 0) {
          observedHigh = spotPriceAmount;
        }
      }
      tickerBuilder.high(observedHigh).low(observedLow);
    }

    return tickerBuilder.build();
  }

  public static String convertToCoinbaseSymbol(String instrumentName) {
    return instrumentName.replace("/", "-").toUpperCase();
  }

  public static CandleStickData adaptBinanceCandleStickData(List<ArrayList<Object>> klines, CurrencyPair currencyPair, KlineInterval interval) {
    CandleStickData candleStickData = null;
    if (klines != null && !klines.isEmpty()) {
      List<CandleStick> candleSticks = new ArrayList<>();
      klines.forEach(a ->  {
                candleSticks.add(
                        new CandleStick.Builder()
                                .timestamp(new Date(Long.parseLong(a.get(0).toString()+ "000") +  interval.getMillis()  - 1))
                                .low(new BigDecimal(a.get(1).toString()))
                                .high(new BigDecimal(a.get(2).toString()))
                                .open(new BigDecimal(a.get(3).toString()))
                                .close(new BigDecimal(a.get(4).toString()))
                                .volume(new BigDecimal(a.get(5).toString()))
                                .build());
              }
      );
      candleStickData = new CandleStickData(currencyPair, candleSticks);
    }
    return candleStickData;
  }
}
