/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.projectagent.agent;
import org.springframework.stereotype.Component; import java.util.List; import java.util.Map;
/** 企业项目交付智能体平台运行边界；默认演示执行器不连接真实模型、业务系统或外部通信渠道。 */
public interface AgentRuntime {
 AgentResult run(AgentRequest request);
 record AgentRequest(String objective,Map<String,String> context){}
 record AgentStep(String name,String status,String evidence){}
 record AgentResult(String runtime,String summary,List<AgentStep> steps,Map<String,Object> metrics){}
}
@Component class DemoAgentRuntime implements AgentRuntime {
 public AgentResult run(AgentRequest request){
  return new AgentResult("project-evidence-demo","已汇总计划、会议、风险和变更证据，交期与范围建议等待项目负责人确认。",List.of(new AgentStep("项目事实同步","COMPLETED","汇总计划、纪要与任务"),new AgentStep("偏差预测","COMPLETED","识别 3 项里程碑影响"),new AgentStep("治理行动","PENDING","等待项目经理确认")),Map.of("evidenceItems",12,"suggestedActions",3,"objectiveLength",request.objective().length()));
 }
}

