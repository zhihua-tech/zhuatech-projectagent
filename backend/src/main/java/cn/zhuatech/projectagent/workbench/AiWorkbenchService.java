/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.projectagent.workbench;

import static cn.zhuatech.projectagent.workbench.WorkbenchInput.*;

import java.math.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class AiWorkbenchService {
  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  public Map<String, Object> execute(Map<String, Object> r) {
    validate(r);
    keys(r, "tasks");
    Map<String, Integer> durations = new LinkedHashMap<>();
    Map<String, List<String>> deps = new LinkedHashMap<>();
    for (var t : rows(r, "tasks", 1, 200)) {
      keys(t, "id", "duration", "dependsOn");
      String id = id(t, "id");
      if (durations.putIfAbsent(id, integer(t, "duration", 1, 10000)) != null)
        throw new IllegalArgumentException("任务编号重复");
      var ds = strings(t, "dependsOn", 0, 200);
      if (new HashSet<>(ds).size() != ds.size()) throw new IllegalArgumentException("依赖重复");
      deps.put(id, ds);
    }
    for (var ds : deps.values())
      if (!durations.keySet().containsAll(ds)) throw new IllegalArgumentException("未知依赖任务");
    Map<String, Integer> early = new LinkedHashMap<>();
    List<String> order = new ArrayList<>();
    while (order.size() < durations.size()) {
      boolean changed = false;
      for (String id : durations.keySet())
        if (!early.containsKey(id) && early.keySet().containsAll(deps.get(id))) {
          int start = 0;
          for (String d : deps.get(id)) start = Math.max(start, early.get(d) + durations.get(d));
          early.put(id, start);
          order.add(id);
          changed = true;
        }
      if (!changed) throw new IllegalArgumentException("任务依赖存在环路");
    }
    int finish = 0;
    for (String id : order) finish = Math.max(finish, early.get(id) + durations.get(id));
    Map<String, Integer> late = new HashMap<>();
    for (int i = order.size() - 1; i >= 0; i--) {
      String id = order.get(i);
      int lf = finish;
      for (String child : order)
        if (deps.get(child).contains(id)) lf = Math.min(lf, late.get(child));
      late.put(id, lf - durations.get(id));
    }
    List<Map<String, Object>> out = new ArrayList<>();
    for (String id : order)
      out.add(
          Map.of(
              "id",
              id,
              "earliestStart",
              early.get(id),
              "latestStart",
              late.get(id),
              "finish",
              early.get(id) + durations.get(id),
              "totalFloat",
              late.get(id) - early.get(id),
              "critical",
              late.get(id).equals(early.get(id))));
    return Map.of("duration", finish, "unit", "WORK_DAY", "tasks", out);
  }
}
