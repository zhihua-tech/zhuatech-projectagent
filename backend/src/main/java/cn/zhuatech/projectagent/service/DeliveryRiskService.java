/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.projectagent.service;
import jakarta.validation.constraints.*; import org.springframework.stereotype.Service; import java.util.*;
/** 基于透明规则评估交付风险；不会自动修改项目基线或客户承诺。 */
@Service public class DeliveryRiskService {
 public record RiskRequest(@NotBlank String project,@Min(-100) @Max(100) int scheduleVarianceDays,@DecimalMin("-100.0") @DecimalMax("100.0") double costVariancePercent,@Min(0) int criticalDependencies,boolean customerCommitmentAffected){}
 public record RiskResult(int score,String level,boolean governanceReview,List<String> actions){}
 public RiskResult assess(RiskRequest r){int score=Math.min(100,Math.max(0,Math.max(0,-r.scheduleVarianceDays())*4+(int)Math.max(0,r.costVariancePercent())*2+r.criticalDependencies()*8+(r.customerCommitmentAffected()?25:0)));String level=score>=70?"HIGH":score>=40?"MEDIUM":"LOW";List<String> actions=new ArrayList<>();actions.add("核对计划基线与实际完成证据");if(r.criticalDependencies()>0)actions.add("明确依赖责任人与最晚解决时间");if(r.customerCommitmentAffected())actions.add("升级项目负责人确认客户沟通");return new RiskResult(score,level,score>=40||r.customerCommitmentAffected(),actions);}}

