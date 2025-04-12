package org.knowm.xchange.coinmarketcap.pro.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

/**
 * Data object representing exchange metadata from CoinMarketCap
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmcExchangeMetaData {

  private final int id;
  private final String name;
  private final String slug;
  private final boolean isActive;
  private final int status;
  private final int rank;
  private final Date firstHistoricalData;
  private final Date lastHistoricalData;

  public CmcExchangeMetaData(
      @JsonProperty("id") int id,
      @JsonProperty("name") String name,
      @JsonProperty("slug") String slug,
      @JsonProperty("is_active") boolean isActive,
      @JsonProperty("status") int status,
      @JsonProperty("rank") int rank,
      @JsonProperty("first_historical_data") Date firstHistoricalData,
      @JsonProperty("last_historical_data") Date lastHistoricalData) {
    this.id = id;
    this.name = name;
    this.slug = slug;
    this.isActive = isActive;
    this.status = status;
    this.rank = rank;
    this.firstHistoricalData = firstHistoricalData;
    this.lastHistoricalData = lastHistoricalData;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getSlug() {
    return slug;
  }

  public boolean isActive() {
    return isActive;
  }

  public int getStatus() {
    return status;
  }

  public int getRank() {
    return rank;
  }

  public Date getFirstHistoricalData() {
    return firstHistoricalData;
  }

  public Date getLastHistoricalData() {
    return lastHistoricalData;
  }

  @Override
  public String toString() {
    return "CmcExchangeMetaData{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", slug='" + slug + '\'' +
        ", isActive=" + isActive +
        ", status=" + status +
        ", rank=" + rank +
        ", firstHistoricalData=" + firstHistoricalData +
        ", lastHistoricalData=" + lastHistoricalData +
        '}';
  }
}