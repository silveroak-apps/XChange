package org.knowm.xchange.coinmarketcap.pro.v1.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map; 
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmarketcap.pro.v1.CmcErrorAdapter;
import org.knowm.xchange.coinmarketcap.pro.v1.cmcexchange.dto.CmcExchangeMetaData;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CmcCurrency;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CmcCurrencyInfo;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CmcTicker;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response.CmcCurrencyInfoResponse;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response.CmcCurrencyMapResponse;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response.CmcTickerListResponse;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response.CmcTickerResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import si.mazi.rescu.HttpStatusIOException;

class CmcMarketDataServiceRaw extends CmcBaseService {

  public CmcMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public CmcCurrencyInfo getCmcCurrencyInfo(Currency currency) throws IOException {

    String currencyCode = currency.getCurrencyCode();

    CmcCurrencyInfoResponse response = null;
    try {
      response = cmcAuthenticated.getCurrencyInfo(apiKey, currencyCode);
    } catch (HttpStatusIOException ex) {
      CmcErrorAdapter.adapt(ex);
    }

    return response.getData().get(currencyCode);
  }

  public Map<String, CmcCurrencyInfo> getCmcMultipleCurrencyInfo(List<Currency> currencyList)
      throws IOException {

    List<String> currencyCodes =
        currencyList.stream().map(Currency::getCurrencyCode).collect(Collectors.toList());
    String commaSeparatedCurrencyCodes = StringUtils.join(currencyCodes, ",");

    CmcCurrencyInfoResponse response = null;
    try {
      response = cmcAuthenticated.getCurrencyInfo(apiKey, commaSeparatedCurrencyCodes);
    } catch (HttpStatusIOException ex) {
      CmcErrorAdapter.adapt(ex);
    }

    return response.getData();
  }

  public List<CmcCurrency> getCmcCurrencyList() throws IOException {

    CmcCurrencyMapResponse response = null;
    try {
      response = cmcAuthenticated.getCurrencyMap(apiKey, "active", 1, 5000, "id");
    } catch (HttpStatusIOException ex) {
      CmcErrorAdapter.adapt(ex);
    }

    return response.getData();
  }

  public List<CmcTicker> getCmcLatestDataForAllCurrencies() throws IOException {

    CmcTickerListResponse response = null;
    try {
      response =
          cmcAuthenticated.getLatestListing(
              apiKey, 1, 5000, Currency.USD.getCurrencyCode(), "symbol", "asc", "all");
    } catch (HttpStatusIOException ex) {
      CmcErrorAdapter.adapt(ex);
    }

    return response.getData();
  }

  public List<CmcTicker> getCmcLatestDataForAllCurrencies(
      int startIndex,
      int limitIndex,
      String currencyCounters,
      String sortByField,
      String sortDirection,
      String currencyType)
      throws IOException {

    CmcTickerListResponse response = null;
    try {
      response =
          cmcAuthenticated.getLatestListing(
              apiKey,
              startIndex,
              limitIndex,
              currencyCounters,
              sortByField,
              sortDirection,
              currencyType);
    } catch (HttpStatusIOException ex) {
      CmcErrorAdapter.adapt(ex);
    }

    return response.getData();
  }

