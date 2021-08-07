package tests.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItem {

    private boolean success;
    private String message;
    private String updatetopcartsectionhtml;
}