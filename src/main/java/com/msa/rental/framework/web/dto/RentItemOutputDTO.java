package com.msa.rental.framework.web.dto;

import com.msa.rental.domain.model.RentalItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RentItemOutputDTO {

    private Integer itemNo;
    private String itemtitle;

    private LocalDate rentDate;
    private boolean overdued;
    //반납 예정일
    private LocalDate overdueDate;

    public static RentItemOutputDTO mapToDTO(RentalItem rentItem)
    {
        RentItemOutputDTO rentItemOutputDTO = new RentItemOutputDTO();
        rentItemOutputDTO.setItemNo(rentItem.getItem().getNo());
        rentItemOutputDTO.setItemtitle(rentItem.getItem().getTitle());
        rentItemOutputDTO.setRentDate(rentItem.getRentDate());
        rentItemOutputDTO.setOverdued(rentItem.isOverdued());
        rentItemOutputDTO.setOverdueDate(rentItem.getOverdueDate());
        return rentItemOutputDTO;
    }
}
