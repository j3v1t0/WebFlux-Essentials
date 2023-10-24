package com.webflux.essentials.service.impl;

import com.webflux.essentials.domain.Anime;
import com.webflux.essentials.repository.AnimeRespository;
import com.webflux.essentials.service.AnimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AnimeServiceImpl implements AnimeService {
    @Autowired
    private AnimeRespository animeRespository;
    @Override
    public Flux<Anime> findAll() {
        return animeRespository.findAll();
    }

    @Override
    public Mono<Anime> findByUuid(String uuid) {
        return animeRespository.findByUuid(uuid)
                .switchIfEmpty(monoResponseStatusNotFoundException());
    }

    public <T> Mono<T> monoResponseStatusNotFoundException(){
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,"Anime not found"));
    }
}
