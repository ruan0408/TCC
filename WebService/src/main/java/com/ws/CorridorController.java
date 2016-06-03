package com.ws;

import com.fasterxml.jackson.annotation.JsonView;
import com.smartsampa.busapi.impl.BusAPI;
import com.smartsampa.busapi.model.Corridor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ruan0408 on 28/05/2016.
 */
@RestController
public class CorridorController {

    @JsonView(View.CorridorComplete.class)
    @RequestMapping("/corridors")
    public List<Corridor> getAllCorridors() {
        return BusAPI.getAllCorridors();
    }

    @JsonView(View.CorridorComplete.class)
    @RequestMapping("/corridors/search")
    public Corridor getCorridorByTerm(@RequestParam(value = "term", defaultValue = "") String term) {
        return BusAPI.getCorridorByTerm(term);
    }
}
