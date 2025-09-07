package uz.qodirov.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import uz.qodirov.payload.PageableRequest;

public class PageableUtil {
    public static PageRequest pageRequest(PageableRequest pageable) {
        return PageRequest.of(
                pageable.getPage(),
                pageable.getPerPage(),
                Sort.Direction.fromString(pageable.getSort().getDirection().name()),
                pageable.getSort().getKey()
        );
    }
}
