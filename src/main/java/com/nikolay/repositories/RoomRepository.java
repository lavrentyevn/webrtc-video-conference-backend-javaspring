package com.nikolay.repositories;

import com.nikolay.dto.RoomResponse;
import com.nikolay.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    Optional<Room> findByName(String name);
    @Query(value = "SELECT r.name as name, e.room_id as room_id, c.username as username, r.created_at as created_at, " +
            "      r.description as description FROM room r\n" +
            "      LEFT JOIN event e ON r.id = e.room_id\n" +
            "      LEFT JOIN invitation i ON r.id = i.room_id \n" +
            "      LEFT JOIN user_model um ON um.id = r.creator_id\n" +
            "      LEFT JOIN client c ON c.user_model_id = um.id\n" +
            "      WHERE e.room_id IS NULL OR r.creator_id = ?1 OR i.user_model_id = ?1", nativeQuery = true)
    List<RoomResponse> getRooms(Integer user_id);
    Optional<Room> findByNameAndUserModelEmail(String name, String email);
}
