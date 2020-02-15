package com.music.demo.web.util;

import com.music.demo.web.vm.PageVM;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.util.UriComponentsBuilder;

@NoArgsConstructor
public final class PageUtils {
    public static <T> PageVM generatePage(Page<T> page, UriComponentsBuilder uriBuilder) {
        PageVM pageVM = new PageVM();
        pageVM.setTotal(page.getTotalElements());
        pageVM.setSize(page.getSize());
        int number = page.getNumber();

        if (number < page.getTotalPages() - 1) {
            pageVM.setNext(uriBuilder.replaceQueryParam("page", Integer.toString(number + 1))
                    .build().toUriString());
        }
        if (number > 0) {
            pageVM.setPrev(uriBuilder.replaceQueryParam("page", Integer.toString(number - 1))
                    .build().toUriString());
        }
        pageVM.setFirst(uriBuilder.replaceQueryParam("page", "0")
                .build().toUriString());
        pageVM.setLast(uriBuilder.replaceQueryParam("page", page.getTotalPages() - 1)
                .build()
                .toUriString());
        return pageVM;
    }
}
