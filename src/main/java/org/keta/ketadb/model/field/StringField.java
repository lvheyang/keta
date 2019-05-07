package org.keta.ketadb.model.field;

import java.util.Date;
import org.joda.time.DateTime;
import org.keta.ketadb.model.type.FieldType;

public class StringField extends Field {

  public StringField(String name, String val) {
    super(name, FieldType.String, val);
  }

  @Override
  public Boolean getAsBoolean() {
    return Boolean.parseBoolean(getAsString());
  }

  @Override
  public Long getAsLong() {
    Long ret;
    try {
      ret = Long.parseLong(getAsString());
    } catch (NumberFormatException e) {
      // TODO debug log
      return null;
    }
    return ret;
  }

  @Override
  public Double getAsDouble() {
    Double ret;
    try {
      ret = Double.parseDouble(getAsString());
    } catch (NumberFormatException e) {
      // TODO debug log
      return null;
    }
    return ret;
  }

  @Override
  public Long getAsTime() {
    return DateTime.parse(getAsString()).toDate().getTime();
  }

  @Override
  public Date getAsDate() {
    return DateTime.parse(getAsString()).toDate();
  }
}
