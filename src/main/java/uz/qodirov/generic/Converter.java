package uz.qodirov.generic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Converter<T, U> {

    private final Function<T, U> fromDto;
    private final Function<U, T> fromEntity;

    public Converter(final Function<T, U> fromDto, final Function<U, T> fromEntity) {
        this.fromDto = fromDto;
        this.fromEntity = fromEntity;
    }

    public final U convertFromDto(final T dto) {
        return fromDto.apply(dto);
    }

    public final T convertFromEntity(final U entity) {
        return fromEntity.apply(entity);
    }

    public final List<U> createFromDtos(final Collection<T> dtos) {
        return dtos.stream().map(this::convertFromDto).collect(Collectors.toList());
    }

    public final List<T> createFromEntities(final Collection<U> entities) {
        return entities.stream().map(this::convertFromEntity).collect(Collectors.toList());
    }

    public final Set<T> createFromEntities(final Set<U> entities) {
        return entities.stream().map(this::convertFromEntity).collect(Collectors.toSet());
    }

    public final Page<T> createFromEntities(final Page<U> entities) {
        return new PageImpl<T>(entities.stream().map(this::convertFromEntity).collect(Collectors.toList()),
                entities.getPageable(),
                entities.getTotalElements());
    }

}
