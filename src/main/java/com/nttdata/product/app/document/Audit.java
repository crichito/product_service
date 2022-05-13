package com.nttdata.product.app.document;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Audit {
    
    private String userReg;
    private Date dateReg;
    private String userUpd;
    private Date dateUdp;
    
}
