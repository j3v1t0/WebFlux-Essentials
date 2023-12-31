package com.webflux.essentials.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Table("anime")
public class Anime implements Serializable {
    @Id
    private UUID uuid;
    @NotNull
    @NotEmpty(message = "The name of the anime cannot be empty")
    private String name;
}
