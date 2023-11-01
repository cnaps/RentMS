package com.msa.rental.application.inputport;

import com.msa.rental.application.outputport.EventOutputPort;
import com.msa.rental.application.outputport.RentalCardOutputPort;
import com.msa.rental.application.usecase.CompensationUsecase;
import com.msa.rental.domain.model.RentalCard;
import com.msa.rental.domain.model.event.PointUseCommand;
import com.msa.rental.domain.model.vo.IDName;
import com.msa.rental.domain.model.vo.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class CompensationInputPort implements CompensationUsecase {

    private final RentalCardOutputPort rentalCardOutputPort;
    private final EventOutputPort eventOutputPort;

    @Override
    public RentalCard cancleRentItem(IDName idName, Item item) {

        return rentalCardOutputPort.loadRentalCard(idName.getId())
                .map(rentalCard -> {
                    try {
                        rentalCard.cancelRentItem(item);
                        eventOutputPort.occurPointUseCommand(new PointUseCommand(idName,10L));
                        return rentalCard;
                    } catch (Exception e){
                        throw new RuntimeException(e);
                    }
                })
                .orElseThrow(() -> new NoSuchElementException("RentalCard not found for ID" + idName.getId()));
    }

    @Override
    public RentalCard cancleReturnItem(IDName idName, Item item, long point) {
        return rentalCardOutputPort.loadRentalCard(idName.getId())
                .map(rentalCard -> {
                    try {
                        rentalCard.cancelReturnItem(item, point);
                        eventOutputPort.occurPointUseCommand(new PointUseCommand(idName, point));
                        return rentalCard;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElseThrow(() -> new NoSuchElementException("Rental card not found for ID: " + idName.getId()));
    }
    @Override
    public long cancleMakeAvailableRental(IDName idName, long point) {
        return rentalCardOutputPort.loadRentalCard(idName.getId())
                .map(rentalCard -> {
                    try {
                        return rentalCard.cancelMakeAvailableRental(point);
                        // 별도로 포인트 사용취소를 보상하기위한 이벤트는 발행하지 않음.
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElseThrow(() -> new NoSuchElementException("Rental card not found for ID: " + idName.getId()));
    }
}
