package com.nttdata.product.app.document;

import java.util.Date;
import java.util.List;

import com.mongodb.client.model.Collation;

import org.springframework.data.annotation.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Operation {
    private String operation;
    private OperationType operationType;
    private String description;
    private Double monto;
    private Double comision;
    private Double total;
    private Date dateOperation;
    private Audit audit;
    private ChannelOperation channelOperation;

}
