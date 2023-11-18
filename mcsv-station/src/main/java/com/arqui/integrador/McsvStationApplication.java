package com.arqui.integrador;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import com.arqui.integrador.grpcservice.StationServiceGrpc;
import com.arqui.integrador.repository.StationRepositoryGrpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

@SpringBootApplication
@EnableDiscoveryClient
public class McsvStationApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		StationRepositoryGrpc repository = new StationRepositoryGrpc();
		
		Server server = ServerBuilder.forPort(6565)
                .addService(new StationServiceGrpc(repository))
                .build();
		
		server.start();
		
		SpringApplication.run(McsvStationApplication.class, args);
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server");
            server.shutdown();
        }));

        // Esperar hasta que el servidor se apague (puede ser reemplazado por lógica de tu aplicación)
        server.awaitTermination();
	}

}
