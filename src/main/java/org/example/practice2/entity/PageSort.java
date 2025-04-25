package org.example.practice2.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;


@Getter
@RequiredArgsConstructor
public enum PageSort {

    ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
    ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
    NAME_ASC(Sort.by(Sort.Direction.ASC, "name")),
    NAME_DESC(Sort.by(Sort.Direction.DESC, "name")),
    GENRE_ASC(Sort.by(Sort.Direction.ASC, "genre")),
    GENRE_DESC(Sort.by(Sort.Direction.DESC, "genre"));

    private final Sort sortValue;


}
