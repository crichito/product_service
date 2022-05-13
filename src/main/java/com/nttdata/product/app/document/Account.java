package com.nttdata.product.app.document;

import java.util.Collection;

import com.mongodb.lang.Nullable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection="account")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Account {
    @Id
    private String id;
    private String accountNumber;
    private Double balance;
    private String idAccountType;
    private String idState;
    private Collection<String> idClients;
    @Nullable
    private AccountBusiness accountBusiness;
    private Collection<Operation> operations;

    private AccountType accountType;
    private State state;
    private Card card;
    //private Collection<Client> clients;

}
