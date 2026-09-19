/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.projectagent.config;
import cn.zhuatech.projectagent.model.*; import cn.zhuatech.projectagent.repository.*; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.*; import org.springframework.security.crypto.password.PasswordEncoder; import java.time.LocalDate; import java.util.List;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Configuration public class DataInitializer {
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Bean CommandLineRunner seed(OperatingUnitRepository units,WorkRecordRepository records,ResourceRegisterRepository resources,ReviewRecordRepository reviews,UserRepository users,PasswordEncoder encoder){return args->{if(units.count()>0)return;
  OperatingUnit first=units.save(new OperatingUnit("PM-APP","企业应用交付组","数字化项目群",2600)),second=units.save(new OperatingUnit("PM-DATA","数据平台交付组","数据项目群",1800)),third=units.save(new OperatingUnit("PMO","项目管理办公室","交付治理中心",1000));
  WorkRecord a=records.save(new WorkRecord("PRJ-2603-MS08","PROJ-SCM-01","供应链协同平台一期上线",first,42,31,4,LocalDate.now().plusDays(2),WorkRecord.Status.RUNNING,"BASELINE-V8")); WorkRecord b=records.save(new WorkRecord("PRJ-2608-MS04","PROJ-CDP-02","客户数据平台数据验收",second,36,36,1,LocalDate.now().plusDays(0),WorkRecord.Status.COMPLETED,"BASELINE-V4")); WorkRecord c=records.save(new WorkRecord("PRJ-2606-MS06","PROJ-OA-02","移动办公二期集成联调",first,28,19,2,LocalDate.now().plusDays(3),WorkRecord.Status.RELEASED,"BASELINE-V6"));
  resources.saveAll(List.of(new ResourceRegister("DATA-PM-01","项目管理只读视图",third,ResourceRegister.Status.RUNNING,95),new ResourceRegister("KNOW-PMO-02","交付方法知识库",third,ResourceRegister.Status.RUNNING,91),new ResourceRegister("GUARD-COMMIT-03","客户承诺审查器",first,ResourceRegister.Status.ALARM,76)));
  reviews.saveAll(List.of(new ReviewRecord("REV-PJ-028",a,"预测准确性",16,2,ReviewRecord.Result.PENDING,"许衡"),new ReviewRecord("REV-PJ-017",b,"证据完整性",28,0,ReviewRecord.Result.PASSED,"林岑"),new ReviewRecord("REV-PJ-039",c,"范围合规",15,3,ReviewRecord.Result.FAILED,"宋期")));
  String demo=encoder.encode("Demo@2026");
  users.saveAll(List.of(new UserAccount("operator",demo,"林岑",UserAccount.Role.DOMAIN_USER,"PM-APP"),new UserAccount("planner",demo,"许衡",UserAccount.Role.DOMAIN_OPERATOR,null),new UserAccount("quality",demo,"评测负责人",UserAccount.Role.QUALITY,null),new UserAccount("admin",encoder.encode("ZhuaTech@2026"),"系统管理员",UserAccount.Role.ADMIN,null)));
 };}}

