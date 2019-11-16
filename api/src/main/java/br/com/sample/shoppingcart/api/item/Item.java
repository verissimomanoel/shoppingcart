/*
 * Item.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.item;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

/**
 * Entity class to represent data of 'Item'.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
@Document("Item")
@Getter
@Setter
public class Item {
    @ApiModelProperty(value = "Id of Item")
    @Id
    private String id;

    @NotNull
    @ApiModelProperty(value = "Name of item")
    @Indexed(name = "name")
    private String name;

    @NotNull
    @ApiModelProperty(value = "Name of item")
    private Double value;
}
