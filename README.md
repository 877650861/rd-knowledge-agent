# R&D Knowledge Agent

面向研发团队的知识检索与任务协同智能体样例。它把分散的项目文档转为可检索证据，在回答时保留引用，并按请求选择确定性工具，减少跨文档查找和多工具切换带来的上下文损失。

## 已实现能力

- **知识入库与检索增强**：提供文档入库 API；使用本地可运行的词项相似度检索返回可追溯的文档和评分。向量数据库或 Spring AI 可在 `KnowledgeBase` 接口后无侵入替换。
- **任务工具调度**：通过 `AgentTool` 抽象选择工具；示例包含发布检查清单，适配层可替换为 MCP 或企业内部服务。
- **上下文管理**：每个会话保留最近八条问答，避免无限增长并让任务连续可追踪。
- **可验证的编排**：不依赖外部模型即可运行与测试；外部 LLM 仅应在生产适配层中接入，避免把密钥和不可控回答放进核心逻辑。

## 启动

```bash
mvn test
mvn spring-boot:run
```

写入团队知识：

```bash
curl -X POST http://localhost:8081/agent/documents -H "Content-Type: application/json" -d '{"title":"支付服务发布流程","content":"发布前需要完成回归测试、灰度策略、监控告警和回滚预案。"}'
```

提问：

```bash
curl -X POST http://localhost:8081/agent/ask -H "Content-Type: application/json" -d '{"sessionId":"demo","question":"支付服务发布前要检查什么？"}'
```

## 设计边界

该项目是演示可测试 RAG 编排边界的工程样例，不宣称已经接入生产数据、LLM、MCP 或向量数据库。接入外部服务时请补充权限隔离、脱敏、可观测性、限额与人工审核。
