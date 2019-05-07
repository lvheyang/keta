package org.keta.ketadb.model.field;

import java.util.Date;
import org.keta.ketadb.model.type.FieldType;

public class DateField extends Field {

  public DateField(String name, Long val) {
    super(name, FieldType.Date, val);
  }

  @Override
  public Boolean getAsBoolean() {
    return isNotNull();
  }

  @Override
  public Long getAsLong() {
    return (Long) getVal();
  }

  @Override
  public Double getAsDouble() {
    return ((Long) getVal()).doubleValue();
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
