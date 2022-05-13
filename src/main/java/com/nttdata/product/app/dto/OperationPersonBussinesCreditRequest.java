package com.nttdata.product.app.dto;

import com.mongodb.client.model.changestream.OperationType;
import com.nttdata.product.app.document.ChannelOperation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationPersonBussinesCreditRequest {
    private String idProduct;
    private String idClient;
    private OperationType operationType;
    private String description;
    private Double value;
    private ChannelOperation channelOperation;  
}