  public Map<String, CmcTicker> getCmcLatestQuote(CurrencyPair currencyPair) throws IOException {

    CmcTickerResponse response = null;
    try {
      response =
          cmcAuthenticated.getLatestQuotes(
              apiKey, currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
    } catch (HttpStatusIOException ex) {
      CmcErrorAdapter.adapt(ex);
    }

    return response.getData();
  }

  public Map<String, CmcTicker> getCmcLatestQuotes(
      Set<Currency> baseCurrencySet, Set<Currency> convertCurrencySet) throws IOException {

    List<String> baseSymbols =
        baseCurrencySet.stream().map(c -> c.getCurrencyCode()).collect(Collectors.toList());
    String commaSeparatedBaseSymbols = StringUtils.join(baseSymbols, ",");
    List<String> convertSymbols =
        convertCurrencySet.stream().map(c -> c.getCurrencyCode()).collect(Collectors.toList());
    String commaSeparatedConvertCurrencies = StringUtils.join(convertSymbols, ",");

    CmcTickerResponse response = null;
    try {
      response =
          cmcAuthenticated.getLatestQuotes(
              apiKey, commaSeparatedBaseSymbols, commaSeparatedConvertCurrencies);
    } catch (HttpStatusIOException ex) {
      CmcErrorAdapter.adapt(ex);
    }

    return response.getData();
  }

  /**
   * Fetches exchange data directly from CoinMarketCap's S3 bucket
   *
   * @return List of CmcExchangeMetaData objects
   * @throws IOException if there's an error fetching or parsing the data
   */
  public List<CmcExchangeMetaData> getCmcExchanges() throws IOException {
    List<CmcExchangeMetaData> result = new ArrayList<>();
    
    try {
      // Direct URL to CoinMarketCap's exchanges data
      URL url = new URL("https://s3.coinmarketcap.com/generated/core/exchange/exchanges.json");
      
      // Parse the JSON response
      ObjectMapper mapper = new ObjectMapper();
      JsonNode root = mapper.readTree(url);
      
      // Get the fields and values arrays
      JsonNode fieldsNode = root.get("fields");
      JsonNode valuesNode = root.get("values");
      
      if (fieldsNode == null || valuesNode == null || !fieldsNode.isArray() || !valuesNode.isArray()) {
        throw new IOException("Invalid response format from CoinMarketCap");
      }
      
      // Find the index of each field
      int idIndex = -1;
      int nameIndex = -1;
      int slugIndex = -1;
      int isActiveIndex = -1;
      int statusIndex = -1;
      int rankIndex = -1;
      int firstHistoricalDataIndex = -1;
      int lastHistoricalDataIndex = -1;
      
      for (int i = 0; i < fieldsNode.size(); i++) {
        String field = fieldsNode.get(i).asText();
        switch (field) {
          case "id":
            idIndex = i;
            break;
          case "name":
            nameIndex = i;
            break;
          case "slug":
            slugIndex = i;
            break;
          case "is_active":
            isActiveIndex = i;
            break;
          case "status":
            statusIndex = i;
            break;
          case "rank":
            rankIndex = i;
            break;
          case "first_historical_data":
            firstHistoricalDataIndex = i;
            break;
          case "last_historical_data":
            lastHistoricalDataIndex = i;
            break;
        }
      }
      
      // Date format for parsing timestamps
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
      dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      
      // Process each value row
      for (int i = 0; i < valuesNode.size(); i++) {
        JsonNode row = valuesNode.get(i);
        if (!row.isArray() || row.size() < fieldsNode.size()) {
          continue; // Skip incomplete rows
        }
        
        try {
          int id = row.get(idIndex).asInt();
          String name = row.get(nameIndex).asText();
          String slug = row.get(slugIndex).asText();
          boolean isActive = row.get(isActiveIndex).asInt() == 1;
          int status = row.get(statusIndex).asInt();
          int rank = row.get(rankIndex).asInt();
          
          Date firstHistoricalData = null;
          Date lastHistoricalData = null;
          
          if (firstHistoricalDataIndex >= 0 && !row.get(firstHistoricalDataIndex).isNull()) {
            try {
              firstHistoricalData = dateFormat.parse(row.get(firstHistoricalDataIndex).asText());
            } catch (ParseException e) {
              // Ignore parsing errors
            }
          }
          
          if (lastHistoricalDataIndex >= 0 && !row.get(lastHistoricalDataIndex).isNull()) {
            try {
              lastHistoricalData = dateFormat.parse(row.get(lastHistoricalDataIndex).asText());
            } catch (ParseException e) {
              // Ignore parsing errors
            }
          }
          
          result.add(new CmcExchangeMetaData(
              id, name, slug, isActive, status, rank, firstHistoricalData, lastHistoricalData));
        } catch (Exception e) {
          // Skip rows with invalid data
        }
      }
    } catch (Exception e) {
      throw new IOException("Error fetching exchange data from CoinMarketCap", e);
    }
    
    return result;
  }
}
