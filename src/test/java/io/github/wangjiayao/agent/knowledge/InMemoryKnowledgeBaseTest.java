package io.github.wangjiayao.agent.knowledge;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
class InMemoryKnowledgeBaseTest {
  @Test void returnsRelevantDocument() {
    InMemoryKnowledgeBase base = new InMemoryKnowledgeBase();
    base.upsert("支付发布流程", "发布前需完成灰度、回滚预案和监控检查");
    assertEquals("支付发布流程", base.search("支付发布回滚", 1).get(0).document().title());
  }
}
