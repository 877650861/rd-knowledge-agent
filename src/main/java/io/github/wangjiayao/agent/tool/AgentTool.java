package io.github.wangjiayao.agent.tool;
public interface AgentTool { String name(); boolean supports(String request); ToolResult execute(String request); }
