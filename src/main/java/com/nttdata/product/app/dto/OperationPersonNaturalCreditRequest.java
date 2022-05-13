package com.nttdata.product.app.dto;

import com.nttdata.product.app.document.ChannelOperation;
import com.nttdata.product.app.document.OperationType;

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
public class OperationPersonNaturalCreditRequest {
    private String idProduct;
    private String idClient;
    private OperationType operationType;
    private String description;
    private Double value;
    private ChannelOperation channelOperation;    
}
