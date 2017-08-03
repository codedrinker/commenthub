package com.codedrinker.controller;

import com.codedrinker.entity.Authorization;
import com.codedrinker.entity.ResponseDTO;
import com.codedrinker.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by codedrinker on 07/07/2017.
 */
@RestController
@RequestMapping(value = "authorizations")
public class AuthorizationController {

    @Autowired
    private AuthorizationService authorizationService;

    @RequestMapping(method = RequestMethod.POST)
    Object create(@RequestBody Authorization authorization) {
        ResponseDTO responseDTO = authorizationService.save(authorization);
        return responseDTO;
    }

    @RequestMapping(method = RequestMethod.PUT)
    Object update(@RequestBody Authorization authorization) {
        ResponseDTO responseDTO = authorizationService.update(authorization);
        return responseDTO;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    Object delete(@RequestParam(value = "id") Integer id) {
        authorizationService.delete(id);
        return ResponseDTO.ok(null);
    }

    @RequestMapping(method = RequestMethod.GET)
    Object get(@RequestParam(value = "id") Integer id) {
        ResponseDTO responseDTO = authorizationService.get(id);
        return responseDTO;
    }
}
