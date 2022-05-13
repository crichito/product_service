package com.nttdata.product.app.document;

import java.util.Collection;
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
public class AccountBusiness {
    private Collection<String> idAuthorizedSigners;

    private Collection<Client> clients;
}
