package org.keta.ketadb.extraction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.keta.ketadb.model.field.Field;

public class RegexRule implements ExtractRule {

  private final Pattern p;
  private final String field;
  private final Map<String, Integer> namedGroups;

  public RegexRule(String p) {
    this(p, Field.FIELD_RAW);
  }

  public RegexRule(String p, String field) {
    this.p = Pattern.compile(p);
    this.field = field;
    Map<String, Integer> ng = Collections.emptyMap();
    try {
      ng = getNamedGroups(this.p);
    } catch (Exception e) {
      System.out.println("e = " + e);
    }
    this.namedGroups = ng;
  }

  @Override
  public Document extract(Document doc) {
    String val = doc.get(field);
    if (val == null || val.length() <= 0) {
      return doc;
    }

    Matcher m = p.matcher(val);
    if (!m.matches()) {
      return doc;
    }

    for (String g : namedGroups.keySet()) {
      String v = m.group(g);
      doc.add(new StringField(g, v, Store.NO));
    }
    return doc;
  }

  private static Map<String, Integer> getNamedGroups(Pattern regex)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method namedGroupsMethod = Pattern.class.getDeclaredMethod("namedGroups");
    namedGroupsMethod.setAccessible(true);
    Map<String, Integer> namedGroups = null;
    namedGroups = (Map<String, Integer>) namedGroupsMethod.invoke(regex);
    if (namedGroups == null) {
      throw new InternalError();
    }

    return Collections.unmodifiableMap(namedGroups);
  }

  public static void main(String[] args) {
    final String regex =
        "^(?<IP>[^ ]+)[^\\[\\n]*\\[(?<timestamp>[^\\]]+)\\]\\s+\"(?<method>[^\"]+)\"\\s+(?<requesttime>[^ ]+)\\s+(?<bodybytessent>[^ ]+)\\s+(?<status>[^ ]+)\\s+(?<bytessent>[^ ]+)\\s+\"\\-\"\\s+\"(?<useragent>[^\"]+)";
    final String string =
        "221.12.12.194 - - [10/Jul/2015 15:51:09 +0800] \"GET /ubuntu.iso HTTP/1.0\" 0.000 129 404 168 \"-\" \"Wget/1.11.4 Red Hat modified\"\n";

    final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
    final Matcher matcher = pattern.matcher(string);

    while (matcher.find()) {
      System.out.println("Full match: " + matcher.group(0));
      for (int i = 1; i <= matcher.groupCount(); i++) {
        System.out.println("Group " + i + ": " + matcher.group(i));
      }
    }
  }
}
