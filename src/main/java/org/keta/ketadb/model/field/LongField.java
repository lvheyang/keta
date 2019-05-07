package org.keta.ketadb.model.field;

import java.util.Date;
import org.keta.ketadb.model.type.FieldType;

public class LongField extends Field {

  public LongField(String name, Long val) {
    super(name, FieldType.Long, val);
  }

  @Override
  public Boolean getAsBoolean() {
    return getAsLong() != 0L;
  }

  @Override
  public Long getAsLong() {
    return (Long) getVal();
  }

  @Override
  public Double getAsDouble() {
    return getAsLong().doubleValue();
  }

  @Override
  public Long getAsTime() {
    return getAsLong();
  }

  @Override
  public Date getAsDate() {
    return new Date(getAsLong());
  }
}
