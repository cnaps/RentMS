package com.msa.rental.framework.web.dto;

import com.msa.rental.domain.model.RentalCard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RentalCardOutputDTO {

    private String rentalCardId;
    private String memberId;
    private String memberName;
    //대여가능여부
    private String rentStatus;
    //전체연체료
    private Long totalLateFee;
    //전체대여도서건수
    private Long totalRentalCnt;
    //반납도서건수
    private Long totalReturnCnt;
    //연체중인도서건수
    private Long totalOverduedCnt;

    public static RentalCardOutputDTO mapToDTO(RentalCard rental){
        RentalCardOutputDTO rentDTO = new RentalCardOutputDTO();
        rentDTO.setRentalCardId(rental.getRentalCardNo().getNo().toString());
        rentDTO.setMemberId(rental.getMember().getId().toString());
        rentDTO.setMemberName(rental.getMember().getName());
        rentDTO.setRentStatus(rental.getRentStatus().toString());
        rentDTO.setTotalRentalCnt(rental.getRentalItemList().stream().count());
        rentDTO.setTotalReturnCnt(rental.getReturnItemLIst().stream().count());
        rentDTO.setTotalOverduedCnt(rental.getRentalItemList().stream().filter(i -> i.isOverdued()).count());
        return rentDTO;
    }
}
