package org.knowm.xchange.cryptodotcom;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cryptodotcom.dto.trade.cryptodotcomCancelOrderParams;
import org.knowm.xchange.cryptodotcom.dto.trade.cryptodotcomQueryOrderParams;
import org.knowm.xchange.cryptodotcom.dto.trade.cryptodotcomTradeHistoryParams;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.FundingRates;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamInstrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Ignore
public class cryptodotcomFutureTest {

    private static final Instrument instrument = new FuturesContract("BTC/USDT/PERP");

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private static Exchange cryptodotcomExchange;

    @Before
    public void setUp() throws IOException {
        Properties prop = new Properties();
        prop.load(this.getClass().getResourceAsStream("/secret.keys"));

        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(cryptodotcomExchange.class);

        ExchangeSpecification spec = exchange.getExchangeSpecification();

        spec.setApiKey(prop.getProperty("apikey"));
        spec.setSecretKey(prop.getProperty("secret"));
        spec.setExchangeSpecificParametersItem(cryptodotcomExchange.SPECIFIC_PARAM_USE_FUTURES_SANDBOX, true);
        exchange.applySpecification(spec);

        cryptodotcomExchange = exchange;
    }

    @Test
    public void cryptodotcomFutureMarketDataService() throws IOException {
        // Get OrderBook
        OrderBook orderBook = cryptodotcomExchange.getMarketDataService().getOrderBook(instrument);
        logger.info("OrderBook: "+orderBook);
        assertThat(orderBook.getBids().get(0).getInstrument()).isEqualTo(instrument);
        //Get Ticker
        Ticker ticker = cryptodotcomExchange.getMarketDataService().getTicker(instrument);
        logger.info("Ticker: "+ticker);
        assertThat(ticker.getInstrument()).isEqualTo(instrument);

        //Get Trades
        Trades trades = cryptodotcomExchange.getMarketDataService().getTrades(instrument);
        logger.info("Trades: "+trades);
        assertThat(trades.getTrades().get(0).getInstrument()).isEqualTo(instrument);
        //Get Funding rates
        FundingRates fundingRates = cryptodotcomExchange.getMarketDataService().getFundingRates();
        fundingRates.getFundingRates().forEach(fundingRate -> System.out.println(fundingRate.toString()));
    }

    @Test
    public void cryptodotcomFutureAccountService() throws IOException {

        AccountInfo accountInfo = cryptodotcomExchange.getAccountService().getAccountInfo();
        logger.info("AccountInfo: "+accountInfo.getWallet(Wallet.WalletFeature.FUTURES_TRADING));
        assertThat(accountInfo.getOpenPositions().stream().anyMatch(openPosition -> openPosition.getInstrument().equals(instrument))).isTrue();
        logger.info("Positions: "+ accountInfo.getOpenPositions());

    }

    @Test
    public void cryptodotcomFutureTradeService() throws IOException {
        Set<Order.IOrderFlags> orderFlags = new HashSet<>();
//        orderFlags.add(cryptodotcomOrderFlags.REDUCE_ONLY);

        //Open Positions
        List<OpenPosition> openPositions = cryptodotcomExchange.getTradeService().getOpenPositions().getOpenPositions();
        logger.info("Positions: "+ openPositions);
        assertThat(openPositions.stream().anyMatch(openPosition -> openPosition.getInstrument().equals(instrument))).isTrue();

        // Get UserTrades
        List<UserTrade> userTrades = cryptodotcomExchange.getTradeService().getTradeHistory(new cryptodotcomTradeHistoryParams(instrument)).getUserTrades();
        logger.info("UserTrades: "+ userTrades);
        assertThat(userTrades.stream().anyMatch(userTrade -> userTrade.getInstrument().equals(instrument))).isTrue();

        // Place LimitOrder
        String orderId = cryptodotcomExchange.getTradeService().placeLimitOrder(new LimitOrder.Builder(Order.OrderType.BID, instrument)
                        .limitPrice(BigDecimal.valueOf(1000))
                        .flags(orderFlags)
                        .originalAmount(BigDecimal.ONE)
                .build());
        // Get OpenOrders
        List<LimitOrder> openOrders = cryptodotcomExchange.getTradeService().getOpenOrders(new DefaultOpenOrdersParamInstrument(instrument)).getOpenOrders();
        logger.info("OpenOrders: "+openOrders);
        assertThat(openOrders.stream().anyMatch(openOrder -> openOrder.getInstrument().equals(instrument))).isTrue();

        // Get order
        Collection<Order> order = cryptodotcomExchange.getTradeService().getOrder(new cryptodotcomQueryOrderParams(instrument, orderId));
        logger.info("GetOrder: "+ order);
        assertThat(order.stream().anyMatch(order1 -> order1.getInstrument().equals(instrument))).isTrue();

        //Cancel LimitOrder
        logger.info("CancelOrder: "+cryptodotcomExchange.getTradeService().cancelOrder(new cryptodotcomCancelOrderParams(instrument, orderId)));
    }
}
