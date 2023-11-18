package com.arqui.integrador.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.arqui.integrador.grpc.StationGrpcObject;
import com.arqui.integrador.grpcservice.StationServiceGrpc;
import com.arqui.integrador.model.Station;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class StationRepositoryGrpc{
	
	private static final Logger LOG = LoggerFactory.getLogger(StationRepositoryGrpc.class);


	@Autowired
    private EntityManagerFactory entityManagerFactory;
	
	public StationRepositoryGrpc() {
		entityManagerFactory = Persistence.createEntityManagerFactory("PersistenceGrpc");
	}
	
	@Transactional
	public List<StationGrpcObject> getNearStations(double minLatitude, double maxLatitude, double minLongitude,
			double maxLongitude) {
		
		 	EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		 	Query query = entityManager.createQuery(
	                "SELECT s " +
	                        "FROM com.arqui.integrador.model.Station s " +
	                        "WHERE (s.latitude BETWEEN :minLatitude AND :maxLatitude) " +
	                        "AND (s.longitude BETWEEN :minLongitude AND :maxLongitude) ");

		 	LOG.info("Entro: {} {} {} {}", minLatitude, maxLatitude, minLongitude, maxLongitude);
		 	
	        query.setParameter("minLatitude", minLatitude);
	        query.setParameter("maxLatitude", maxLatitude);
	        query.setParameter("minLongitude", minLongitude);
	        query.setParameter("maxLongitude", maxLongitude);
	        
			@SuppressWarnings("unchecked")
			List<Station> result = (List<Station>)query.getResultList();
			
	        
	        List<StationGrpcObject> objectsToReturn = new ArrayList<>();
			
	        result.forEach(station -> objectsToReturn.add(
	        		StationGrpcObject.newBuilder()
							.setId(station.getId())
							.setLocation(station.getLocation())
							.setLongitude(station.getLongitude())
							.setLatitude(station.getLatitude())
							.build()));
	        
	        entityManager.close();
	        
	        return objectsToReturn;
	}

	@Transactional
	public StationGrpcObject getNearest(double latitude, double longitude) {
	 	
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		Query query = entityManager.createQuery(
                "SELECT s "
                + "FROM com.arqui.integrador.model.Station s "
                + "ORDER BY (SQRT((POWER(s.latitude - :latitude, 2)) + "
                + "(POWER(s.longitude-:longitude, 2)))) ASC ");

        query.setParameter("latitude", latitude);
        query.setParameter("longitude", longitude);
        query.setMaxResults(1);
        
        Station nearestStation = (Station) query.getSingleResult();
        
        StationGrpcObject nearestStationGrpc = StationGrpcObject.newBuilder()
                .setId(nearestStation.getId())
                .setLocation(nearestStation.getLocation())
                .setLongitude(nearestStation.getLatitude())
                .setLatitude(nearestStation.getLongitude())
                .build();
        
        entityManager.close();
        
        return nearestStationGrpc;
	}
	
}
