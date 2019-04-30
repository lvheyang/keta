package org.keta.ketadb.model;

import java.util.List;
import org.keta.ketadb.model.field.Field;

public class Row {

  private List<Field> fields;

  public Row(List<Field> fields) {
    this.fields = fields;
  }

  public List<Field> getFields() {
    return fields;
  }
}
