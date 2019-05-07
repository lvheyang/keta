package org.keta.ketadb.searcher;

import java.io.IOException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Weight;

public class WrapIndexSearcher extends IndexSearcher {

  public WrapIndexSearcher(IndexReader r) {
    super(r);
  }

  public void search1(Query query) throws IOException {
    query = rewrite(query);
    Weight weight = createWeight(query, false, 1);

    for (LeafReaderContext ctx : leafContexts) { // search each subreader
      Scorer s = weight.scorer(ctx);
      if (s == null) {
        // No docs match
        continue;
      }

      if (s.twoPhaseIterator() == null) {
        for (int doc = s.iterator().nextDoc();
            doc != DocIdSetIterator.NO_MORE_DOCS;
            doc = s.iterator().nextDoc()) {
          if (ctx.reader().getLiveDocs() == null || ctx.reader().getLiveDocs().get(doc)) {
            System.out.println(
                "getIndexReader().document(doc) = " + getIndexReader().document(doc));
          }
        }
      } else {
        // The scorer has an approximation, so run the approximation first, then check acceptDocs,
        // then confirm
        final DocIdSetIterator approximation = s.twoPhaseIterator().approximation();
        for (int doc = approximation.nextDoc();
            doc != DocIdSetIterator.NO_MORE_DOCS;
            doc = approximation.nextDoc()) {
          if ((ctx.reader().getLiveDocs() == null || ctx.reader().getLiveDocs().get(doc))
              && s.twoPhaseIterator().matches()) {
            System.out.println(
                "getIndexReader().document(doc) = " + getIndexReader().document(doc));
          }
        }
      }
    }
  }
}
