package com.msa.rental.domain.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class IDName {
    private String id;
    private String name;

    public static IDName sampe(){
        return new IDName("scant","han");
    }

    public static void main(String[] args){
        System.out.println(sampe().toString());
    }
}
