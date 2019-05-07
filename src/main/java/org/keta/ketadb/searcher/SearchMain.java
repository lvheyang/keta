package org.keta.ketadb.searcher;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.index.StandardDirectoryReader;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.keta.ketadb.searcher.collectors.CommonCollector;

public class SearchMain {

  public static final int size = 10;

  public static void main(String[] args) throws QueryNodeException, IOException {
    IndexReader[] readers = new IndexReader[size];
    for (int i = 0; i < size; i++) {
      IndexReader reader =
          StandardDirectoryReader.open(
              FSDirectory.open(Paths.get("/Users/wenzhengcui/tmp/test/index-" + i)));
      readers[i] = reader;
    }
    IndexReader m = new MultiReader(readers);
    WrapIndexSearcher searcher = new WrapIndexSearcher(m);
    StandardQueryParser parser = new StandardQueryParser();
    Query q = parser.parse("raw:\"127.0.0.1 RFC1413?? CustomerIdentification 2019-05-06T13:35:37.680+08:00 requestLine 302 222 Apache-Coyote/1.1:80 http://ip:p/baidu/forwardDesktop;jsessionid=A83FFD32A5644CFBAA3D0618B3C631D8? JSESSIONID=A83FFD32A5644CFBAA3D0618B3C631D8 Mozilla/5.0(Windows NT 6.1; Win64; x64)AppleWebKit/537.36(KHTML,like Gecko)Chrome/69.0.3497.100Safari/537.36 784 437 X-Real-IP hostyuming 280797127.0.0.1 RFC1413?? CustomerIdentification 2019-05-06T13:35:37.840+08:00 requestLine 302 222 Apache-Coyote/1.1:80 http://ip:p/baidu/forwardDesktop;jsessionid=A83FFD32A5644CFBAA3D0618B3C631D8? JSESSIONID=A83FFD32A5644CFBAA3D0618B3C631D8 Mozilla/5.0(Windows NT 6.1; Win64; x64)AppleWebKit/537.36(KHTML,like Gecko)Chrome/69.0.3497.100Safari/537.36 784 437 X-Real-IP hostyuming 280797\"", "raw");
    //    Query q = parser.parse("raw:1*", "raw");

    long start = System.nanoTime();
    for (int i = 0; i < 1; i++) {
      CommonCollector c =
          new CommonCollector(
              (doc, result) -> {
                result.add(doc);
                return 1;
              });
      searcher.search1(q);
      //      TopDocs c = search(searcher, q, 10);
      for (int j = 0; j < 1; j++) {
        System.out.println("docs.totalHits = " + c.getHits());
        System.out.println("docs.docs = " + c.getResult());
      }
    }
    System.out.println("System.nanoTime() - start = " + (System.nanoTime() - start) / 1000000);
  }

  public static TopDocs search(IndexSearcher searcher, Query query, int numHits)
      throws IOException {
    final int limit = Math.max(1, searcher.getIndexReader().maxDoc());
    final int cappedNumHits = Math.min(numHits, limit);

    TopScoreDocCollector c = TopScoreDocCollector.create(cappedNumHits, null);
    searcher.search(query, c);
    return c.topDocs();
  }
}
