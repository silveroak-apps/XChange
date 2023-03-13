package org.knowm.xchange.cryptodotcom;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.cryptodotcom.dto.cryptodotcomException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.OrderAmountUnderMinimumException;
import org.knowm.xchange.exceptions.OrderNotValidException;

public class cryptodotcomErrorAdapterTest {

  @Test
  public void testcryptodotcomExceptionWithCode1013MsgLotSize() {
    final ExchangeException adaptedException =
        cryptodotcomErrorAdapter.adapt(new cryptodotcomException(-1013, "LOT_SIZE"));

    assertThat(adaptedException).isInstanceOf(OrderNotValidException.class);
  }

  @Test
  public void testcryptodotcomExceptionWithCode1013MsgMinNotional() {
    final ExchangeException adaptedException =
        cryptodotcomErrorAdapter.adapt(new cryptodotcomException(-1013, "MIN_NOTIONAL"));

    assertThat(adaptedException).isInstanceOf(OrderAmountUnderMinimumException.class);
  }
}
