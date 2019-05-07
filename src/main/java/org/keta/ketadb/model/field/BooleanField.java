package org.keta.ketadb.model.field;

import java.util.Date;
import org.keta.ketadb.model.type.FieldType;

public class BooleanField extends Field {

  protected BooleanField(String name, Boolean val) {
    super(name, FieldType.Boolean, val);
  }

  @Override
  public Boolean getAsBoolean() {
    return (Boolean) getVal();
  }

  @Override
  public Long getAsLong() {
    if (getAsBoolean()) {
      return 1L;
    }
    return 0L;
  }

  @Override
  public Double getAsDouble() {
    if (getAsBoolean()) {
      return 1d;
    }
    return 0d;
  }

  @Override
  public Long getAsTime() {
    return 0L;
  }

  @Override
  public Date getAsDate() {
    return new Date(0L);
  }
}
