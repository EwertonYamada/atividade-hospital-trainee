package com.hospital.room.controller;

import com.hospital.room.dto.RoomRequest;
import com.hospital.room.model.Room;
import com.hospital.room.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/specialty/{wardId}")
    public ResponseEntity<List<Room>> create(@PathVariable Long wardId, @RequestBody RoomRequest roomRequest) {
        return ResponseEntity.ok(this.roomService.create(wardId, roomRequest));
    }
}
