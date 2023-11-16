package com.arqui.integrador.grpcservice;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.arqui.integrador.grpc.Location;
import com.arqui.integrador.grpc.StationGrpcObject;
import com.arqui.integrador.grpc.StationGrpcObjectList;
import com.arqui.integrador.grpc.StationGrpcServiceGrpc.StationGrpcServiceImplBase;
import com.arqui.integrador.repository.IStationRepository;

import io.grpc.stub.StreamObserver;

public class StationServiceGrpc extends StationGrpcServiceImplBase{

private static final Logger LOG = LoggerFactory.getLogger(StationServiceGrpc.class);
	
	@Value("${distance}")
	private int DISTANCE;
	
	private IStationRepository stationRepository;
	
	public StationServiceGrpc(IStationRepository stationRepository) {
		this.stationRepository = stationRepository;
	}
	
	@Override
	public void getNearest(Location request, StreamObserver<StationGrpcObject> responseObserver) {
		
		StationGrpcObject response = this.stationRepository.getNearestStation(
				request.getLatitude(), request.getLongitude());
		
		
		LOG.info("Near station: {} ", response);
		
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Override
	public void getNearStations(Location request, StreamObserver<StationGrpcObjectList> responseObserver) {
		
		List<StationGrpcObject> response = this.stationRepository.getNearStations(
				request.getLatitude()-DISTANCE, 
				request.getLatitude()+DISTANCE, 
				request.getLongitude()-DISTANCE, 
				request.getLongitude()+DISTANCE);
		
		StationGrpcObjectList.Builder listToReturn = StationGrpcObjectList.newBuilder();
		
		response.forEach(object -> listToReturn.addStation(object));
		
		LOG.info("Getting near stations: {} ", listToReturn);
		
		responseObserver.onNext(listToReturn.build());
		responseObserver.onCompleted();
	}
	
}
