package com.flo.demo.web.vm;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AddTracksVM {

    @Size(max = 100)
    List<Long> albumIds = new ArrayList<>();

    @Size(max = 100)
    List<Long> songIds = new ArrayList<>();
}
