package com.nttdata.product.app.dto;

import java.util.List;

import com.nttdata.product.app.document.Audit;
import com.nttdata.product.app.document.ChannelOperation;
import com.nttdata.product.app.document.OperationType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationPersonNaturalAccountRequest {

    private String idAccount;
    //private String idAccountType;
    private String idClient;
    //private String operation;
    private OperationType operationType;
    //private String idChannelOperation;
    private String description;
    private Double value;
    //private Double comision;
    //private Double total;
    //private Audit audit;
    private ChannelOperation channelOperation;
    //private List<String> clients;
    
}
