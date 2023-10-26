package com.webflux.essentials.service;

import com.webflux.essentials.domain.Anime;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AnimeService {
    public Flux<Anime> findAll();
    public Mono<Anime> findByUuid(String uuid);

    Mono<Anime> save(Anime anime);

    Mono<Void> update(Anime anime);
}
