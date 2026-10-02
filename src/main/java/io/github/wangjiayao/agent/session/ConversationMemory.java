package io.github.wangjiayao.agent.session;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
@Component
public class ConversationMemory {
  private final Map<String, Deque<String>> turns = new ConcurrentHashMap<>();
  public void add(String sessionId, String turn) { Deque<String> history = turns.computeIfAbsent(sessionId, ignored -> new ArrayDeque<>()); synchronized (history) { history.addLast(turn); while (history.size() > 8) history.removeFirst(); } }
  public List<String> recent(String sessionId) { Deque<String> history = turns.getOrDefault(sessionId, new ArrayDeque<>()); synchronized (history) { return List.copyOf(history); } }
}
