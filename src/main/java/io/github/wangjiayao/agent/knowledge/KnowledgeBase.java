package io.github.wangjiayao.agent.knowledge;
import java.util.List;
public interface KnowledgeBase { KnowledgeDocument upsert(String title, String content); List<RetrievedDocument> search(String query, int limit); }
