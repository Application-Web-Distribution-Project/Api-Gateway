package com.restaurant.api_gateway.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/diagnostic")
public class DiagnosticController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/services")
    public Map<String, Object> listServices() {
        Map<String, Object> result = new HashMap<>();
        
        // Get all service IDs
        List<String> services = discoveryClient.getServices();
        result.put("availableServices", services);
        
        // Get instance details for each service
        Map<String, List<Map<String, Object>>> serviceDetails = new HashMap<>();
        for (String service : services) {
            List<Map<String, Object>> instanceDetails = discoveryClient.getInstances(service)
                .stream()
                .map(this::instanceToMap)
                .collect(Collectors.toList());
            
            serviceDetails.put(service, instanceDetails);
        }
        
        result.put("serviceDetails", serviceDetails);
        return result;
    }
    
    @GetMapping("/services/{serviceId}")
    public Map<String, Object> getServiceDetails(@PathVariable String serviceId) {
        Map<String, Object> result = new HashMap<>();
        
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        if (instances.isEmpty()) {
            result.put("error", "Service not found or not registered");
            result.put("availableServices", discoveryClient.getServices());
            return result;
        }
        
        result.put("instances", instances.stream()
            .map(this::instanceToMap)
            .collect(Collectors.toList()));
            
        return result;
    }
    
    private Map<String, Object> instanceToMap(ServiceInstance instance) {
        Map<String, Object> details = new HashMap<>();
        details.put("serviceId", instance.getServiceId());
        details.put("host", instance.getHost());
        details.put("port", instance.getPort());
        details.put("uri", instance.getUri().toString());
        details.put("secure", instance.isSecure());
        details.put("metadata", instance.getMetadata());
        return details;
    }
}