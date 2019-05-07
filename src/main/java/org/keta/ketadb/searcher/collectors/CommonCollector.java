package org.keta.ketadb.searcher.collectors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.LeafReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.LeafCollector;
import org.keta.ketadb.searcher.pipeline.PipelineCollector;

public class CommonCollector implements Collector {

  protected long totalHits = 0L;
  protected PipelineCollector pipeline;

  private List<Document> result = new ArrayList<>();

  public CommonCollector(PipelineCollector pipeline) {
    this.pipeline = pipeline;
  }

  @Override
  public LeafCollector getLeafCollector(LeafReaderContext context) {
    final int docBase = context.docBase;
    final LeafReader reader = context.reader();
    return new AbstractLeafCollector() {

      @Override
      public void collect(int doc) throws IOException {
        totalHits += pipeline.collect(reader.document(doc + docBase), result);
      }
    };
  }

  public Collection<Document> getResult() {
    return result;
  }

  public long getHits() {
    return totalHits;
  }

  @Override
  public boolean needsScores() {
    return false;
  }
}
