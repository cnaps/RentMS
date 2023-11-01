package com.msa.rental.domain.model.event;

import com.msa.rental.domain.model.vo.IDName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class PointUseCommand implements Serializable {
    private IDName idName;
    private long point;
}
