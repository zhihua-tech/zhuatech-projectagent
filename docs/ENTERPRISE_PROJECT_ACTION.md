# 企业级项目 Agent 行动执行

`POST /api/enterprise/projectagent/project-action-execution` 检查基线、责任人、执行权限、预算进度影响、风险、依赖、外部承诺、回滚和审计，返回 `EXECUTE / COORDINATE / BLOCKED`。

生产环境应通过受控工具执行，Agent 不得直接绕过项目审批修改预算、基线或对外承诺。
