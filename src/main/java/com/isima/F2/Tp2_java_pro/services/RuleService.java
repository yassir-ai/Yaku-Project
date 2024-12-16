package com.isima.F2.Tp2_java_pro.services;


import org.springframework.stereotype.Service;

@Service
public interface RuleService {
    int getPoint(String component, double componentValue);
}
