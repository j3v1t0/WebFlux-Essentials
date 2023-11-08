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
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Service;
import reactor.netty.http.client.PrematureCloseException;
import reactor.util.retry.Retry;

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
                .subscribeOn(Schedulers.immediate())
                .switchIfEmpty(monoResponseStatusNotFoundException())
                .map((c) -> {
                    c.setName(anime.getName());
                    return c;
                })
                .flatMap(animeRespository::save)
                .retryWhen(catchingQueryTimeoutException).log()
                .checkpoint();
    }

    public <T> Mono<T> monoResponseStatusNotFoundException(){
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,"Anime not found"));
    }

    public static final Retry catchingQueryTimeoutException = Retry.fixedDelay(3, Duration.ofSeconds(2))
            .filter(it -> it instanceof PrematureCloseException || it instanceof QueryTimeoutException)
            .doAfterRetry(retrySignal ->
                    log.info("iteration: {}, cause: {} {}",
                            retrySignal.totalRetriesInARow(),
                            retrySignal.failure().getClass().toString(),
                            retrySignal.failure().getMessage())
            ).onRetryExhaustedThrow((retrySpec, retrySignal) -> retrySignal.failure());
}
