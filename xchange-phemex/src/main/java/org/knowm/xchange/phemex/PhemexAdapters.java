package org.knowm.xchange.phemex;

import org.knowm.xchange.phemex.dto.PhemexKlineInterval;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStick;
import org.knowm.xchange.dto.marketdata.CandleStickData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/** jamespedwards42 */
public final class PhemexAdapters {

  private PhemexAdapters() {
  }

  /**
   * "rows": [
   *       [
   *         <timestamp>,
   *         <interval>,
   *         <last_close>,
   *         <open>,
   *         <high>,
   *         <low>,
   *         <close>,
   *         <volume>,
   *         <turnover>
   *       ],
   *       [
   *         ...
   *       ]
   *       Scaled pricing https://github.com/phemex/phemex-api-docs/blob/master/Public-Contract-API-en.md#priceratiovalue-scales
   * @param klines
   * @param currencyPair
   * @param interval
   * @return
   */
  public static CandleStickData adaptCryptodotcomCandleStickData(LinkedHashMap klines, CurrencyPair currencyPair, PhemexKlineInterval interval) {
    CandleStickData candleStickData = null;
    if (klines != null) {
      List<CandleStick> candleSticks = new ArrayList<>();
      ((ArrayList)((LinkedHashMap)klines.get("data")).get("rows")).forEach(a ->  {
                ArrayList val = (ArrayList) a;
                candleSticks.add(
                      //Dividing by 10000 is to scale the price to 4 decimal places in this exchange
                        new CandleStick.Builder()
                                .timestamp(new Date(Long.valueOf((Integer)val.get(0))*1000 + interval.getMillis() -1))
                                .open(BigDecimal.valueOf((Integer) val.get(3)/10000))
                                .high(BigDecimal.valueOf((Integer) val.get(4)/10000))
                                .low(BigDecimal.valueOf((Integer) val.get(5)/10000))
                                .close(BigDecimal.valueOf((Integer) val.get(6)/10000))
                                .volume(BigDecimal.valueOf((Integer) val.get(7)/10000))
                                .build());
              }
      );

      candleStickData = new CandleStickData(currencyPair, candleSticks);
    }
    return candleStickData;
  }

  public static String convertToCryptodotomSymbol(String symbol) {
    return symbol.replace("/", "");
  }
}
