/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.projectagent.controller;
import cn.zhuatech.projectagent.agent.AgentRuntime;
import cn.zhuatech.projectagent.common.ApiResponse;
import cn.zhuatech.projectagent.dto.ProjectAgentDto.*;
import cn.zhuatech.projectagent.service.ProjectAgentService;
import cn.zhuatech.projectagent.service.DeliveryRiskService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/shopfloor") @PreAuthorize("hasAnyRole('DOMAIN_USER','ADMIN')")
public class WorkspaceController {
 private final ProjectAgentService service; private final AgentRuntime runtime; private final DeliveryRiskService domainAgent;
 public WorkspaceController(ProjectAgentService service,AgentRuntime runtime,DeliveryRiskService domainAgent){this.service=service;this.runtime=runtime;this.domainAgent=domainAgent;}
 @GetMapping("/dashboard") public ApiResponse<Dashboard> dashboard(){return ApiResponse.ok(service.shopfloorDashboard());}
 @PostMapping("/work-orders/{id}/reports") public ApiResponse<ReportResult> report(@PathVariable Long id,@Valid @RequestBody ReportRequest request){return ApiResponse.ok("反馈提交成功",service.report(id,request));}
 @PostMapping("/agent-preview") public ApiResponse<AgentRuntime.AgentResult> preview(@RequestBody Map<String,String> body){return ApiResponse.ok(runtime.run(new AgentRuntime.AgentRequest(body.getOrDefault("objective","分析当前业务事项"),Map.of("mode","demo","approval","required"))));}
 @PostMapping("/delivery-risk") public ApiResponse<DeliveryRiskService.RiskResult> domainAction(@Valid @RequestBody DeliveryRiskService.RiskRequest request){return ApiResponse.ok("项目交付风险评估完成",domainAgent.assess(request));}
}

