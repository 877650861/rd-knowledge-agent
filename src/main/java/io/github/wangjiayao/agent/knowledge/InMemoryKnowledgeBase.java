package io.github.wangjiayao.agent.knowledge;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/** Replaceable retrieval adapter: lexical overlap makes the demo fully local and testable. */
@Component
public class InMemoryKnowledgeBase implements KnowledgeBase {
    private final Map<String, KnowledgeDocument> documents = new ConcurrentHashMap<>();
    @Override public KnowledgeDocument upsert(String title, String content) {
        KnowledgeDocument document = new KnowledgeDocument(UUID.randomUUID().toString(), title, content, Instant.now());
        documents.put(document.id(), document); return document;
    }
    @Override public List<RetrievedDocument> search(String query, int limit) {
        Set<String> terms = tokens(query);
        return documents.values().stream().map(d -> new RetrievedDocument(d, score(terms, d)))
            .filter(hit -> hit.score() > 0).sorted(Comparator.comparingDouble(RetrievedDocument::score).reversed()).limit(limit).collect(Collectors.toList());
    }
    private double score(Set<String> queryTerms, KnowledgeDocument document) {
        Set<String> candidate = tokens(document.title() + " " + document.content());
        if (queryTerms.isEmpty() || candidate.isEmpty()) return 0;
        long overlap = queryTerms.stream().filter(candidate::contains).count();
        return (double) overlap / Math.sqrt(queryTerms.size() * candidate.size());
    }
    private Set<String> tokens(String text) {
        Set<String> result = new HashSet<>();
        StringBuilder latinWord = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = Character.toLowerCase(text.charAt(i));
            if (Character.UnicodeScript.of(c) == Character.UnicodeScript.HAN) {
                flushWord(latinWord, result);
                result.add(String.valueOf(c));
            } else if (Character.isLetterOrDigit(c)) {
                latinWord.append(c);
            } else {
                flushWord(latinWord, result);
            }
        }
        flushWord(latinWord, result);
        return result;
    }
    private void flushWord(StringBuilder word, Set<String> tokens) { if (word.length() > 1) tokens.add(word.toString()); word.setLength(0); }
}
