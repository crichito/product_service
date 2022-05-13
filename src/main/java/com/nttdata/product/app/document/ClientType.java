package com.nttdata.product.app.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection="client_type")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ClientType {

    

    public ClientType(String description) {
        this.description = description;
    }
    
    @Id
    private String id;
    private String description;      
}
