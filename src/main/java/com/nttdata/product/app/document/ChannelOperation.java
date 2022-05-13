package com.nttdata.product.app.document;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Document(collection="channel_operation")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ChannelOperation {
    @Id
    private String id;
    private String description;
    
}
