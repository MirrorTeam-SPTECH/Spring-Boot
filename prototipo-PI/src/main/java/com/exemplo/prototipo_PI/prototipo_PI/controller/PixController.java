package com.exemplo.prototipo_PI.prototipo_PI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pix")
public class PixController {

//    @Autowired
//    private PixService pixService;
//
//    @PostMapping("/generate")
//    public ResponseEntity<PixPayloadResponse> generate(@RequestBody CreatePixRequest request) {
//        String payload = pixService.gerarPayload(request);
//        return ResponseEntity.ok(new PixPayloadResponse(payload));
//    }
}