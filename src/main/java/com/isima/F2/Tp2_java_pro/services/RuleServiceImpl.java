package com.isima.F2.Tp2_java_pro.services;

import com.isima.F2.Tp2_java_pro.models.Rule;
import com.isima.F2.Tp2_java_pro.repositories.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RuleServiceImpl implements RuleService{

    private final RuleRepository ruleRepository;

    @Autowired
    public RuleServiceImpl(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Override
    public int getPoint(String component, double componentValue) {

        List<Rule> rules = new ArrayList<>();
        rules = ruleRepository.findByName(component);
        int result = 0;

        for(Rule rule : rules)
        {
            if(rule.getMinBound() < componentValue) result = rule.getPoints();
            else break;
        }

        if(rules.get(0).getComponent() == "P") return result * (-1);

        return result;
    }
}
