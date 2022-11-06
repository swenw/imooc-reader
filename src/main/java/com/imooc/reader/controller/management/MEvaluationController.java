package com.imooc.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.mapper.EvaluationMapper;
import com.imooc.reader.service.BussinessException;
import com.imooc.reader.service.EvaluationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/management/evaluation")
public class MEvaluationController {
    @Resource
    private EvaluationService evaluationService;

    @GetMapping("/index.html")
    public ModelAndView showEvaluation() {
        return new ModelAndView("/management/evaluation");
    }

    @GetMapping("/list")
    @ResponseBody
    public Map list(Integer page, Integer limit) {
        if (page == null) page = 1;
        if (limit == null) limit = 10;
        List<Evaluation> paging = evaluationService.paging(page, limit);
        Map result = new HashMap();
        result.put("code", "0");
        result.put("msg", "success");
        result.put("data", paging);
        result.put("count", paging.size());
        return result;
    }

    @PostMapping("/disable")
    @ResponseBody
    public Map disable(Long evaluationId, String reason) {
        Map result = new HashMap();
        try {
            Evaluation evaluation = evaluationService.disable(evaluationId, reason);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }
}
