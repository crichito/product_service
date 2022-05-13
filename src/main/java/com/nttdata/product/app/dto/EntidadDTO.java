package com.nttdata.product.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntidadDTO<T> {
    public boolean result;
    public String message;
    private T entidad;
}
