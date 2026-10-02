package io.github.wangjiayao.agent.knowledge;
import java.time.Instant;
public record KnowledgeDocument(String id, String title, String content, Instant indexedAt) { }
