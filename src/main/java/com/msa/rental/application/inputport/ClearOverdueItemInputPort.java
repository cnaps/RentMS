package com.msa.rental.application.inputport;

import com.msa.rental.application.outputport.EventOutputPort;
import com.msa.rental.application.outputport.RentalCardOutputPort;
import com.msa.rental.application.usecase.ClearOverdueItemUsecase;
import com.msa.rental.domain.model.RentalCard;
import com.msa.rental.framework.web.dto.ClearOverdueInfoDTO;
import com.msa.rental.framework.web.dto.RentalResultOutputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClearOverdueItemInputPort implements ClearOverdueItemUsecase {

    private final RentalCardOutputPort rentalCardOutputPort;
    private final EventOutputPort eventOutputPort;

    @Override
    public RentalResultOutputDTO clearOverdue(ClearOverdueInfoDTO clearOverdueInfoDTO) throws Exception {
        RentalCard rentalCard = rentalCardOutputPort.loadRentalCard(clearOverdueInfoDTO.UserId)
                .orElseThrow(() -> new IllegalArgumentException("해당 카드가 존재하지 않습니다."));

        rentalCard.makeAvailableRental(clearOverdueInfoDTO.getPoint());

        eventOutputPort.occurOverdueClearedEvent(RentalCard.createOverdueCleardEvent(rentalCard.getMember(),clearOverdueInfoDTO.getPoint()));

        return RentalResultOutputDTO.mapToDTO(rentalCard);
    }
}
