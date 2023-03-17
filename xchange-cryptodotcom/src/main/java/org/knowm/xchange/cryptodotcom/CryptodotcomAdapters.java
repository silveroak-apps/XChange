package org.knowm.xchange.cryptodotcom;

import org.knowm.xchange.cryptodotcom.dto.KlineInterval;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStick;
import org.knowm.xchange.dto.marketdata.CandleStickData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/** jamespedwards42 */
public final class CryptodotcomAdapters {

  private CryptodotcomAdapters() {
  }

  public static CandleStickData adaptCryptodotcomCandleStickData(LinkedHashMap klines, CurrencyPair currencyPair, KlineInterval interval) {
    CandleStickData candleStickData = null;
    if (klines != null) {
      List<CandleStick> candleSticks = new ArrayList<>();
      ((ArrayList)((LinkedHashMap)klines.get("result")).get("data")).forEach(a ->  {
                LinkedHashMap val = (LinkedHashMap)a;
                candleSticks.add(

                        new CandleStick.Builder()
                                .timestamp(new Date((Long)val.get("t")-1))
                                .low(new BigDecimal((String)val.get("l")))
                                .high(new BigDecimal((String)val.get("h")))
                                .open(new BigDecimal((String)val.get("o")))
                                .close(new BigDecimal((String)val.get("c")))
                                .volume(new BigDecimal((String)val.get("v")))
                                .build());
              }
      );

      candleStickData = new CandleStickData(currencyPair, candleSticks);
    }
    return candleStickData;
  }

  public static String convertToCryptodotomSymbol(String symbol) {
    return symbol.replace("/", "_");
  }
}
