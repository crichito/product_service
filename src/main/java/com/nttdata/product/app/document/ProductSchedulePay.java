package com.nttdata.product.app.document;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductSchedulePay {
    private Integer letter;
    private Date datePay;
    private Date datePayed;
    private Double value;
    private State state;
}
