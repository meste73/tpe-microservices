package com.arqui.integrador.grpcservice;

import java.util.List;

import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arqui.integrador.grpc.Location;
import com.arqui.integrador.grpc.StationGrpcObject;
import com.arqui.integrador.grpc.StationGrpcObjectList;
import com.arqui.integrador.grpc.StationServiceGrpc.StationServiceImplBase;
import com.arqui.integrador.repository.StationRepositoryGrpc;

import io.grpc.stub.StreamObserver;

@GRpcService
public class StationServiceGrpc extends StationServiceImplBase{

	private static final Logger LOG = LoggerFactory.getLogger(StationServiceGrpc.class);

	private static final double DISTANCE = 50;
	
	private StationRepositoryGrpc stationRepository;
	
	public StationServiceGrpc(StationRepositoryGrpc stationRepository) {
		this.stationRepository = stationRepository;
	}

	@Override
	public void getNearest(Location request, StreamObserver<StationGrpcObject> responseObserver) {
		
		StationGrpcObject response = this.stationRepository.getNearest(
				request.getLatitude(), request.getLongitude());
		
		
		LOG.info("Near station: {} ", response);
		
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Override
	public void getNearStations(Location request, StreamObserver<StationGrpcObjectList> responseObserver) {
		
		LOG.info("ENTRO: {} ");
		
		double minLatitude = request.getLatitude() - DISTANCE;
		double maxLatitude = request.getLatitude() + DISTANCE;
		double minLongitude = request.getLongitude() - DISTANCE;
		double maxLongitude = request.getLongitude() + DISTANCE;
		
		List<StationGrpcObject> response = this.stationRepository.getNearStations(
				minLatitude, maxLatitude, minLongitude, maxLongitude);
		
		LOG.info("PASO: {} ");
		
		StationGrpcObjectList.Builder listToReturn = StationGrpcObjectList.newBuilder();
		
		LOG.info("PASO 2: {} ");
		
		response.forEach(object -> listToReturn.addStation(object));
		
		LOG.info("Getting near stations: {} ", listToReturn);
		
		responseObserver.onNext(listToReturn.build());
		responseObserver.onCompleted();
	}
	
}
