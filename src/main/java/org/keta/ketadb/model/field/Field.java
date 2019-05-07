package org.keta.ketadb.model.field;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.keta.ketadb.model.type.FieldType;

public abstract class Field {

  public static final String FIELD_RAW = "_raw";
  public static final String FIELD_TIME = "_time";
  public static final String FIELD_INDEX = "_index";
  public static final String FIELD_TYPE = "_type";

  private static final Set<String> reservedSet =
      new HashSet<>(Arrays.asList(FIELD_RAW, FIELD_INDEX, FIELD_TIME, FIELD_TYPE));

  public static boolean isReserved(String name) {
    return reservedSet.contains(name);
  }

  private final String name;
  private final FieldType type;
  private final Object val;

  Field(String name, FieldType type, Object val) {
    this.name = name;
    this.type = type;
    this.val = val;
  }

  public String getName() {
    return name;
  }

  public FieldType getType() {
    return type;
  }

  public Object getVal() {
    return val;
  }

  public String getAsString() {
    return String.valueOf(val);
  }

  public boolean isNull() {
    return val == null;
  }

  public boolean isNotNull() {
    return !isNull();
  }

  public abstract Boolean getAsBoolean();

  public abstract Long getAsLong();

  public abstract Double getAsDouble();

  public abstract Long getAsTime();

  public abstract Date getAsDate();
}
