package com.msa.rental.application.outputport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.msa.rental.domain.model.event.ItemRented;
import com.msa.rental.domain.model.event.ItemReturned;
import com.msa.rental.domain.model.event.OverdueCleared;
import com.msa.rental.domain.model.event.PointUseCommand;

public interface EventOutputPort {
    public void occurRentalEvent(ItemRented itemRented) throws JsonProcessingException;
    public void occurReturnEvent(ItemReturned itemReturned) throws JsonProcessingException;
    public void occurOverdueClearedEvent(OverdueCleared overdueCleared) throws JsonProcessingException;

    void occurPointUseCommand(PointUseCommand pointUseCommand);
}
