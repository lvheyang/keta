package org.keta.ketadb.extraction;

import org.apache.lucene.document.Document;

public interface ExtractRule {

  Document extract(Document doc);
}
