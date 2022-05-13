package com.nttdata.product.app.dto;

import com.nttdata.product.app.document.ChannelOperation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOperationCreditCardRequest {
    private String cardNumber;
    private String pin;   
    private String description;
    private Double value;
    private ChannelOperation channelOperation; 
}
