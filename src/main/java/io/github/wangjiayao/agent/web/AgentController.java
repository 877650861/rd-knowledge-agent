package io.github.wangjiayao.agent.web;
import io.github.wangjiayao.agent.knowledge.*;
import io.github.wangjiayao.agent.orchestration.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/agent")
public class AgentController {
  private final KnowledgeBase knowledgeBase; private final ResearchAgentService agent;
  public AgentController(KnowledgeBase knowledgeBase, ResearchAgentService agent) { this.knowledgeBase = knowledgeBase; this.agent = agent; }
  @PostMapping("/documents") public KnowledgeDocument ingest(@Valid @RequestBody IngestRequest request) { return knowledgeBase.upsert(request.title(), request.content()); }
  @PostMapping("/ask") public AgentResponse ask(@Valid @RequestBody AskRequest request) { return agent.ask(request.sessionId(), request.question()); }
}
