package com.webflux.essentials.repository;

import com.webflux.essentials.domain.Anime;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AnimeRespository extends ReactiveCrudRepository<Anime, UUID> {

}
