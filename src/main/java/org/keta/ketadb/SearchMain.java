package org.keta.ketadb;

import java.io.IOException;
import java.nio.file.Paths;
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

public class SearchMain {

  public static final int size = 1;

  public static void main(String[] args) throws QueryNodeException, IOException {
    IndexReader[] readers = new IndexReader[size];
    for (int i = 0; i < size; i++) {
      IndexReader reader =
          StandardDirectoryReader.open(
              FSDirectory.open(Paths.get("/Users/wenzhengcui/tmp/test/index-" + i)));
      readers[i] = reader;
    }
    IndexReader m = new MultiReader(readers);
    IndexSearcher searcher = new IndexSearcher(m);
    StandardQueryParser parser = new StandardQueryParser();
    Query q = parser.parse("raw:1*", "raw");

    long start = System.nanoTime();
    for (int i = 0; i < 1; i++) {
      TopDocs docs = SearchMain.search(searcher, q, 10);
      for (int j = 0; j < 1; j++) {
        System.out.println("docs.totalHits = " + docs.totalHits);
        System.out.println("docs.docs = " + m.document(docs.scoreDocs[j].doc));
      }
    }
    System.out.println("System.nanoTime() -start = " + (System.nanoTime() - start) / 1000000);
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
