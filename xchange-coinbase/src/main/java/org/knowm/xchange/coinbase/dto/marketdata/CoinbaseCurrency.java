package org.knowm.xchange.coinbase.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseCurrency.CoinbaseCurrencyDeserializer;

/** @author jamespedwards42 */
@JsonDeserialize(using = CoinbaseCurrencyDeserializer.class)
public class CoinbaseCurrency {

  private final String name;
  private final String id;

  private final String status;

  private CoinbaseCurrency(String name, final String id, String status) {

    this.name = name;
    this.id = id;
    this.status = status;
  }

  public String getName() {

    return name;
  }

  public String getId() {

    return id;
  }

  @Override
  public String toString() {

    return "CoinbaseCurrency [name=" + name + ", isoCode=" + id + "]";
  }

  static class CoinbaseCurrencyDeserializer extends JsonDeserializer<CoinbaseCurrency> {

    @Override
    public CoinbaseCurrency deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      ObjectCodec oc = jp.getCodec();
      JsonNode node = oc.readTree(jp);
      if (node.isArray()) {
        String name = node.path(0).asText();
        String id = node.path(1).asText();
        String status = node.path(2).asText();
        return new CoinbaseCurrency(name, id, status);
      }
      return null;
    }
  }
}
