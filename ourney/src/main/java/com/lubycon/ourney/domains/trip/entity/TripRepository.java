package com.lubycon.ourney.domains.trip.entity;

import com.lubycon.ourney.domains.trip.dto.TripListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {
    @Query("SELECT T.tripId FROM Trip T WHERE T.tripName = :tripName AND T.ownerId = :ownerId")
    UUID findIdbyTripName(@Param("tripName") String tripName, @Param("ownerId") Long ownerId);

    @Query("SELECT new com.lubycon.ourney.domains.trip.dto.TripListResponse(T.tripId, T.tripName, T.startDate, T.endDate, T.isEnded)" +
            "FROM Trip T INNER JOIN UserTripMap M " +
            "ON T.tripId = M.trip.tripId where M.user.id = :userId")
    List<TripListResponse> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(M.user.id) FROM UserTripMap M WHERE M.trip.tripId = :tripId")
    Long findByTripId(@Param("tripId") UUID tripId);

}