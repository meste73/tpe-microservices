package com.arqui.integrador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.arqui.integrador.grpc.StationGrpcObject;
import com.arqui.integrador.model.Station;

@Repository
public interface IStationRepository extends JpaRepository<Station, Integer>{
	
	@Query("SELECT new com.arqui.integrador.grpc.StationGrpcObject(s.id, s.location, s.latitude, s.longitude) "
			+ "FROM com.arqui.integrador.model.Station s "
			+ "WHERE (s.latitude BETWEEN :minLatitude AND :maxLatitude)"
			+ "AND (s.longitude BETWEEN :minLongitude AND :maxLongitude)")
	List<StationGrpcObject> getNearStations(@Param ("minLatitude") double minLatitude,
									@Param ("maxLatitude") double maxLatitude,
									@Param ("minLongitude") double minLongitude,
									@Param ("maxLongitude") double maxLongitude);
	
	@Query("SELECT new com.arqui.integrador.grpc.StationGrpcObject(s.id, s.location, s.latitude, s.longitude) "
			+ "FROM com.arqui.integrador.model.Station s "
			+ "ORDER BY (SQRT((POWER(s.latitude-:latitude, 2)) + (POWER(s.longitude-:longitude, 2)))) ASC "
			+ "LIMIT 1")
	StationGrpcObject getNearest(@Param ("latitude") double latitude,
									@Param ("longitude") double longitude);
	
}
