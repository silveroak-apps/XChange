package org.knowm.xchange.coinmarketcap.pro.v1.cmcexchange.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Response object for CoinMarketCap exchanges data from the direct URL
 * https://s3.coinmarketcap.com/generated/core/exchange/exchanges.json
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmcExchangesResponse {

  private final List<String> fields;
  private final List<List<Object>> values;
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

  static {
    DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  public CmcExchangesResponse(
      @JsonProperty("fields") List<String> fields,
      @JsonProperty("values") List<List<Object>> values) {
    this.fields = fields;
    this.values = values;
  }

  public List<String> getFields() {
    return fields;
  }

  public List<List<Object>> getValues() {
    return values;
  }

  /**
   * Converts the raw response data to a list of CmcExchangeMetaData objects
   * 
   * @return List of CmcExchangeMetaData objects
   */
  public List<CmcExchangeMetaData> toCmcExchangeMetaDataList() {
    List<CmcExchangeMetaData> result = new ArrayList<>();
    
    if (fields == null || values == null) {
      return result;
    }
    
    // Find the index of each field
    int idIndex = fields.indexOf("id");
    int nameIndex = fields.indexOf("name");
    int slugIndex = fields.indexOf("slug");
    int isActiveIndex = fields.indexOf("is_active");
    int statusIndex = fields.indexOf("status");
    int rankIndex = fields.indexOf("rank");
    int firstHistoricalDataIndex = fields.indexOf("first_historical_data");
    int lastHistoricalDataIndex = fields.indexOf("last_historical_data");
    
    // Process each value row
    for (List<Object> row : values) {
      if (row.size() < fields.size()) {
        continue; // Skip incomplete rows
      }
      
      try {
        int id = ((Number) row.get(idIndex)).intValue();
        String name = (String) row.get(nameIndex);
        String slug = (String) row.get(slugIndex);
        boolean isActive = ((Number) row.get(isActiveIndex)).intValue() == 1;
        int status = ((Number) row.get(statusIndex)).intValue();
        int rank = ((Number) row.get(rankIndex)).intValue();
        
        Date firstHistoricalData = null;
        Date lastHistoricalData = null;
        
        if (firstHistoricalDataIndex >= 0 && row.get(firstHistoricalDataIndex) != null) {
          try {
            firstHistoricalData = DATE_FORMAT.parse((String) row.get(firstHistoricalDataIndex));
          } catch (ParseException e) {
            // Ignore parsing errors
          }
        }
        
        if (lastHistoricalDataIndex >= 0 && row.get(lastHistoricalDataIndex) != null) {
          try {
            lastHistoricalData = DATE_FORMAT.parse((String) row.get(lastHistoricalDataIndex));
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
    
    return result;
  }
}