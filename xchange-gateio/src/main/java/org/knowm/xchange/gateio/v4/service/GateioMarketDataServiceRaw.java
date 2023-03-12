package org.knowm.xchange.gateio.v4.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.GateioUtils;
import org.knowm.xchange.gateio.dto.GateioBaseResponse;
import org.knowm.xchange.gateio.dto.marketdata.*;
import org.knowm.xchange.gateio.v4.Gateio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GateioMarketDataServiceRaw {

    protected final Gateio bter;

    /**
     * Constructor
     *
     * @param exchange
     */
    public GateioMarketDataServiceRaw(Exchange exchange) {
        this.bter =
                ExchangeRestProxyBuilder.forInterface(
                                Gateio.class, exchange.getExchangeSpecification())
                        .build();
    }

    protected <R extends GateioBaseResponse> R handleResponse(R response) {

        if (!response.isResult()) {
            throw new ExchangeException(response.getMessage());
        }

        return response;
    }

    //Time in seconds
    public List<ArrayList<Object>> getKlines(CurrencyPair pair, String interval, Long from, Long to)
            throws IOException {
        return bter.getKlinesSpot(
                GateioAdapters.toV4PairString(pair),
                from/1000,
                to/1000,
                interval);
    }
}
