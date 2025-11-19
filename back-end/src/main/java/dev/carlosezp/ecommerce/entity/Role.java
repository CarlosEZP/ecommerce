package dev.carlosezp.ecommerce.entity;

import dev.carlosezp.ecommerce.enums.AppRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AppRole name;

    public Role(AppRole name) {
        this.name = name;
    }
}
