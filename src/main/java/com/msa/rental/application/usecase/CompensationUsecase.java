package com.msa.rental.application.usecase;

import com.msa.rental.domain.model.RentalCard;
import com.msa.rental.domain.model.vo.IDName;
import com.msa.rental.domain.model.vo.Item;

public interface CompensationUsecase {

    public RentalCard cancleRentItem(IDName idName, Item item);
    public RentalCard cancleReturnItem(IDName idName,Item item, long point) throws Exception;
    public long cancleMakeAvailableRental(IDName idName, long point);


}
