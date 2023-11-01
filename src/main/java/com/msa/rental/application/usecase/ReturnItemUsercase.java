package com.msa.rental.application.usecase;

import com.msa.rental.framework.web.dto.RentalCardOutputDTO;
import com.msa.rental.framework.web.dto.UserItemInputDTO;

public interface ReturnItemUsercase {
    public RentalCardOutputDTO returnItem(UserItemInputDTO returnDto) throws Exception;
}
