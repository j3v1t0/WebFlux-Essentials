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

import java.util.UUID;

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
    public Mono<Anime> findByUuid(UUID uuid) {
        return animeRespository.findById(uuid)
                .switchIfEmpty(monoResponseStatusNotFoundException());
    }

    @Override
    public Mono<Anime> save(Anime anime) {
        return animeRespository.save(anime);
    }

    @Override
    public Mono<Anime> update(Anime anime) {
        return animeRespository.findById(anime.getUuid())
                .switchIfEmpty(monoResponseStatusNotFoundException())
                .map((c) -> {
                    c.setName(anime.getName());
                    return c;
                })
                .flatMap(animeRespository::save);

    }

    public <T> Mono<T> monoResponseStatusNotFoundException(){
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,"Anime not found"));
    }
}
