package samatov.com.Task35;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class Main {
    static String url = "http://94.198.50.185:7081/api/users";
    private static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        Main main = new Main();
        main.getSession();
    }

    private void getSession() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List> user = restTemplate.exchange(url, HttpMethod.GET, requestEntity, List.class);
        List<String> cookies = user.getHeaders().get("Set-Cookie");
        if (cookies != null) {
            headers.put("Cookie", cookies);
        }

        HttpStatus statusCode = (HttpStatus) user.getStatusCode();
        System.out.println("GET status code - " + statusCode);
        List<Object> userDetails = user.getBody();
        System.out.println("GET response body - " + userDetails);
        HttpHeaders responseHeaders = user.getHeaders();
        System.out.println("GET response Headers - " + responseHeaders);

        User userToAdd = new User();
        userToAdd.setName("James");
        userToAdd.setLastName("Brown");
        userToAdd.setAge((byte) 29);
        userToAdd.setId(3L);

        ResponseEntity<String> responseEntityPost = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(userToAdd, headers), String.class);

        HttpStatus postStatusCode = (HttpStatus) responseEntityPost.getStatusCode();
        System.out.println("POST status code - " + postStatusCode);
        String userDetailsAdded = responseEntityPost.getBody();
        System.out.println("POST response body - " + userDetailsAdded);
        HttpHeaders postResponseHeaders = responseEntityPost.getHeaders();
        System.out.println("POST response Headers - " + postResponseHeaders);

        User userToUpdate = new User();
        userToUpdate.setName("Thomas");
        userToUpdate.setLastName("Shelby");
        userToUpdate.setAge((byte) 2);
        userToUpdate.setId(3L);

        ResponseEntity<String> responseEntityPut = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(userToUpdate, headers), String.class);

        HttpStatus putStatusCode = (HttpStatus) responseEntityPut.getStatusCode();
        System.out.println("PUT status code - " + putStatusCode);
        String userDetailsUpdated = responseEntityPut.getBody();
        System.out.println("PUT response body - " + userDetailsUpdated);
        HttpHeaders putResponseHeaders = responseEntityPut.getHeaders();
        System.out.println("PUT response Headers - " + putResponseHeaders);

        Long userIdToDelete = 3L;
        String deleteUrl = url + "/" + userIdToDelete;

        ResponseEntity<String> deleteResponseEntity = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, new HttpEntity<>(headers), String.class);

        HttpStatus deleteStatusCode = (HttpStatus) deleteResponseEntity.getStatusCode();
        System.out.println("DELETE status code - " + deleteStatusCode);
        String userDetailsDeleted = String.valueOf(deleteResponseEntity.getBody());
        System.out.println("DELETE response body - " + userDetailsDeleted);

    }
}
