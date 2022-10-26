package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import za.ac.cput.entity.Category;
import za.ac.cput.entity.UserPayment;
import za.ac.cput.factory.CategoryFactory;
import za.ac.cput.factory.UserPaymentFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserPaymentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private  UserPaymentController controller;
    private UserPayment userPayment;
    @LocalServerPort
    private  int port;
    private  String baseURL;
    @BeforeEach
    public void setUp() {
        assertNotNull(controller);
        this.userPayment = UserPaymentFactory.build("001", "EFT", "800");
        this.baseURL = "http://localhost:"+ this.port +"/userPayment/";
    }
    @Order(1)
    @Test
    void save() {
        String url = baseURL + "save/";
        System.out.println(url);
        ResponseEntity<UserPayment> response = this.restTemplate.postForEntity(url,this.userPayment,UserPayment.class);
        System.out.println(response);
        assertAll(()->assertEquals(HttpStatus.OK,response.getStatusCode()),
                ()->assertNotNull(response.getBody()));
        System.out.println("Created: " + response.getBody());
    }
    @Order(4)
    @Test
    void delete() {
        String url = baseURL + "delete/"+ this.userPayment.getId();
        this.restTemplate.delete(url);
        System.out.println("Deleted:" + this.userPayment.getId()+"\n"+url);
    }
    @Order(3)
    @Test
    void findAll() {
        String url = baseURL + "all/" ;
        System.out.println(url);
        HttpHeaders header = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, header);
        ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, entity,String.class);
        assertNotNull(response);
        assertAll(()->assertEquals(HttpStatus.OK,response.getStatusCode()));
        System.out.println(response.getBody());
    }
    @Order(2)
    @Test
    void read() {
        String url = baseURL + "read/"+ this.userPayment.getId();
        System.out.println(url);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserPayment> entity = new HttpEntity<>(null, headers);
        ResponseEntity<UserPayment> response = restTemplate.exchange(url, HttpMethod.GET, entity, UserPayment.class);
        System.out.println("Read: " + response.getBody());
    }
}
