package dev.carlosezp.ecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {
    private Map<String, String> message;
    private boolean status;
}
