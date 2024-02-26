package com.khachidze.commissionservice.service;


import com.khachidze.commissionservice.dto.AmountResponseDto;
import com.khachidze.commissionservice.entity.CommissionEntity;
import com.khachidze.commissionservice.repository.CommissionRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Optional;

@ApplicationScoped
public class CommissionService {

    @Inject
    private CommissionRepository commissionRepository;

    public AmountResponseDto getTotalAmount(BigDecimal amountRequest) {

        Optional<CommissionEntity> commissionEntityOpt = commissionRepository.findCommissionByAmount(amountRequest);

        CommissionEntity commissionEntity = commissionEntityOpt
                .orElseThrow(() -> new IllegalArgumentException("Commission not found"));

        BigDecimal commissionAmount = amountRequest.multiply(commissionEntity.getCommissionRate()).divide(BigDecimal.valueOf(100));

        return new AmountResponseDto(amountRequest.add(commissionAmount));
    }

}
