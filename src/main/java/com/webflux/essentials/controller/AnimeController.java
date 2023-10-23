package com.webflux.essentials.controller;

import com.webflux.essentials.domain.Anime;
import com.webflux.essentials.repository.AnimeRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("animes")
@Slf4j
@RequiredArgsConstructor
public class AnimeController {
    private final AnimeRespository animeRespository;

    @GetMapping
    public Flux<Anime> listAll(){
        return animeRespository.findAll();
    }
}
