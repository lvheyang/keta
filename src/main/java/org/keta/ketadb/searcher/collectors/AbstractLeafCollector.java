package org.keta.ketadb.searcher.collectors;

import java.io.IOException;
import org.apache.lucene.search.LeafCollector;
import org.apache.lucene.search.Scorer;

public abstract class AbstractLeafCollector implements LeafCollector {

  @Override
  public void setScorer(Scorer scorer) throws IOException {
    return;
  }
}
