package com.webflux.essentials.service;

import com.webflux.essentials.domain.Anime;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AnimeService {
    public Flux<Anime> findAll();
    public Mono<Anime> findByUuid(UUID uuid);

    Mono<Anime> save(Anime anime);

    Mono<Anime> update(Anime anime);
}
