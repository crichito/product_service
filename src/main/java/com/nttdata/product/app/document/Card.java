package com.nttdata.product.app.document;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private String cardNumber;
    private Date dueDate;
    private String code;
    private String pin;    
}
