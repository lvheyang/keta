package org.keta.ketadb.model.field;

import java.util.Date;
import org.keta.ketadb.model.type.FieldType;

public class DoubleField extends Field {

  public DoubleField(String name, Double val) {
    super(name, FieldType.Double, val);
  }

  @Override
  public Boolean getAsBoolean() {
    return getAsLong() != 0L;
  }

  @Override
  public Long getAsLong() {
    return ((Double) getVal()).longValue();
  }

  @Override
  public Double getAsDouble() {
    return (Double) getVal();
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
