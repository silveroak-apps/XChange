package org.knowm.xchange.bittrex.dto.marketdata;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


/**
 * {
 *     "startsAt": "string (date-time)",
 *     "open": "number (double)",
 *     "high": "number (double)",
 *     "low": "number (double)",
 *     "close": "number (double)",
 *     "volume": "number (double)",
 *     "quoteVolume": "number (double)"
 *   }
 */
@Data
@NoArgsConstructor
public class BittrexCandle {
    private String startsAt;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal volume;
    private BigDecimal quoteVolume;
}
