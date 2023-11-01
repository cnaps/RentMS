package com.msa.rental.domain.model;

import com.msa.rental.domain.model.vo.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RentalItem {
   @Embedded
   private Item item;
   private LocalDate rentDate;

   private boolean overdued;

   private LocalDate overdueDate; //반납예정일

   public static RentalItem createRentalItem(Item item)
   {
    return new RentalItem(item,LocalDate.now(),false,LocalDate.now().plusDays(14));
   }

   public static RentalItem sample(){
    return RentalItem.createRentalItem(Item.Sample());
   }
}
