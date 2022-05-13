package com.nttdata.product.app.document;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Unwrapped.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection="client")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Client {
 
    public Client(String idDocumentType, String document, String direction, String idClientType,
            NaturalPerson naturalPerson, Audit audit) {
        this.idDocumentType = idDocumentType;
        this.document = document;
        this.direction = direction;
        this.idClientType = idClientType;
        this.naturalPerson = naturalPerson;
        this.audit = audit;
    }

    @Id
    private String id;
    private String idDocumentType;
    private String document;
    private String direction;
    private String idClientType;
    @Nullable
    private NaturalPerson naturalPerson;
    @Nullable
    private BusinessPerson businessPerson;
    private Audit audit;

    @Transient
    private ClientType clientType;
    @Transient
    private DocumentType documentType;
        
}
