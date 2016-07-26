package com.ws;

import com.smartsampa.busapi.BusAPI;
import com.smartsampa.busapi.Corridor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ruan0408 on 28/05/2016.
 */
@RestController
public class CorridorController {

    @RequestMapping("/corridors")
    public List<Corridor> getAllCorridors() {
        return BusAPI.getAllCorridors();
    }

    @RequestMapping("/corridors/search")
    public Corridor getCorridorByTerm(@RequestParam(value = "term", defaultValue = "") String term) {
        return BusAPI.getCorridorByTerm(term);
    }
}
