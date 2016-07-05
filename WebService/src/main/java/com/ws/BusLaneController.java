package com.ws;


import com.smartsampa.busapi.BusAPI;
import com.smartsampa.busapi.BusLane;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ruan0408 on 28/05/2016.
 */
@RestController
public class BusLaneController {

    @RequestMapping("/buslanes")
    public List<BusLane> getAllBusLanes() {
        return BusAPI.getAllBusLanes();
    }

    @RequestMapping("/buslanes/search")
    public List<BusLane> getBusLanesByTerm(@RequestParam(value = "term", defaultValue = "") String term) {
        return BusAPI.getBusLanesByTerm(term);
    }
}
