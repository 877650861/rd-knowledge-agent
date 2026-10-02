package io.github.wangjiayao.agent.orchestration;

import io.github.wangjiayao.agent.knowledge.*;
import io.github.wangjiayao.agent.session.ConversationMemory;
import io.github.wangjiayao.agent.tool.*;
import java.util.List;
import org.springframework.stereotype.Service;

/** Coordinates retrieval, deterministic tool selection and bounded session memory. */
@Service
public class ResearchAgentService {
  private final KnowledgeBase knowledgeBase; private final List<AgentTool> tools; private final ConversationMemory memory;
  public ResearchAgentService(KnowledgeBase knowledgeBase, List<AgentTool> tools, ConversationMemory memory) { this.knowledgeBase = knowledgeBase; this.tools = tools; this.memory = memory; }
  public AgentResponse ask(String sessionId, String question) {
    List<RetrievedDocument> citations = knowledgeBase.search(question, 3);
    List<ToolResult> toolResults = tools.stream().filter(tool -> tool.supports(question)).map(tool -> tool.execute(question)).toList();
    String evidence = citations.isEmpty() ? "知识库中暂未检索到直接证据。" : "已检索到 " + citations.size() + " 条相关知识：" + citations.stream().map(hit -> hit.document().title()).reduce((a,b) -> a + "、" + b).orElse("");
    String action = toolResults.isEmpty() ? "建议先补充上下文或拆分任务。" : toolResults.get(0).summary();
    String answer = evidence + " " + action;
    memory.add(sessionId, "Q: " + question); memory.add(sessionId, "A: " + answer);
    return new AgentResponse(answer, citations, toolResults, memory.recent(sessionId));
  }
}
