package io.github.wangjiayao.agent.orchestration;
import io.github.wangjiayao.agent.knowledge.RetrievedDocument;
import io.github.wangjiayao.agent.tool.ToolResult;
import java.util.List;
public record AgentResponse(String answer, List<RetrievedDocument> citations, List<ToolResult> toolResults, List<String> memory) { }
