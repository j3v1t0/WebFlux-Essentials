package com.webflux.essentials.repository;

import com.webflux.essentials.domain.Anime;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AnimeRespository extends ReactiveCrudRepository<Anime, String> {
}
