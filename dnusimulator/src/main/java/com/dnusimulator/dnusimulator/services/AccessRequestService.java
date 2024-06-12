package com.dnusimulator.dnusimulator.services;

import com.dnusimulator.dnusimulator.dto.EventType;
import com.dnusimulator.dnusimulator.dto.AccessRequest;

import java.time.Duration;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class AccessRequestService {

    private static final Logger logger = LoggerFactory.getLogger(AccessRequestService.class);
    private RestTemplate restTemplate;

    private Random random = new Random();

    @Value("${teracontrol.url}${accessRequestEndpoint}")
    private String url;

    public AccessRequestService() {
        this.restTemplate = new RestTemplate();
    }

    public void generateRandomAccessRequests() throws InterruptedException {
        int maxRequests = 5000;
        int requestCount = 0;
        int toSleep = 1400   ;

        for (int i = 0; i < maxRequests; i++) {

            AccessRequest req = new AccessRequest(diceAKeyCode(), generateRandomEventType(), generateRandomDSN());
            logger.info("Sending request: {}, {}, {}", req.getKeychainCode(), req.getEventType(),
                    req.getSerialNumber());

            tryOpenDoor(req);

            Thread.sleep(toSleep);
            requestCount++;

            logger.info("Sleeping: {} miliseconds", toSleep);

        }
        logger.info("Total requests sent: {}", requestCount);

    }

    private String generateRandomDSN() {

        String[] serialNumbers = {
                "SN-1",
                "SN-2",
                "SN-3",
                "SN-4",
                "SN-5",
                "SN-6"
        };
        this.random = new Random();
        int randomIndex = random.nextInt(serialNumbers.length);
        return serialNumbers[randomIndex];
    }

    private EventType generateRandomEventType() {
        boolean isEntry = random.nextBoolean();
        return isEntry ? EventType.ENTRY : EventType.EXIT;
    }

    private String diceAKeyCode() {

        String[] keys = {
            "0123456789ABCDEF",
            "FEDCBA9876543210",
            "A1B2C3D4E5F67890",
            "138958ed",
            "1A2B3C4D5E",
            "6P7Q8D9S0T",
            "1U2V3WDX5Y",
            "6Z7A8B9CHD",
            "HE2F3G4H5I",
            "6H7K8L9M0N",
            "1OHP3Q4R5S",
            "6T7U8VHW0X",
            "1Y2ZHA4B5C",
            "6D7E8F9H0H",
            "1I2J3KHL5M",
            "6N7OHP9Q0R",
            "1S2THU4V5W",
            "6X7YHZ9A0B",
            "1C2D3H4F5G",
            "6H7IHJ9K0L",
            "1M2NHO4P5Q",
            "6H7S8T9U0V",
            "1W2X3YHZ5A",
            "6B7C8D9H0F",
            "1G2H3I4J5K",
            "6L7M8N9H0P",
            "1Q2R3SJT5U",
            "6V7W839H0Z",
            "6F7G3H9I0J",
            "6P7Q8RUS0T",
            "1U2V3WUX5Y",
            "6Z7A8U9C0D",
            "1E2F3G4HUI",
            "6J7K8L9MUN",
            "1O2P3QUR5S",
            "6T7U8VUW0X",
            "1Y2Z3AUB5C",
            "6D7EUF9G0H",
            "1I2J3KUL5M",
            "6N7OUP9Q0R",
            "1S2T3U4V5W",
            "6X7Y8U9A0B",
            "1C2D3E4FUG",
            "6H7I8J9U0L",
            "1M2NUO4P5Q",
            "6R7SUT9U0V",
            "1WRX3Y4Z5A",
            "6B7R8D9E0F",
            "1G2H3U4J5K",
            "6L7M8N9S0P",
            "1QSR3S4T5U",
            "6V7W8X9Y0Z",
            "6F7G8H9D0J",
            "1K2L3M4NFO",
            "6P7Q8RGS0T",
            "1U2VGW4X5Y",
            "6Z7A8H9C0D",
            "1E2FJG4H5I",
            "6J7KKL9M0N",
            "1O2P3QSR5S",
            "6T7U8VXW0X",
            "1C2Z3A4B5C",
            "6D7E8V9G0H",
            "1I2J3K4V5M",
            "6N7O8P9Q0R",
            "1S2T3UVV5W",
            "6X7Y8ZBA0B",
            "1C2D3ENF5G",
            "6H7I8M9K0L",
            "1M2N3O4P5Q",
            "6R7S8T9U0V",
            "1W2X3Y4Z5A",
            "6B7C8D9E0F",
            "1G2HW4J5K",
            "6L7M8N9OFP",
            "1Q2R3S4T5G",
            "6V7W8X9H0Z",
            "1A2B334D5E",
            "6F7G7H9I3",
            "6P7Q8A9S0T",
            "1U2A3W4X5Y",
            "6Z7AAB9C0D",
            "1E2FAG4H5I",
            "6J7K8L9A0N",
            "1O2P3Q4RAS",
            "6D7U8V9W0X",
            "1Y2Z3D4B5C",
            "6D7F8F9G0H",
            "1I2H3K4L5M",
            "6N7OGP9Q0R",
            "1S2T3U4H5W",
            "6XJY8Z9A0B",
            "1CHD3E4F5G",
            "6H7E8JGK0L",
            "1M2NGG4P5Q",
            "6R7S8TGR0V",
            "1W2XHYJZ5A",
            "6B7J8D9E7F",
            "1G2J5I4J5K",
            "6L7MKN640P",
            "1Q2RLKRT5U",
            "6V7W8HDY0Z",
            "1A2B34S85E",
            "6A7G3H9I0J",
            "6P7Q8RXS0T",
            "1U4VCW4X5Y",
            "6Z2AVB9C0D",
            "1EDF3B4H5I",
            "6J7K8J9V0N",
            "1O2PCQ4R5S",
            "6TEV8V9W0X",
            "1Y2Z3AJC5C",
            "6D758F9G0H",
            "1I283K4L5M",
            "6N648P9Q0R",
            "1S2T3U6V5W",
            "6X7Y8Z740B",
            "1C2D334F5G",
            "6H7D829K0L",
            "1M2N3ODX5Q",
            "6R7SCA9U0V",
            "1W2X3Y435A",
            "6B7C89E0F",
            "1G2H304J5K",
            "6L7M9N9O0P",
            "1S2T3U8V5W",
            "6X7Y8Z7A0B",
            "1E2F364H5I",
            "6J7K8L9M4N",
            "1O2P3Q1R5S"
        };
        this.random = new Random();
        int randomIndex = random.nextInt(keys.length);
        return keys[randomIndex];
    }

    public void tryOpenDoor(AccessRequest accessRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestJson = "{"
                + "\"code\":\"" + accessRequest.getKeychainCode() + "\","
                + "\"eventType\":\"" + accessRequest.getEventType() + "\","
                + "\"serialNumber\":\"" + accessRequest.getSerialNumber() + "\""
                + "}";

                logger.info("\n\n\nSending request:\n KEY: {} \n EVENT-TYPE: {} \n SERIAL: {} \n", accessRequest.getKeychainCode(), accessRequest.getEventType(),
                                        accessRequest.getSerialNumber());

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        try {
       restTemplate.postForObject(url, entity, String.class);
} catch (ResourceAccessException e) {
    logger.error("Timeout occurred", e);
}

    catch (Exception e) {
        logger.error("Error occurred", e);
        // Handle the exception and log the error message
        // You can also return an appropriate HTTP status code response here
        // For example:
        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
       
    

    @Bean
    public CommandLineRunner run() {
        return args -> {
            try {
                generateRandomAccessRequests();

            } catch (Exception e) {

            }

        };
    }

    @Bean
    private RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(5000))
                .setReadTimeout(Duration.ofMillis(5000))
                .build();
    }
}