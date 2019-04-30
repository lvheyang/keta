package org.keta.ketadb;

import java.io.IOException;
import java.nio.file.Paths;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.NoMergePolicy;
import org.apache.lucene.store.FSDirectory;
import org.joda.time.DateTime;

public class Main {

  public static final String template =
      "127.0.0.1 RFC1413?? CustomerIdentification %s requestLine 302 222 Apache-Coyote/1.1:80 http://ip:p/baidu/forwardDesktop;jsessionid=A83FFD32A5644CFBAA3D0618B3C631D8? JSESSIONID=A83FFD32A5644CFBAA3D0618B3C631D8 Mozilla/5.0(Windows NT 6.1; Win64; x64)AppleWebKit/537.36(KHTML,like Gecko)Chrome/69.0.3497.100Safari/537.36 784 437 X-Real-IP hostyuming 280797"
          + "127.0.0.1 RFC1413?? CustomerIdentification %s requestLine 302 222 Apache-Coyote/1.1:80 http://ip:p/baidu/forwardDesktop;jsessionid=A83FFD32A5644CFBAA3D0618B3C631D8? JSESSIONID=A83FFD32A5644CFBAA3D0618B3C631D8 Mozilla/5.0(Windows NT 6.1; Win64; x64)AppleWebKit/537.36(KHTML,like Gecko)Chrome/69.0.3497.100Safari/537.36 784 437 X-Real-IP hostyuming 280797";
  public static final String fileNameTemplate = "/Users/wenzhengcui/tmp/test/index-%s/";
  public static final int million = 1000000;

  public static void main(String[] args) throws IOException {

    for (int fileNo = 0; fileNo < 10; fileNo++) {
      long start = System.currentTimeMillis();
      String dirname = String.format(fileNameTemplate, fileNo);
      IndexWriterConfig config = new IndexWriterConfig();
      config.setMergePolicy(NoMergePolicy.INSTANCE);

      IndexWriter writer = new IndexWriter(FSDirectory.open(Paths.get(dirname)), config);
      for (int i = 0; i < 4 * million; i++) {
        // 单条760 B
        Document doc = new Document();
        String log = String.format(template, new DateTime(), new DateTime());
        doc.add(new StringField("raw", log, Store.YES));
        writer.addDocument(doc);
      }
      writer.close();
      System.out.println("Interval = " + ((System.currentTimeMillis() - start) / 1000) + "s");
    }
  }
}
