package io.github.wangjiayao.agent.tool;
import org.springframework.stereotype.Component;
/** A deterministic tool adapter; in production this boundary can call MCP or an internal release API. */
@Component
public class ReleaseChecklistTool implements AgentTool {
  public String name() { return "release-checklist"; }
  public boolean supports(String request) { return request.contains("发布") || request.toLowerCase().contains("release"); }
  public ToolResult execute(String request) { return new ToolResult(name(), "发布前检查：回归测试、变更评审、灰度策略、监控告警和回滚预案。"); }
}
