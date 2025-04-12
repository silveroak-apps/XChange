package org.knowm.xchange.coinmarketcap.pro.v1.cmcexchange.dto;

import java.util.Date;

/**
 * Data object representing exchange metadata from CoinMarketCap
 */
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
      int id,
      String name,
      String slug,
      boolean isActive,
      int status,
      int rank,
      Date firstHistoricalData,
      Date lastHistoricalData) {
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