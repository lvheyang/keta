package org.keta.ketadb.searcher.pipeline;

import java.util.Collection;
import org.apache.lucene.document.Document;

public interface PipelineCollector {

  /**
   * Collect single document and the final documents result
   *
   * @param doc current document
   * @param c the intermediate result
   * @return the total hits should be accumulated
   */
  int collect(Document doc, Collection<Document> c);
}
